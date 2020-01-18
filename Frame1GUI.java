//Student Name: Aditee Dnyaneshwar Dakhane
//Student ID: 1001745502

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.awt.event.ActionEvent;

public class Frame1GUI {

	FileClient fc;       //Declare instance of class FileClient

	private JFrame Client1;  //Declare variable for JFrame


	private JTextField textField;  //declare variable for textField
	/* Most of this code is auto - generated as windows builder is used to create GUI*/



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {    //updates GUI at the end
			public void run() {
				try {
					Frame1GUI window = new Frame1GUI(); //creates an instance of class Frame1GUI
					window.Client1.setVisible(true);     //frame is made visible 

				} catch (Exception e) {
					e.printStackTrace();   //Catches exception
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1GUI() {
		initialize();     //Constructor that calls the initialize method of the JFrame
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		//Constructor that calls the initialize method of the JFrame
		Client1 = new JFrame();           //creates frame for Client GUI
		Client1.setBounds(100, 100, 450, 300);  //sets frame size
		Client1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //exit the operation when click on the 'X' of the frame
		Client1.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); //provides layout to our frame


		JLabel lblNewLabel = new JLabel("Enter Username");   //Creates label to enter username
		lblNewLabel.setBounds(91, 64, 109, 21);    //set size for label
		Client1.getContentPane().add(lblNewLabel);  //add label to client frame

		textField = new JTextField();          //creates textfield to enter the value for username
		textField.setBounds(219, 59, 130, 26);   //sets size for textfield
		Client1.getContentPane().add(textField);   //add textfield to client frame
		textField.setColumns(10);	

		JTextArea textAreaClient1 = new JTextArea(2, 20);    //creates a textarea to get the output printed on GUI
		PrintStream printStreamClient1 = new PrintStream(new CustomOutputStream(textAreaClient1)); //Redirects console content to textarea
		System.setOut(printStreamClient1);// Re-assigns the standard output stream.
		System.setErr(printStreamClient1);//Re-assigns the standard error output stream.
		Client1.getContentPane().add(textAreaClient1);//Add the textarea to frame
		//Sites referred: line 86 to line 93 
		//https://www.tutorialspoint.com/how-to-set-action-command-to-jbutton-in-java
		//https://stackoverflow.com/questions/35094602/java-app-cant-be-closed-even-close-on-exit-tcp-server
		JButton btnSubmit = new JButton("Submit");   //creates an instance for submit button
		btnSubmit.setActionCommand("Start");          //set command for action on button
		btnSubmit.addActionListener(new ActionListener() {    //listen to the action on clicking the button
			public void actionPerformed(ActionEvent e) {       //Perform the action on event occurrence
				JOptionPane.showMessageDialog(null, "A Client named " + textField.getText() +" is connected");   //shows message or connected client
				String cName = textField.getText();       //get the clientname
				fc = new FileClient(textField.getText());   //passed the clientname to FileClient file
				String command = e.getActionCommand();      
				if ("Start".equals(command)) {      //if the command is equal to the Start
					new Thread(new Runnable() {      //implements runnable for the thread
						@Override
						public void run() {
							try {
								String directoryPath = "/Users/Ankit/Documents/Client1Directory/";   //sets directory path for the client
								fc.Start(directoryPath,textField.getText());   //pass path of directory and clientname to start method of FileClient file
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();
				} 
			}
		});
		btnSubmit.setBounds(232, 97, 117, 29);
		Client1.getContentPane().add(btnSubmit);  //add the submit button to the frame
		//printStreamClient1.close();



	}
}
