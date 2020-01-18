//Student Name: Aditee Dnyaneshwar Dakhane
//Student ID: 1001745502

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ServerFrame {

	private JFrame frame;    //declare variable for JFrame
	
	/**
	 * Launch the application.
	 */
	/* Most of this code is auto - generated as windows builder is used to create GUI*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {           //updates GUI at the end
			public void run() {
				try {
					ServerFrame window = new ServerFrame();    //creates an instance of class ServerFrame
					window.frame.setVisible(true);             //frame is made visible 
				} catch (Exception e) {
					e.printStackTrace();                 //Catches exception
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerFrame() {  //Constructor that calls the initialize method of the JFrame
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @return 
	 */
	private JFrame initialize() {
		
		/* All of this code is auto - generated as windows builder is used to create GUI*/
		frame = new JFrame();                           //creates frame for server GUI
		frame.setBounds(100, 100, 450, 300);              //sets frame size
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         //exit the operation when click on the 'X' of the frame
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));    //provides layout to our frame

		ButtonClickListener buttonListener = new ButtonClickListener();   //notifies whenever u click on the button

		JPanel client = new JPanel();                 //creates instance of jpanel
		client.setLayout(new FlowLayout());          //sets layouts to the panel

		JTextArea textArea = new JTextArea(10, 40);            //creates a textarea to get the output printed on GUI
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));    //Redirects console content to textarea  
		System.setOut(printStream); // Re-assigns the standard output stream.      
		System.setErr(printStream);  //Re-assigns the standard error output stream.
        frame.add(textArea);           //Add the textarea to frame
        
		JButton start = new JButton("Start");    //creates instance for JButton  
		start.setActionCommand("Start");         //set command for action on button
		start.addActionListener(buttonListener);    //listen to the action on clicking the button
		frame.add(start);               //add the button to frame

	

		return frame;  //return frame with all the components added
	}

}
