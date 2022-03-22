import java.io.Console;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;
 
/**
 * This is the chat client program.
 * Type 'bye' to terminte the program.
 *
 * @author www.codejava.net
 */
public class ClientDist {
    private String hostname;
    private int port;
    private String userName;
    private int blocdId;
    private int roll_T;
    private boolean started;
    private String name = ""; 
    //static ChatClient client;
    //private PrintWriter writer;
    
    public ClientDist(String hostname, int port,String name) {
        this.hostname = hostname;
        this.port = port;
        this.started = false;
        this.name = name;
        
    }
 
    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            
            /*try {
                OutputStream output = socket.getOutputStream();
                writer = new PrintWriter(output, true);
            } catch (IOException ex) {
                System.out.println("Error getting output stream: " + ex.getMessage());
                ex.printStackTrace();
            }*/
            
            
            System.out.println("Connected to the chat server");
            
            Console console = System.console();
            
            /*userName = console.readLine("\nEnter your Request: ");
            client.setUserName(userName);
            //System.out.println(userName);
            writer.println(userName); */
            
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
 
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
 
    }
 
    void setUserName(String userName) {
        this.userName = userName;
    }
 
    String getUserName() {
        return this.userName;
    }
    
    void setblockId(int blockId) {
    	System.out.println("Setting Block ID" + blockId);
        this.blocdId = blockId;
    }
    
    int getblockId() {
    	System.out.println("Getting Block ID" + this.blocdId);
        return this.blocdId;
    	//return 9888;
    }
    
    
    void setThreadStarted(boolean started) {
    	System.out.println("Setting started flag" + started);
        this.started = started;
    }
    
    boolean isStarted() {
    	System.out.println("Getting started flag ID" + this.started);
        return this.started;
    	//return 9888;
    }
    
    
    void setname(String roll) {
    	System.out.println("Setting name ID" + roll);
        this.name = roll;
    }
    
    String getname() {
    	System.out.println("Getting name ID" + this.name);
        return this.name;
    	//return 9888;
    }
    
    void setroll_T(int roll) {
    	System.out.println("Setting roll ID" + roll);
        this.roll_T = roll;
    }
    
    int getroll_T() {
    	System.out.println("Getting roll ID" + this.roll_T);
        return this.roll_T;
    	//return 9888;
    }
    
    public static void main(String[] args) {
        if (args.length < 1) 
        	{
        	System.out.println("Please specify the Number of Clients");
        	return;
        	}
 
        //String hostname = args[0];
        //int no = Integer.parseInt(args[0]);
        int no = 3;
        System.out.println(" "+ " "+ no);
    	
    	
    	String[] names = {"Client 1","Client 2","Client 3","Client 4","Client 5","Client 6","Client 7","Client 8"};
    	//ChatClient[]  clientsss = {new ChatClient("localhost", 6523,"Client 1"),new ChatClient("localhost", 6523,"Client 2"),new ChatClient("localhost", 6523,"Client 3"),new ChatClient("localhost", 6523,"Client 4"),new ChatClient("localhost", 6523,"Client 5"),new ChatClient("localhost", 6523,"Client 6"),new ChatClient("localhost", 6523,"Client 7"),new ChatClient("localhost", 6523,"Client 8"),};
    	
    	ClientDist[]  clientsss = new ClientDist[no];
    	
    	for (int k=0 ; k<no;k++) {
    		
    		clientsss[k] = new ClientDist("localhost", 6523, names[k]);
    		
    		clientsss[k].execute();
    		try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	
        //ChatClient client = new ChatClient("localhost", 6523,"Client 1");
        //client.execute();
        
        
        //ChatClient clients = new ChatClient("localhost", 6523,"Client 2");
        //clients.execute();
        
        
    }
}