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

public class Frame3GUI {

	FileClient fc;//Declare instance of class FileClient


	private JFrame Client3;//Declare variable for JFrame


	private JTextField textField3;//declare variable for textField
	/* Most of the code is auto - generated as windows builder is used to create GUI*/



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {//updates GUI at the end
			public void run() {
				try {
					Frame3GUI window = new Frame3GUI();//creates an instance of class Frame2GUI

					window.Client3.setVisible(true);//frame is made visible 
				} catch (Exception e) {
					e.printStackTrace();//Catches exception
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame3GUI() {
		initialize();//Constructor that calls the initialize method of the JFrame
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		//Constructor that calls the initialize method of the JFrame
		Client3 = new JFrame();//creates frame for Client GUI
		Client3.setBounds(100, 100, 450, 300);//sets frame size
		Client3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit the operation when click on the 'X' of the frame
		Client3.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));//provides layout to our frame

		JLabel lblNewLabel3 = new JLabel("Enter Username");//Creates label to enter username
		lblNewLabel3.setBounds(91, 64, 109, 21);//set size for label
		Client3.add(lblNewLabel3);//add label to client frame

		textField3 = new JTextField();//creates textfield to enter the value for username
		textField3.setBounds(219, 59, 130, 26);//sets size for textfield
		Client3.getContentPane().add(textField3);//add textfield to client frame
		textField3.setColumns(10);

		JTextArea textAreaClient3 = new JTextArea(2, 20);//creates a textarea to get the output printed on GUI
		PrintStream printStreamClient3 = new PrintStream(new CustomOutputStream(textAreaClient3));//Redirects console content to textarea
		System.setOut(printStreamClient3);// Re-assigns the standard output stream.
		System.setErr(printStreamClient3);//Re-assigns the standard error output stream.
		Client3.getContentPane().add(textAreaClient3);//Add the textarea to frame
		//Site referred: line 86 to line 94
		//https://www.tutorialspoint.com/how-to-set-action-command-to-jbutton-in-java
		//https://stackoverflow.com/questions/35094602/java-app-cant-be-closed-even-close-on-exit-tcp-server
		JButton btnSubmit3 = new JButton("Submit");//creates an instance for submit button
		btnSubmit3.setActionCommand("Start");//set command for action on button
		btnSubmit3.addActionListener(new ActionListener() { //listen to the action on clicking the button
			public void actionPerformed(ActionEvent e) {  //Perform the action on event occurrence
				JOptionPane.showMessageDialog(null, "A Client named " + textField3.getText() +" is connected");//show message to connected client
				String cName2 = textField3.getText();//get the clientname
				fc = new FileClient(textField3.getText());//passed the clientname to FileClient file
				String command = e.getActionCommand();
				if ("Start".equals(command)) { //if the command is equal to the Start
					new Thread(new Runnable() {  //implements runnable for the thread
						@Override
						public void run() {
							try {
								String directoryPath = "/Users/Ankit/Documents/Client3Directory/"; //sets directory path for the client
								fc.Start(directoryPath,textField3.getText()); //pass path of directory and clientname to start method of FileClient file
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
		btnSubmit3.setBounds(232, 97, 117, 29);
		Client3.getContentPane().add(btnSubmit3);//add the submit button to the frame
		//printStreamClient3.close();



	}
}
