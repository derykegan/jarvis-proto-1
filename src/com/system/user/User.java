package com.system.user;

public class User {
	
	String name = "";
	String nick = "";
	String gender = "";
	
	/**
	 * Create user
	 * @param name   - the name of this user
	 * @param nick   - the nickname of this user
	 * @param gender - the gender of this user (Male | Female)
	 * 
	 */
	public User(String name, String nick, String gender){
		this.name = name;
		this.nick = nick;
		this.gender = gender;
		
		setEmptyDefaults();
	}
	
	public String getName(){
		return name;
	}
	
	public String getNick(){
		return nick;
	}
	
	public String getGender(){
		return gender;
	}
	
	private void setEmptyDefaults(){
		if(name.isEmpty()){
			name = "Unknown";
		}
		if(nick.isEmpty()){
			nick = "Unknown";
		}
		if(gender.isEmpty() || (!gender.equals("Male") && !gender.equals("Female") ) ){
			gender = "Male";
		}
	}

}
