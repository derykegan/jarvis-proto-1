package com.out;

import com.system.display.DisplayWindowV2;
import com.system.display.EnrollmentDialog;
import com.system.display.SelectUserDialog;
import com.system.user.User;
import com.system.user.UserManager;

public class OutputManager {
	
	SpeechOutputManager speech;
	DisplayWindowV2 display;
	UserManager users;
	User currentUser;
	
	public OutputManager(){
		//System.out.println("Output manager");
		display = new DisplayWindowV2(this);
		speech = new SpeechOutputManager(false);
		users = new UserManager();
	}
	
	/**
	 * Invoke the enrollment process or user picklist, depending
	 * on status of existing users.
	 */
	public void doStartupAction(){
		// do enrollment if no users exist
		if(users.getUserNumbers() < 1){
			EnrollmentDialog name = new EnrollmentDialog(
					users.getExistingUserNames());
			
			// if registration completed, retrieve user details.
			if(name.getRegistrationStatus()){
				String[] newUser = name.getUserDetails();
				users.addUser(newUser[0], 
						newUser[1], 
						newUser[2]);
			}
		}
		
		// if users exist, present a picklist of users
		else{
			SelectUserDialog select = new SelectUserDialog(
					users.getExistingUserNames());
			
			if(select.getUserName() != null){
				currentUser = users.getUser(select.getUserName());
				display.setCurrentUser(currentUser);
			}
		}
	}
	
	/**
	 * Get JARVIS to speak this string. Will be logged to
	 * output as appropriate.
	 * @param s
	 */
	public void speak(String s){
		if(s != null && !s.equals("")){
			display.addToOutputLog('\n' + s);
			speech.jarvisOut(s);
		}
	}
	
	/**
	 * Shut down JARVIS speech output, and kill display.
	 */
	public void close(){
		speech.close();
		display.close();
	}
	
	/**
	 * Returns the full speech log from application init.
	 * @return Full speech log as a string.
	 */
	public String getSpeechLog(){
		return speech.getSpeechLog();
	}
	
	/**
	 * Writes speech log to the display.
	 */
	public void writeSpeechLog(){
		display.addToOutputLog(speech.getSpeechLog());
	}
	
	/**
	 * Adds the given string to the input log.
	 * Works similar to println()
	 * @param s
	 */
	public void addToInputLog(String s){
		if(s != null && !s.equals("")){
			display.addToInputLog('\n' + s);
		}
	}
	
	/**
	 * Set the display's visibility.
	 * @param visible
	 */
	public void setDisplayVisible(boolean visible){
		display.setVisible(visible);
	}
	
	public void repaint(){
		display.repaint();
	}
	
	/**
	 * Invokes the enrollment process.
	 */
	public void invokeEnrollment(){
		
		EnrollmentDialog name = new EnrollmentDialog(
				users.getExistingUserNames());
		
		// if registration completed, retrieve user details.
		if(name.getRegistrationStatus()){
			String[] newUser = name.getUserDetails();
			users.addUser(newUser[0], 
					newUser[1], 
					newUser[2]);
		}
		
	}

}
