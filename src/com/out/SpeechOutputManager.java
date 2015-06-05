package com.out;


import com.out.speech.SpeechOutput;
import com.system.logging.SpeechOutLog;

/**
 * The class that handles the output from JARVIS back to the user.
 * @author deryk
 *
 */
public class SpeechOutputManager {

	static SpeechOutput speechOut;
	static SpeechOutLog log;
	static boolean ConsoleEnabled = true; 	// write text output to console, default true
	
	/**
	 * Init speech output with default voice.
	 */
	public SpeechOutputManager(){
		speechOut = new SpeechOutput();
		log = new SpeechOutLog();
	}
	
	/**
	 * Init speech output with default voice.
	 * @param consoleEnabled - Whether console output is enabled.
	 */
	public SpeechOutputManager(boolean consoleEnabled){
		speechOut = new SpeechOutput();
		log = new SpeechOutLog();
		ConsoleEnabled = consoleEnabled;
	}
	
	/**
	 * Jarvis will speak this string, and optionally print to
	 * console if this option is enabled globally.
	 * @param s
	 */
	public void jarvisOut(String s){
		try {
			if(speechOut.ready()){
				speechOut.speechOutput(s);
			}
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ConsoleEnabled){
			printToConsole(s);
		}
		log.add(s);
	}
	
	/**
	 * Shut down JARVIS speech output.
	 */
	public void close(){
		speechOut.close();
	}
	
	/**
	 * Helper method to print string to console.
	 * @param s
	 */
	private void printToConsole(String s){
		System.out.println("JARVIS: " + s);
	}
	
	/**
	 * Returns the log of speech output for this session.
	 * @return
	 */
	public String getSpeechLog(){
		return log.getLog();
	}
	
}
