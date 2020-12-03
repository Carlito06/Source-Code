import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class userDataBase {

	public String user;
	public String pass;
	
	private int hashVal;
	private static userDataBase []userData;
	public static int maxUsers = 260;
	
	
	public userDataBase(String username,String password)
	{
		 user = username;
		 pass = password;
	}
	
	public static void insertData(String user,String pass, userDataBase[] userData)
	{
		int hashVal = hashFunction(user);
		
		if (userData[hashVal] == null)
		{
			userData[hashVal] = new userDataBase(user,pass);
		}
		
		
	}
	
	// test function
	public static void printTable(userDataBase[] userData)
	{
		for(int i =0; i<maxUsers; i++)
		{
			if(userData[i]!=null)
			{
				getUser(userData[i]);
				getPassword(userData[i]);
				
			}		
			
		}
	}
	
	public static boolean isEmpty(String key,userDataBase []userData)
	{
		if(userData[hashFunction(key)]!=null)
		{
			return false;
		}
		
		return true;
		
	}
	
	public static String isValidPassword(String key,userDataBase []userData)
	{
		if(userData[hashFunction(key)]==null) //just in case, should never execute!
		{
			System.out.print("Invalid User. Username does not exist\n");
			return "$";
		}
		
		return userData[hashFunction(key)].pass;
		
		
	}
	
	//helper
	public static void getPassword(userDataBase userData)
	{
		System.out.print("PASSWORD: "+userData.pass+"\n");
	}
	//helper
	public static void getUser(userDataBase userData)
	{
		System.out.print("USER: "+userData.user+"\n");
	}
	
	
	/*parameter = (user)*/
	//ONLY SUPPORTS LOWERCASE CHARACTERS FOR NOW
	static int hashFunction(String key)
	{
		key = key.toLowerCase();
		int hashVal = 0;
		char a = 'a';
		
		for(int i = 0; i < key.length();i++)
		{
			if (key.charAt(i)>122 ||key.charAt(i)<97)
			{
				
				JOptionPane.showMessageDialog(new JFrame(), "ERROR: Username muse be greater than 4 characters and less than 11.");
				return 0;
			}
			
			hashVal += ((key.charAt(i) - (a-1)));
			
		}
		
		
		return hashVal-1;
	}

	
	

}
