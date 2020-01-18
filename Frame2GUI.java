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

public class Frame2GUI {

	FileClient fc;  //Declare instance of class FileClient


	private JFrame Client2; //Declare variable for JFrame

	private JTextField textField2;//declare variable for textField
	/* Most of this code is auto - generated as windows builder is used to create GUI*/




	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {  //updates GUI at the end
			public void run() {
				try {
					Frame2GUI window = new Frame2GUI();  //creates an instance of class Frame2GUI

					window.Client2.setVisible(true); //frame is made visible 

				} catch (Exception e) {
					e.printStackTrace(); //Catches exception
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame2GUI() {
		initialize();  //Constructor that calls the initialize method of the JFrame
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {


		//Constructor that calls the initialize method of the JFrame
		Client2 = new JFrame();//creates frame for Client GUI
		Client2.setBounds(100, 100, 450, 300);//sets frame size
		Client2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit the operation when click on the 'X' of the frame
		Client2.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); //provides layout to our frame

		JLabel lblNewLabel2 = new JLabel("Enter Username"); //Creates label to enter username
		lblNewLabel2.setBounds(91, 64, 109, 21); //set size for label
		Client2.getContentPane().add(lblNewLabel2);//add label to client frame

		textField2 = new JTextField();//creates textfield to enter the value for username
		textField2.setBounds(219, 59, 130, 26); //sets size for textfield
		Client2.getContentPane().add(textField2); //add textfield to client frame
		textField2.setColumns(10);

		JTextArea textAreaClient2 = new JTextArea(2, 20); //creates a textarea to get the output printed on GUI
		PrintStream printStreamClient2 = new PrintStream(new CustomOutputStream(textAreaClient2));//Redirects console content to textarea
		System.setOut(printStreamClient2);// Re-assigns the standard output stream.
		System.setErr(printStreamClient2);//Re-assigns the standard error output stream.
		Client2.getContentPane().add(textAreaClient2);//Add the textarea to frame
		//printStreamClient2.flush();*/
		//Site referred: line 89 to line 97
		//https://www.tutorialspoint.com/how-to-set-action-command-to-jbutton-in-java
		//https://stackoverflow.com/questions/35094602/java-app-cant-be-closed-even-close-on-exit-tcp-server
		JButton btnSubmit2 = new JButton("Submit");//creates an instance for submit button
		btnSubmit2.setActionCommand("Start");//set command for action on button
		btnSubmit2.addActionListener(new ActionListener() {  //listen to the action on clicking the button
			public void actionPerformed(ActionEvent e) {  //Perform the action on event occurrence
				JOptionPane.showMessageDialog(null, "A Client named " + textField2.getText() +" is connected");//show message to connected client
				String cName1 = textField2.getText(); //get the clientname
				fc = new FileClient(textField2.getText());//passed the clientname to FileClient file
				String command = e.getActionCommand();
				if ("Start".equals(command)) {  //if the command is equal to the Start
					new Thread(new Runnable() { //implements runnable for the thread
						@Override
						public void run() {
							try {

								String directoryPath = "/Users/Ankit/Documents/Client2Directory/"; //sets directory path for the client
								fc.Start(directoryPath,textField2.getText()); //pass path of directory and clientname to start method of FileClient file
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
		btnSubmit2.setBounds(232, 97, 117, 29);
		Client2.getContentPane().add(btnSubmit2);//add the submit button to the frame
		//printStreamClient2.close();


	}
}
