import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
 
/**
 * This is the chat server program.
 * Press Ctrl + C to terminate the program.
 *
 * @author www.codejava.net
 */
public class ServerDist {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
    public ArrayList<Integer> arrlist = new ArrayList<Integer>();
    //public FileWriter myWriter ;
    File file;
    BufferedWriter br;
    FileWriter fr;
    long start;
    long finalTime;
    
    public ServerDist(int port) {
        this.port = port;
    }
    
    @SuppressWarnings("resource")
	public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
        	
        	for (int j=1;j<=1024;j++){
        		arrlist.add(j); 
        	}
        	        	
        	file = new File("append.txt");
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            
        	
            System.out.println("Chat Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");
                start = System.currentTimeMillis();
                
                
                
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
 
            }
            
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        /*if (args.length < 1) {
            System.out.println("Syntax: java ChatServer <port-number>");
            System.exit(0);
        }*/
    	
        //int port = Integer.parseInt(args[0]);
        
        ServerDist server = new ServerDist(2222);
        server.execute();
    }
    
    /**
     * Delivers a message from one user to others (broadcasting)
     */
    void broadcast(String message, UserThread excludeUser) {
    	
    	//System.out.println("\n"+ message + " this message is getting in server" + excludeUser.getName());
    	System.out.println("\n"+ message);
    	
    	
    	
    	try {
    		
    		if(!message.contains("Next")){
    			br.write(message);
    			br.newLine();

    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    		for (UserThread aUser : userThreads) {
                if (aUser == excludeUser) {
                	if (excludeUser.connected){
                		//aUser.sendMessage(1018312015);
                		excludeUser.connected = false;
                	}
                	else {
                		
                		
                		if(arrlist.isEmpty()==false) {
                			
                			
                			if (message.contains("Next")) {
                				aUser.sendMessage(arrlist.get(0));
                        		//System.out.println(arrlist.get(0));
                				try {
    								TimeUnit.MINUTES.sleep(2);
    							} catch (InterruptedException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
                        		arrlist.remove(0);
                			}
                			
                			
                			
                		}
                		else {
                			aUser.sendMessage(9999);
                			try {
                				
                				
                				finalTime = System.currentTimeMillis() - start;
                				br.newLine();
                				br.write("Time :" + (int) finalTime);
                				System.out.println("\r\nTime (Miliseconds) :"+ finalTime);
								br.close();
								fr.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                			
                			System.exit(0);
                		}
                		
                		
                		
                	}
                    
            }
    	}
    	
        
    }
    
    /**
     * Stores username of the newly connected client.
     */
    void addUserName(String userName) {
        userNames.add(userName);
    }
    
    /**
     * When a client is disconneted, removes the associated username and UserThread
     */
    void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }
 
    Set<String> getUserNames() {
        return this.userNames;
    }
 
    /**
     * Returns true if there are other users connected (not count the currently connected user)
     */
    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}