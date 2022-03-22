import java.io.*;
import java.net.*;
import java.util.*;
 
/**
 * This thread handles connection for each connected client, so the server
 * can handle multiple clients at the same time.
 *
 * @author www.codejava.net
 */
public class UserThread extends Thread {
    private Socket socket;
    private ServerDist server;
    private PrintWriter writer;
    public boolean connected;
 
    public File file;
    public FileWriter fw;
    public BufferedWriter bw;
    
    
    public UserThread(Socket socket, ServerDist server) {
        this.socket = socket;
        this.server = server;
        this.connected = true;
    }
 
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            
            printUsers();
 
            String userName = reader.readLine();
            server.addUserName(userName);
            
            String serverMessage = "Total user connected: " + server.getUserNames().size();
            server.broadcast(serverMessage, this);
            
            String clientMessage;
 
            do {
                clientMessage = reader.readLine();
                //serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(clientMessage, this);
                //bw.write(serverMessage); 
 
            } while (!clientMessage.equals("bye"));
 
            server.removeUser(userName, this);
            socket.close();
 
            serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, this);
 
        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    /**
     * Sends a list of online users to the newly connected user.
     */
    void printUsers() {
        if (server.hasUsers()) {
        	
        	Random random = new Random();
        	String roll = "1018312015";
        	int val = random.nextInt(server.arrlist.size());
        	int blk = random.nextInt(server.arrlist.get(val));
        	String Block = Integer.toString(blk);
        	
        	String newString = roll + ":"+Block;
        	server.arrlist.remove(val);
        	System.out.println(newString);
            writer.println(newString);
            
        } else {
        	
        	Random random = new Random();
        	String roll = "1018312015";
        	int val = random.nextInt(server.arrlist.size());
        	int blk = random.nextInt(server.arrlist.get(val));
        	String Block = Integer.toString(blk);
        	
        	String newString = roll + ":"+Block;
        	server.arrlist.remove(val);
        	System.out.println(newString);
            writer.println(newString);
        }
    }
    
    /**
     * Sends a message to the client.
     */
    void sendMessage(int message) {
        writer.println(message);
    }
}