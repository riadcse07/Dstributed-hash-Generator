import java.io.*;
import java.net.*;
 
/**
 * This thread is responsible for reading server's input and printing it
 * to the console.
 * It runs in an infinite loop until the client disconnects from the server.
 *
 * @author www.codejava.net
 */
public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ClientDist client;
 
    public ReadThread(Socket socket, ClientDist client) {
        this.socket = socket;
        this.client = client;
 
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void run() {
        while (true) {
            try {
                String response = reader.readLine(); //RESPONSE from sERVER
                System.out.println("\n" + response);
                
                if (response.contains("9999")) {
                	socket.close();
                	System.exit(0); 
                }
                
                
                if (!response.contains("other")){
                	
                	if (response.length()<= 4){
                		int blockId=Integer.parseInt(response);
                		System.out.println(blockId+ "asdfasdfaf");
                        client.setblockId(blockId);
                	}
                	if (response.length()>= 10){
                		
                		
                		String[] arrOfStr = response.split(":", 2); 
                		  
                		int roll_T=Integer.parseInt(arrOfStr[0]);
                		System.out.println(roll_T);
                        client.setroll_T(roll_T);
                        
                        int blockId_T=Integer.parseInt(arrOfStr[1]);
                		System.out.println(blockId_T);
                        client.setblockId(blockId_T);
                        
                        
                        
                	}
                }
                
                // prints the username after displaying the server's message
                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: "); // jUST pRINT THE client name console 
                }
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}