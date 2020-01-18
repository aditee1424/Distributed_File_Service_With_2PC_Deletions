//Student Name: Aditee Dnyaneshwar Dakhane
//Student ID: 1001745502

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;


public class MyServer {


	int i=1;              //Declaring variable i to keep count of each incoming client request
	String cName;         // Declaration of varible to store client Name.
	List<Thread> threads = new ArrayList<>();// Declaring List to manage list of threads for every client getting connected to server
	ArrayList<String> clientnames = new ArrayList<>();// Declaration of ArrayList to store the names of clients.
	Map<Integer, java.net.Socket> mapping = new HashMap<>();


	public void start() throws InterruptedException {
		/*In This method, Server starts running on a port number.Server accepts connection with all the three clients.
		 *Server gets username of all the clients and check whether the username of any client matches with any 
		 *other client. Socket connection is closed when username matches. Else threads are started for each client.
		 *Runnable method is implemented to get modified file from clients in the Server Directory*/
		try (ServerSocket serverSocket = new ServerSocket(13267)) //Server starts running on Port number 13267  //Site :: https://coderanch.com/t/556838/java/Transferring-file-file-data-socket
		{
			System.out.println("Server is listening on port ");
			while (true) {
				Socket clientSocket = serverSocket.accept(); //Server accepts connection with client
				Integer portaddress = clientSocket.getPort();
				//System.out.println("PortAddress current:"+portaddress);


				DataInputStream disname = new DataInputStream(clientSocket.getInputStream()); // Instance of DataInputStream is created to read data from client's inputstream
				cName = disname.readUTF(); //reads the data written by client in the outputstream and stores the data in variable cName 

				if(i==1) //If condition for first client
				{

					clientnames.add(cName);                                  //Add the name of client to arraylist of clientnames
					String clientname = cName;                               // Store the name of client read from outputstream in a temporary variable called clientname
					System.out.println("Clientname :"+cName+" :is connected");  //Print the name of client on server side
					System.out.println("---------------------------------");
					System.out.println("Clients:\n"+clientnames);                 //Print dynamic arraylist of clientnames
					System.out.println("---------------------------------");
					System.out.println("Client has unique name..go ahead");              // It is not duplicate as it is the first thread
					mapping.put(portaddress,clientSocket);                   //Pass portaddress and clientsocket to hashmap
					cName = null;                                            //empty the cName variable to store the name of next Client.
					Thread t = new ServerThread(clientSocket,mapping,clientname);    // Creates a thread for client having unique name
					threads.add(t);                                         //Add that thread to the list of threads
					t.start();                                            //Start the thread method implementing runnable


				}
				if(i>1 && cName!= null)               //If it is not first client then check for duplicate username
				{
					if(clientnames.contains(cName))     //If arraylist of clientnames contains the current clientname
					{                                   //Site: https://stackoverflow.com/questions/8936141/java-how-to-compare-strings-with-string-arrays
						System.out.println("ClientName already present--Submit name again");

						DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());  //Creates instance of dataoutputstream to write the duplicate name of client
						dos.writeUTF(cName);       //writes the name of client which then be read by client side by datainputstream
						cName = null;              //empty the cName variable to store the name of next client.
						//clientSocket.close();     //Username is duplicate so close the connection of socket for that client.

					}else{
						clientnames.add(cName);                              //Add the name of client to arraylist of clientnames
						String client_name = cName;                             //Store the name of client read from outputstream in a temporary variable called clientname
						System.out.println("Clientname :"+cName+" :is connected"); //Print the name of client connected with unique name
						System.out.println("---------------------------------");
						System.out.println("Clients:\n"+clientnames);             //Print the dynamic arraylist of clientnames
						System.out.println("---------------------------------");
						System.out.println("Client has unique name ....go ahead");
						mapping.put(portaddress,clientSocket);
						cName = null;                                           //empty the cName variable to store the name of next client.
						Thread t = new ServerThread(clientSocket,mapping,client_name);  // Creates a thread for client having unique name
						threads.add(t);                                        //Add that thread to the list of threads
						t.start();                                             //Start the thread method implementing runnable
					}

				}

				i++;         //Increment counter i for each coming client request.

			}



		} catch (IOException ex) {
			//	System.out.println("Server exception: " + ex.getMessage());   //Catches an exception if one of the client disconnects
			//	ex.printStackTrace();
		}
	}

}
class ServerThread extends Thread {
	private Socket clientSocket;      //Declare variable of type Socket.
	int bytesRead;                    //Declare variable to read bytes
	private String clientName;        //Declare a string to store Client name
	Map<Integer, java.net.Socket> mapping = new HashMap<>(); //Hashmap for port address and socket
	ArrayList<Integer>votes = new ArrayList<>();

	//Constructor ServerThread having parameters Socket and String
	public ServerThread(Socket clientSocket,Map<Integer,java.net.Socket> mapping, String clientName) throws IOException {    
		this.clientSocket = clientSocket;                                              //Initialize clientSocket variable declared by passing the values of arguments
		this.mapping = mapping;														//Initialize hashmap declared by passing the values of arguments
		this.clientName = clientName;                                                 //Initialize clientName variable declared by passing the values of arguments
	}
	//Site Referred for following section of code:https://stackoverflow.com/questions/36849197/send-message-to-specific-clients-using-java
	public void run() {   //Run method that contains the code executed by each thread

		DataInputStream dname;


		try {
			while(true){
				dname = new DataInputStream(clientSocket.getInputStream());  //Get the inputstream to read the data sent from client

				String clientName = dname.readUTF();          //Get the clientname from the inputstream

				String clientpath = dname.readUTF();         //Get the filepath of client

				Path path = Paths.get(clientpath);
				Path fileName = path.getFileName();         //Get the filename from filepath
				String fname = fileName.toString();        //convert the filename to string format

				if(!clientName.isEmpty()){
					System.out.println("File"+" "+fname+" "+"has been received from client named:"+clientName); //Print the message of update

					 //Set the client paths and server path
					String clientPath1="/Users/Ankit/Documents/Client1Directory/"+fname;

					String clientPath2="/Users/Ankit/Documents/Client2Directory/"+fname;

					String clientPath3="/Users/Ankit/Documents/Client3Directory/"+fname;

					String serverPath ="/Users/Ankit/Documents/ServerDirectory/"+fname;

					/*If the path is same as client1 path then download files in client2 and client3*/
					if(clientpath.contains(clientPath1)){
						File serverFile = new File(serverPath);   //creation of fileobject for server file
						File clientFile = new File(clientPath2);  //creation of fileobject for client file
						if(!clientFile.exists()){                  //check if file exists at destination
							FileUtils.copyFile(serverFile,clientFile);  //fileutils method to download file from server to client
							System.out.println("File"+" "+fname+" "+"downloaded by client2 from server.");	
						}
						else{
							System.out.println("-------------------------------------------");
						}
						

						File serverFile1 = new File(serverPath); //creation of fileobject for server file
						File clientFile1 = new File(clientPath3);  //creation of fileobject for client file
						if(!clientFile1.exists()){                 //check if file exists at destination
							FileUtils.copyFile(serverFile1,clientFile1); //fileutils method to download file from server to client
							System.out.println("File"+" "+fname+" "+"downloaded by client3 from server.");
						}else{
							System.out.println("-------------------------------------------");
						}
						
					}
					/*If the path is same as client1 path then download files in client2 and client3*/
					if(clientpath.contains(clientPath2)){
						File serverFile = new File(serverPath); //creation of fileobject for server file
						File clientFile = new File(clientPath1); //creation of fileobject for client file
						if(!clientFile.exists()){                //check if file exists at destination
							FileUtils.copyFile(serverFile,clientFile);//fileutils method to download file from server to client
							System.out.println("File"+" "+fname+" "+"downloaded by client1 from server.");
						}else{
							System.out.println("-------------------------------------------");
						}
						

						File serverFile1 = new File(serverPath);//creation of fileobject for server file
						File clientFile1 = new File(clientPath3);//creation of fileobject for client file
						if(!clientFile1.exists()){                //check if file exists at destination
							FileUtils.copyFile(serverFile1,clientFile1);//fileutils method to download file from server to client
							System.out.println("File"+" "+fname+" "+"downloaded by client3 from server.");
						}else{
							System.out.println("-------------------------------------------");
						}
					
					}
					if(clientpath.contains(clientPath3)){ 
						File serverFile = new File(serverPath);//creation of fileobject for server file
						File clientFile = new File(clientPath2);//creation of fileobject for client file
						if(!clientFile.exists()){                       //check if file exists at destination
							FileUtils.copyFile(serverFile,clientFile);//fileutils method to download file from server to client
							System.out.println("File"+" "+fname+" "+"downloaded by client2 from server.");
						}
						else{
							System.out.println("-------------------------------------------");
						}

						File serverFile1 = new File(serverPath);//creation of fileobject for server file
						File clientFile1 = new File(clientPath1);//creation of fileobject for client file
						if(!clientFile1.exists()){                    //check if file exists at destination
							FileUtils.copyFile(serverFile1,clientFile1);//fileutils method to download file from server to client
							System.out.println("File"+" "+fname+" "+"downloaded by client1 from server.");
						}else{
							System.out.println("-------------------------------------------");
						}
					
					}

				}

				int c= dname.readByte();
	
/*Below part of code will work when co-ordinator deletes a file from its directory*/
				if(c==1){                 
                  
					Integer portadd = clientSocket.getPort();          //Get the port address of coordinator

					for (Iterator<Integer> iter = mapping.keySet().iterator(); iter.hasNext(); )   //Iterate through the hashmap
					{
						int key = iter.next();
						//If current client's port address is not same as one in hashmap send the message 
						
						//Below code will work for participants
						if(portadd!=key){
							java.net.Socket clientSocket = mapping.get(key); 
							//Set the socket for the remaining two clients
							
							OutputStream os = clientSocket.getOutputStream();      
							DataOutputStream dos = new DataOutputStream(os);     //Use outputstream to send the message of invalidation to clients
							String message="Coordinator is"+" "+clientName+"\n"+"INSTRUCTION:Delete file"+"\n"+"Co-ordinator requesting for vote";
							
							dos.writeUTF(message);                               //Write the message to the outputstream
							
					
							Random rand = new Random();                        //Generate random probability to vote
							int rand_int = rand.nextInt(2);                    
							dos.writeInt(rand_int); 
							votes.add(rand_int);                              //send random votes

							
							if(votes.size()==2){
								for (int i = 0; i < votes.size()-1; i++) 
									for (int k = i+1; k < votes.size(); k++) 
										if(votes.get(i)==1 && votes.get(k)==1){         //check if both votes are 1 
											System.out.println("Votelist:"+votes.get(i)+","+votes.get(k));
											System.out.println("GLOBAL COMMIT");           //if vote is 1 then it is commit
											
											String deletedfpath=dname.readUTF();//read the deleted file path from input stream
											

											Path deletef = Paths.get(deletedfpath); 
											Path dfname1 = deletef.getFileName();      //get the name of deleted file from co-ordinator
											String dfname =dfname1.toString();        //convert the name to string
											System.out.println("File to be deleted:"+dfname);   
					
											//set path of client to delete file from
											String fpath1="/Users/Ankit/Documents/Client1Directory/"+dfname;  
											String fpath2="/Users/Ankit/Documents/Client2Directory/"+dfname;
											String fpath3="/Users/Ankit/Documents/Client3Directory/"+dfname;


											//If client1 is coordinator delete files from client2 and client3 
											if(deletedfpath.contains(fpath1)){
												File clientfile2 = new File(fpath2);
												FileUtils.forceDelete(clientfile2);     
												System.out.println("File"+" "+dfname+" "+"deleted from client2");
												File clientfile3 = new File(fpath3);
												FileUtils.forceDelete(clientfile3);
												System.out.println("File"+" "+dfname+" "+"deleted from client3");
											}
											//If client1 is coordinator delete files from client1 and client3 
											if(deletedfpath.contains(fpath2)){
												File clientfile1 = new File(fpath1);
												FileUtils.forceDelete(clientfile1);
												System.out.println("File"+" "+dfname+" "+"deleted from client1");
												File clientfile3 = new File(fpath3);
												FileUtils.forceDelete(clientfile3);
												System.out.println("File"+" "+dfname+" "+"deleted from client3");
											}
											//If client1 is coordinator delete files from client1 and client2 
											if(deletedfpath.contains(fpath3)){
												File clientfile2 = new File(fpath2);
												FileUtils.forceDelete(clientfile2);
												System.out.println("File"+" "+dfname+" "+"deleted from client2");
												File clientfile1 = new File(fpath1);
												FileUtils.forceDelete(clientfile1);
												System.out.println("File"+" "+dfname+" "+"deleted from client1");
											}

										}else{   //if either of the vote is 0
											System.out.println("Votelist:"+votes.get(i)+","+votes.get(k));
											System.out.println("GLOBAL ABORT");
											
											String deletedfpath=dname.readUTF(); //read the deleted file path from input stream
											
											Path deletef = Paths.get(deletedfpath); 
											Path dfname1 = deletef.getFileName(); //get the name of deleted file
											String dfname =dfname1.toString();   //convert the name to string
											System.out.println("File to be deleted:"+dfname);
									
											//set filepath to restore file
											String fp1="/Users/Ankit/Documents/Client1Directory/"+dfname;
											String fp2="/Users/Ankit/Documents/Client2Directory/"+dfname;
											String fp3="/Users/Ankit/Documents/Client3Directory/"+dfname;
											String sp="/Users/Ankit/Documents/ServerDirectory/"+dfname;
										
											//restore file for client1 from client2
											if(deletedfpath.contains(fp1)){
												File sf = new File(fp2);
												File cf = new File(fp1);
												FileUtils.copyFile(sf,cf);
												System.out.println("File restored for client1");  
											}
											//restore file for client2 from client1
											if(deletedfpath.contains(fp2)){
												File sf = new File(fp1);
												File cf = new File(fp2);
												FileUtils.copyFile(sf,cf);
												System.out.println("File restored for client2");  
											}
											//restore file for client3 from client2
											if(deletedfpath.contains(fp3)){
												File sf = new File(fp2);
												File cf = new File(fp3);
												FileUtils.copyFile(sf,cf);
												System.out.println("File restored for client3");  
											}
										}

							}
		

						}


					}


				}


			}


		} catch (IOException e) {

			
		} 

	}


}
