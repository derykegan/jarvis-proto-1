package com.system.logging;

import java.util.Date;
import java.util.GregorianCalendar;
/**
 * Log the system speech output.
 * @author deryk
 *
 */
public class SpeechOutLog {
	static String log;			// the log
	
	public SpeechOutLog(){
		log = "";
	}
	
	/**
	 * Add an entry to the log.
	 * @param s
	 */
	public void add(String s){
		Date datetime = GregorianCalendar.getInstance().getTime();
		log = log.concat(datetime + ":    " + s + "\n");
	}
	
	/**
	 * Returns the log of system speech output.
	 * @return
	 */
	public String getLog(){
		return log;
	}
}
