//Student Name: Aditee Dnyaneshwar Dakhane
//Student ID: 1001745502

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClickListener implements ActionListener{

	MyServer server;
	public ButtonClickListener() {
		server = new MyServer();  //creates instance of MyServer class
	}
	//Site referred:: https://www.tutorialspoint.com/how-to-set-action-command-to-jbutton-in-java
	//Site::https://stackoverflow.com/questions/35094602/java-app-cant-be-closed-even-close-on-exit-tcp-server
   //For line 18 to line 25
	@Override
	public void actionPerformed(ActionEvent e) { //performs action on clicking the button
		String command = e.getActionCommand();  //set command for action on button
		if ("Start".equals(command)) {     //if the command is equal to the Start
			new Thread(new Runnable() {    //implements runnable for the thread
				@Override
				public void run() {
					try {
						server.start();   //calls the start method of MyServer class
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();   //catches exception
					}
				}
			}).start();
		}
	} 
}



