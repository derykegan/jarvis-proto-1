package com.system.display;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;

public class SelectUserDialog{
	
	public boolean REGISTRATION_COMPLETED = false;
	private ArrayList<String> existingUserNames;
	
	String 	name = null,
			nick = null,
			gender = null;
	
	public void doEnrollment(){
		
		// if no users exist already, display first time message.
		if(existingUserNames == null ||
				existingUserNames.size() < 1){
			JOptionPane.showMessageDialog(null, 
					"No users have been entered," + '\n' + 
					"please press OK to set up a" + '\n' +
					"user account.",
					"JARVIS - USER REGISTRATION", 1);
			return;
		}
		
		String[] existingUsersArray = null;
		// convert arraylist to array
		existingUserNames.toArray(existingUsersArray);
		
		while(name == null){
			name = (String)JOptionPane.showInputDialog(
			                    null,
			                    "Select User:",
			                    "User Selection",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    existingUsersArray,
			                    null);
		}
		
		REGISTRATION_COMPLETED = true;
	}
	
	/**
	 * Show dialog to select from existing users.
	 */
	public SelectUserDialog(ArrayList<String> existingUserNames){
		this.existingUserNames = existingUserNames;
		doEnrollment();
	}
	
	/**
	 * Returns true if the registration has completed successfully.
	 * @return
	 */
	public boolean getRegistrationStatus(){
		return REGISTRATION_COMPLETED;
	}
	
	/**
	 * Returns user details as a string, IF registration has completed.
	 * Else returns null.
	 * @return
	 */
	public String getUserName(){
		if(REGISTRATION_COMPLETED){
			return name;
		}
		else
			return null;
	}
	
	
}