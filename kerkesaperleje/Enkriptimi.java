/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kerkesaperleje;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author Iliriani
 */
public class Enkriptimi {
    private String str;
    
        
    public String enc(String s) {
       try{ String plaintext = s;
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(plaintext.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while(hashtext.length() < 32 ){
        hashtext = "0"+hashtext;
        
        }
            str=hashtext;
            System.out.println(str);
    }
      
    catch(Exception ex){ex.printStackTrace();}
       return str;
    }
    


      
    public static void main(String[]a){
        Enkriptimi e=new Enkriptimi();
        try {e.enc("123");}
        catch(Exception ex){ex.printStackTrace();}
    }
    
    
}
