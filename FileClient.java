//Student Name: Aditee Dnyaneshwar Dakhane
//Student ID: 1001745502


import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

//import net.contentobjects.jnotify.JNotify;
//import net.contentobjects.jnotify.JNotifyListener;

import java.io.*;

public class FileClient {

	private Socket sock;                     //Declare a variable of type socket for establishing connection of client over socket
	private String clientName;               //Declare a variable name clientName for storing the name of clients obtained from GUI 


	public FileClient(String clientName) {      //Constructor to initialize the clientName variable by passing the value of argument obtained from GUI
		// TODO Auto-generated constructor stub
		this.clientName = clientName;

	}

	public void Start(String directoryPath,String cName) throws IOException, InterruptedException{
		/*Client connects to the socket. Pass the name of the client connected to the server. Then directory is watched 
		 * for any kind of file modification. As soon as a file is modified in the directory, that file is uploaded to server
		 * using sendfile method*/


		sock = new Socket("localhost", 13267);// Connecting to socket on the localhost with given port number

		System.out.println("Client named:" +cName+ "- is connected");  //Print the name of the client connected
		DataOutputStream dosname = new DataOutputStream(sock.getOutputStream());  // Creates a new data output stream to write data
		dosname.writeUTF(cName);     //Writes the name of the client to be read by Server
		dosname.flush();             //Flush the outputstream


		String serverDirectory="/Users/Ankit/Documents/ServerDirectory/";
		watcherService(directoryPath,serverDirectory);     //Call the watchservice method for any file modification


	}
	//Site: https://www.baeldung.com/java-nio2-watchservice
	private void watcherService(String directoryPath, String serverDirectory) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		int rand_int = 0;
		WatchService watchService
		= FileSystems.getDefault().newWatchService();  //WatchService Instance is created using java.nio.file.FileSystems class:

		Path path = Paths.get(directoryPath);     //Path to the Client Directory to be monitored
		path.register(watchService,	StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE);  //Event is triggered whenever a file is modified in the client directory

		DataOutputStream dsout = new DataOutputStream(sock.getOutputStream());
		DataInputStream dis= new DataInputStream(sock.getInputStream());
		WatchKey key;
		while (true) {     
			try {


				key = watchService.poll(30, TimeUnit.SECONDS);  //blocks for timeout units to give more time within which an event may occur instead of returning null right away.

				if (key == null) {

					String request = dis.readUTF();
					System.out.println(request); //read the coordinator message
                
					int i = dis.readInt();
					if(i==1){
						//TimeUnit.SECONDS.sleep(3);
						System.out.println("I vote to COMMIT");  //give the vote
						System.out.println("------------------------------------------");
					}
					else{
						//TimeUnit.SECONDS.sleep(3);
						System.out.println("I vote to ABORT");    //give the vote
						System.out.println("------------------------------------------");
					}


				} 
				else
				{ 
					for (WatchEvent<?> event : key.pollEvents()) {      // gives the next queued watch key any of whose events have occurred or null if no registered events have occurred

						if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE){

							System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");  //Print the event occurred and the file affected	
							String filepath1 = directoryPath + event.context();      //Get the file path
							String serverpath = serverDirectory + event.context();    //Get the serverfile
							sendFile(filepath1,serverpath);            //call sendFile to upload modified file to server

						}
						if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE){

							System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");  //Print the event occurred and the file affected
							String filepath2 = directoryPath + event.context();

							dsout.writeByte(1);
							dsout.writeUTF(filepath2);    //send the deleted file path to output stream
							dsout.flush();

						}




					}

				}
				boolean valid = key.reset();         //watchkey instance is put back in the queue for more events
				if (!valid) {
					break;
				} 
			} catch (Exception e) {
				// TODO: handle exception
				//System.out.println("Server Exception:"+e.getMessage());  //Catch exception when thrown
			}



		}
	}





	//Site for this section of code:https://stackoverflow.com/questions/9235401/java-copy-file-to-another-directory-using-fileutils-and-copyfiletodirectory
	private void sendFile(String filepath1, String serverpath) throws IOException {


		File serverFile = new File(serverpath);
		File clientFile = new File(filepath1);
		if(!serverFile.exists()){            //if file not exists on server
			FileUtils.copyFile(clientFile, serverFile);   //upload file from client to server
			System.out.println("File uploaded to server from client.");
			System.out.println("------------------------------------------");
			OutputStream os = sock.getOutputStream();           
			DataOutputStream dos = new DataOutputStream(os);  
			dos.writeUTF(clientName);   //send clientname to outputstream
			dos.writeUTF(filepath1);   //send uploaded filepath to outputstream
		}
		else{
			System.out.println("---------------------------------------");
		}



	}

}
















