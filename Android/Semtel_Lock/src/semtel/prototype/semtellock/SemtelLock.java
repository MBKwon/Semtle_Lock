package semtel.prototype.semtellock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import net.imcore.AES256Cipher;

public class SemtelLock {
	
	File SAVE_PATH = Environment.getExternalStorageDirectory();
	
	//const
	String TRUE = "true";
	String FALSE = "false";
	String IS_LOCK_FILE = "islock.dat";
	String PASSWORD_FILE = "password.dat";
	
	String SEMTELOCK = "SemtelLock";
	
	//
	String key;
	//String encodedPassword;
	//Boolean isLock;
	
	Context appContext;
	
	public SemtelLock(Context context){
		
		//to do : key and encodedPassword load in file
		//encodedPassword = loadPassword();
		
		//isLock = false;
		key = "abcdefghijklmnopqrstuvwxyz123456";
		appContext = context;
		
		File isLockFile = new File(IS_LOCK_FILE);
		File passwordFile = new File(PASSWORD_FILE);
		
		if(!isLockFile.exists()){
			try {
				isLockFile.createNewFile();
				setUnLock();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!passwordFile.exists()){
			try {
				passwordFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
	
	/**
	 * 
	 * @param pramPassword
	 * @return 
	 */
	public boolean setPassword(String pramPassword){
		
		if(pramPassword==null || pramPassword.equals("")==true ){
			return false;
		}
		
		try {
			
			//apply AES256 
			String encodedPassword = AES256Cipher.AES_Encode(pramPassword, key);
			
			storePassword(encodedPassword);
		
		}catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	/**
	 * 입력한 패스워드가 맞는지 검사한다.
	 * @param paramPassword
	 * @return 패스워드가 일치하면 true, 틀리면 false, 오류발생시 null
	 */
	public Boolean isRight(String paramPassword){
		//TO DO...
		//AES256 암호화 과정을 거친 param과 비교를 해야한다.
		Boolean result = false;
		String loadedPassword = loadPassword();
		try {
			//if(encodedPassword.equals(AES256Cipher.AES_Encode(paramPassword, key))){
			//apply AES256 
			String temp = AES256Cipher.AES_Encode(paramPassword, key);
			if(loadedPassword.equals(AES256Cipher.AES_Encode(paramPassword, key))){
				result = true;
			}
		}catch (Exception e) {
			//e.printStackTrace();
			result = null;
		} 
	
		return result;
	}
	
	/**
	 * 
	 */
	public boolean setLock(){
		//isLock = true;
		try {
			OutputStream out = appContext.openFileOutput(IS_LOCK_FILE, Activity.MODE_PRIVATE);
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(out);
			outStreamWriter.write(TRUE);
			outStreamWriter.close();
			out.close();
			
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.i(SEMTELOCK," setLock() FileNotFoundException");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(SEMTELOCK," setLock() IOException");
			return false;
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean setUnLock(){
		//isLock = false;
		try {
			OutputStream out = appContext.openFileOutput(IS_LOCK_FILE, Activity.MODE_PRIVATE);
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(out);
			outStreamWriter.write(FALSE);
			outStreamWriter.close();
			out.close();
			
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.i(SEMTELOCK," setUnLock() FileNotFoundException");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(SEMTELOCK," setUnLock() IOException");
			return false;
		}
	}
	
	/**
	 * 
	 * @return Lock이 걸어져있으면 true, 풀려져 있으면 false, 오류발생시 null
	 */
	public Boolean isLock(){
		//return isLock;
		String str;
		try {
			InputStream in = appContext.openFileInput(IS_LOCK_FILE);
			InputStreamReader inStreamReader = new InputStreamReader(in);
			BufferedReader bufReader = new BufferedReader(inStreamReader);
			str = bufReader.readLine();
			
			if(str.equals(TRUE) == true){
				return true;
			}else if(str.equals(FALSE) == true){
				return false;
			}else{
				return null;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//Log.i(SEMTELOCK,"loadPassword()) FileNotFoundException");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.i(SEMTELOCK,"loadPassword()) IOException");
			return null;
		}
	}
	
	/**
	 * 
	 * @param paramPassword
	 * @return
	 */
	private boolean storePassword(String paramPassword){
		
		try {
			OutputStream out = appContext.openFileOutput(PASSWORD_FILE, Activity.MODE_PRIVATE);
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(out);
			outStreamWriter.write(paramPassword);
			outStreamWriter.close();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.i(SEMTELOCK,"storePassword(String paramPassword) FileNotFoundException");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(SEMTELOCK,"storePassword(String paramPassword) IOException");
			return false;
		}
		
		return true;
		
		
	}
	
	/**
	 * load the encoded Password from file
	 * 암호화된 패스워드를 파일로부터 불러온다
	 * @return If successful, encodedPassword, null if it fails
	 * 			성공하면 암호화된 패스워드를, 실패하면 null을 반환한다.
	 */
	private String loadPassword(){
		
		String str;
		try {
			InputStream in = appContext.openFileInput(PASSWORD_FILE);
			InputStreamReader inStreamReader = new InputStreamReader(in);
			BufferedReader bufReader = new BufferedReader(inStreamReader);
			str = bufReader.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.i(SEMTELOCK,"loadPassword()) FileNotFoundException");
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.i(SEMTELOCK,"loadPassword()) IOException");
			return null;
		}
		
		return str+"\n";

	}

}
