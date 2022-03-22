import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
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
	
	
	public static String keychecker (String T , int blockID){
		
		String S_Value = getValueof_S(T);
		System.out.println(S_Value);
		
		System.out.println(blockID);
		long  i = (blockID-1)*4194304 + 1;
		//System.out.println(i); 
		long range = i + 4194304-1;
		
		for (long j = i ; j< range;j++){
			
			String V =T+Long.toString(j);
			//System.out.println(V);
			String md5_hash_M = getMd5(V);
			//System.out.println(md5_hash_M);
			String First_five = getFirstFiveCharacters(md5_hash_M);
			
			if (S_Value.equalsIgnoreCase(First_five)){
				System.out.println(md5_hash_M);
			}
				
		}
		
		return "";
	}
	   // Constructor
    public MD5(String T , Integer nonce)  
    {
        this.T = T;
        this.nonce = nonce;
        
    }
    
	public static void main(String[] args) throws IOException  
	{
		
		System.out.println(keychecker("1018312015",725));
		
	}

}

