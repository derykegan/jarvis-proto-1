package com.system.filesystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserReader {

	File f = new File("."); // current directory
	String extension = ".user";
	ArrayList<File> files;

	/**
	 * Initialise UserReader
	 */
	public UserReader(){

		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(extension)) {
					return true;
				} else {
					return false;
				}
			}
		};
		
		files = new ArrayList<File>(Arrays.asList(f.listFiles(textFilter)));
		
	}
	
	/**
	 * Get the number of user profiles that exist.
	 * @return
	 */
	public int getUserCount(){
		return files.size();
	}
	
	/**
	 * Return the full list of user files.
	 * @return
	 */
	public ArrayList<File> getUserFiles(){
		return files;
	}
	
	/**
	 * 
	 * @param fetch - the file to go looking for
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String[] getFile(File fetch) throws FileNotFoundException, IOException{
		 try(BufferedReader br = new BufferedReader(new FileReader(fetch))) {
		        String name, nick, gender;
		        name = br.readLine();
		        nick = br.readLine();
		        gender = br.readLine();
		        
		        String[] toReturn = {name, nick, gender};
		        return toReturn;
		    }
	}
	
	/**
	 * Save the user details to a file.
	 * @param userToSave
	 */
	public void saveUser(String[] userToSave){
		// if there are too few attributes, do not save
		if(userToSave.length < 3){
			// do nothing
			return;
		}
		// otherwise proceed with writing
		else{
			String name, nick, gender;
			name 	= userToSave[0];
			nick 	= userToSave[1];
			gender 	= userToSave[2];
			
			try{
				File path = new File("./" + name + extension);
				
				FileWriter fileWriter = new FileWriter(path,false);
				BufferedWriter bufferFileWriter  = new BufferedWriter(fileWriter);
				
				// OS specific line separation
				String sep = System.getProperty("line.separator");
				
				fileWriter.write(name + sep);
				fileWriter.append(nick + sep);
				fileWriter.append(gender + sep);
				
				bufferFileWriter.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}

}
