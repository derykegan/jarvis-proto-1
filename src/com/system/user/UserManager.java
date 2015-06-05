package com.system.user;

import java.io.File;
import java.util.ArrayList;

import com.system.filesystem.UserReader;

public class UserManager {
	
	static ArrayList<User> users;
	static UserReader reader;
	
	/**
	 * 
	 */
	public UserManager(){
		users = new ArrayList<User>();
		reader = new UserReader();
		
		// if user files exist, generate the internal users
		if(reader.getUserCount() > 0){
			System.out.println("Users exist!");
			ArrayList<File> userFiles = reader.getUserFiles();
			
			for(File f : userFiles){
				try {
					String[] userDetails = reader.getFile(f);
					addUser(
							userDetails[0],
							userDetails[1],
							userDetails[2]);
					} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		// existing users now generated, if any.
	}
	
	/**
	 * Returns the number of users currently registered.
	 * @return
	 */
	public int getUserNumbers(){
		return users.size();
	}
	
	/**
	 * Add a new user.
	 * @param name
	 * @param nick
	 * @param gender
	 */
	public void addUser(String name, String nick, String gender){
		User newUser = new User(name, nick, gender);
		users.add(newUser);
		reader.saveUser(new String[]{name, nick, gender});
	}
	
	/**
	 * Get a specific user by their name.
	 * @param name
	 * @return
	 */
	public User getUser(String name){
		User toReturn = null;
		for(User u : users){
			if(u.getName().equals(name)){
				toReturn = u;
			}
		}
		return toReturn;
	}
	
	/**
	 * Returns arraylist of all user names currently registered.
	 * @return
	 */
	public ArrayList<String> getExistingUserNames(){
		ArrayList<String> toReturn = new ArrayList<String>();
		
		// Add the name for each user that exists
		for(User u : users){
			toReturn.add(u.getName());
		}
		
		return toReturn;
	}

}
