
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tuncaymsi
 */
public class encryption {
    
    
    
    public static SecretKey getSecretEncryptionKey() throws Exception{

        KeyGenerator generator = KeyGenerator.getInstance("AES");

        generator.init(128); // The AES key size in number of bits

        SecretKey secKey = generator.generateKey();

        return secKey;

    }
    
    public static byte[] encryptText(String plainText,SecretKey secKey) throws Exception{

        // AES defaults to AES/ECB/PKCS5Padding in Java 7

        Cipher aesCipher = Cipher.getInstance("AES");

        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);

        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());

        return byteCipherText;

    }
    
    public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {

        // AES defaults to AES/ECB/PKCS5Padding in Java 7

        try {
            Cipher aesCipher = Cipher.getInstance("AES");
            
            aesCipher.init(Cipher.DECRYPT_MODE, secKey);
            
            byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
            
            return new String(bytePlainText);
        } 
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        } catch (NoSuchPaddingException noSuchPaddingException) {
        } catch (InvalidKeyException invalidKeyException) {
        } catch (IllegalBlockSizeException illegalBlockSizeException) {
        } catch (BadPaddingException badPaddingException) {
        }
    return "";

    }
    
    static String  bytesToHex(byte[] hash) {

        return DatatypeConverter.printHexBinary(hash);

    }
    
    String sifrele(String text, String symKeyHex)
    {
        try {
            
            final byte[] symKeyData = DatatypeConverter.parseHexBinary(symKeyHex);
             final SecretKeySpec seck = new SecretKeySpec(symKeyData, "AES");
            
            encryption sifre = new encryption();
            
            String plainText = text;
            
           // SecretKey secKey = sifre.getSecretEncryptionKey();
             SecretKey secKey = seck;
            
            byte[] cipherText = sifre.encryptText(plainText, secKey);
            
            String decryptedText = sifre.decryptText(cipherText, secKey);
            
            System.out.println("Original Text:" + plainText);
            
            System.out.println("AES Key (Hex Form):"+ sifre.bytesToHex(secKey.getEncoded()));
            
            System.out.println("Encrypted Text (Hex Form):"+ sifre.bytesToHex(cipherText));
            
            System.out.println("Descrypted Text:"+decryptedText);
            
         return sifre.bytesToHex(cipherText);
         
        } catch (Exception ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    public static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
    }
    return data;
}
    
    String decoding(String text, String symKeyHex)
    {
        try {
          
            final byte[] symKeyData = DatatypeConverter.parseHexBinary(symKeyHex);
             final SecretKeySpec seck = new SecretKeySpec(symKeyData, "AES");
            
             
            byte[] bytestring = hexStringToByteArray(text);
            
            String decryptedText = decryptText(bytestring, seck);
            
      
            System.out.println("Descrypted Text:"+decryptedText);
            
         return decryptedText;
         
        } catch (Exception ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }




    
}
