package com.system.display;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;

public class EnrollmentDialog{
	
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
		}
		
		boolean alreadyExists = false;
		while(name == null){
			if(alreadyExists){
				name = JOptionPane.showInputDialog(null, 
						"Username already exists." + '\n' + '\n' +
						"Enter a user name : ", 
						"User Registration - Username - (1/3)", 1);
				alreadyExists = false;
			}
			else{
				name = JOptionPane.showInputDialog(null, "Enter a user name : ", 
					"User Registration - Username - (1/3)", 1);
			}
			if(name == null){
				// do nothing
			}
			else{
				// if the user name already exists, ditch it and prompt again.
				if(existingUserNames.contains(name) || name.equals("")){
					name = null;
					alreadyExists = true;
				}
			}
		}
		while(nick == null){
			nick = JOptionPane.showInputDialog(null, "Enter a nickname : ", 
					"User Registration - Nickname - (2/3)", 1);
		}
		while(gender == null){
			Object[] possibilities = {"male", "female"};
			gender = (String)JOptionPane.showInputDialog(
			                    null,
			                    "User Gender:",
			                    "User Registration - Gender - (3/3)",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    possibilities,
			                    "male");
		}
		JOptionPane.showMessageDialog(null, 
				"Thank you for registering, " +  name + "." + 
				'\n' + "You entered the following details:" +
				'\n' + '\n' +
				"Name:   " + name + '\n' +
				"Nick:   " + nick + '\n' +
				"Gender: " + gender,
				"Complete Registration", 1);
		
		REGISTRATION_COMPLETED = true;
	}
	
	/**
	 * Begin enrollment process.
	 */
	public EnrollmentDialog(ArrayList<String> existingUserNames){
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
	public String[] getUserDetails(){
		if(REGISTRATION_COMPLETED){
			return new String[]{name, nick, gender};
		}
		else
			return null;
	}
	
	
}