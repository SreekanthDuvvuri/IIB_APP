package encDecypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;



public class EncyptionDecryption {
	
	public static SecretKeySpec secretkey;
	private static byte[] key;
	
	public static void setkey(String myKey) {
		MessageDigest sha = null;
		
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key , 16);
			secretkey =new SecretKeySpec(key, "AES");
			
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
		public static String encrypt(String strToEnc , String sec) {
			try {
				setkey(sec);
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(cipher.ENCRYPT_MODE,secretkey);
				return Base64.getEncoder().encodeToString(cipher.doFinal(strToEnc.getBytes("UTF-8")));

			
				
			} catch (Exception e) {
				System.out.println("Error while encryption" + e.toString());
			}
			return null;
			
		}
		
		public static String decrypt(String strToDec , String sec) {
			try {
				setkey(sec);
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(cipher.DECRYPT_MODE,secretkey);
				return new String(cipher.doFinal(Base64.getDecoder().decode(strToDec)));
				
			} catch (Exception e) {
				System.out.println("Error while Decryption" + e.toString());
			}
			return null;
			
		}
	

}
