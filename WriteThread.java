import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
 
/**
 * This thread is responsible for reading user's input and send it
 * to the server.
 * It runs in an infinite loop until the user types 'bye' to quit.
 *
 * @author www.codejava.net
 */
public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ClientDist client;
    
    
    // MD5 Generator and hashing check
    
    String T = "";
	Integer nonce = 0;
	
	
	public static String getMd5(String input) 
    { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
  
	public static String getValueof_S (String T){
		String lastFourDigits = "";     //substring containing last 4 characters
		 
		if (T.length() > 3) 
		{
		    lastFourDigits = T.substring(T.length() - 3);
		} 
		else
		{
		    lastFourDigits = T;
		}
		 
	return "00"+lastFourDigits;
		
	}
	
	public static String getFirstFiveCharacters (String M){
		String lastFourDigits = "";     //substring containing last 4 characters
		 
		if (M.length() > 5) 
		{
		    lastFourDigits = M.substring(0, 5);
		} 
		else
		{
		    lastFourDigits = M;
		}
		 
	return lastFourDigits;
		
	}
	
	
	public static ArrayList<String> keychecker (int t , int blockID){
		
		
		ArrayList<String> arrayList = new ArrayList<>();
		
		String T = Integer.toString(t); 
		
		String S_Value = getValueof_S(T);
		//System.out.println(S_Value);
		
		System.out.println(blockID);
		long  i = (blockID-1)*4194304 + 1;
		//System.out.println(i); 
		long range = i + 4194304-1;
		
		//System.out.println(range);
		String md5_hash_M = "";
		
		for (long j = i ; j< range;j++){
			
			String V =T+Long.toString(j);
			//System.out.println(V);
			md5_hash_M = getMd5(V);
			//System.out.println(md5_hash_M);
			String First_five = getFirstFiveCharacters(md5_hash_M);
			
			if (S_Value.equalsIgnoreCase(First_five)){
				//System.out.println(md5_hash_M);
				arrayList.add(md5_hash_M);
			}
				
		}
		
		return arrayList;
	}
    
    
    // MD5 generator checking closing
    
    
    
    
    
    
    
    
    
    // Constructor
    public int MD5(int blockId)  
    {
        return blockId; 
    }
    
    public WriteThread(Socket socket, ClientDist client) {
        this.socket = socket;
        this.client = client;
 
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void run() {
 
        /*Console console = System.console();
 
        String userName = console.readLine("\nEnter your name: ");
        client.setUserName(userName);
        //System.out.println(userName);
        writer.println(userName);
 
        String text = null;
        
       int i =0;
        while (true){
        	//writer.println(MD5(client.getroll_T()));
        	
        	ArrayList<String> arr = keychecker (client.getroll_T() , 789);
        	
        	for (int counter = 0; counter < arr.size(); counter++) { 		      
                //System.out.println(arr.get(counter));
                writer.println(arr.get(counter));
            } 
        	//writer.println(keychecker (client.getroll_T() , 789));
        	
        	//i++;
        	//if (i==10)
        		break;
        }*/
        
    	int i =0;
    	String userName = "";
        do {
            //text = console.readLine("[" + userName + "]: ");
            //writer.println(text);
            
            Console console = System.console();
            
            
            if (client.isStarted()==false) {
            	//userName = console.readLine("\nEnter your Request: ");
                //client.setUserName(userName);
                 //System.out.println(userName);
                //writer.println(userName);
            	writer.println(client.getname());
                client.setThreadStarted(true);
            }
           
            else {
            	//userName = console.readLine("\nEnter your Request: ");
                //client.setUserName(userName);
                 //System.out.println(userName);
                //writer.println("Next");
                
            }
     
            String text = null;
            
           
            while (true){
            	//writer.println(MD5(client.getroll_T()));
            	
            	ArrayList<String> arr = keychecker (client.getroll_T() , client.getblockId());
            	//System.out.println(arr);
            	writer.println("["+ client.getname()+"]"+arr);
            	
            	String compose_message = "";
            	StringBuilder sb = new StringBuilder();
            	
            	/*for (int counter = 0; counter < arr.size(); counter++) { 		      
                    //System.out.println(arr.get(counter));
                    //writer.println("["+ client.getname()+"]"+arr.get(counter));
                    sb.append("["+ client.getname()+"]"+arr.get(counter));
                    compose_message = sb.toString();
                    compose_message = compose_message + "\r\n"; 
                    
                    //writer.println("Success");
                } 
            	writer.println(compose_message);*/
            	writer.println("Next");
            	
            	//writer.println(keychecker (client.getroll_T() , 789));
            	
            	i++;
            	//if (i==10)
            		break;
            }
                
        } while (i!=1024);  
    }
}