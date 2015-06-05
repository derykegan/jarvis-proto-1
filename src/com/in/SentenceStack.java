package com.in;

import java.util.ArrayList;

/**
 * A class to hold input sentences waiting to be processed.
 * @author deryk
 *
 */
public class SentenceStack {
	
	ArrayList<String> stack;
	
	/**
	 * Create new sentence stack.
	 */
	public SentenceStack(){
		stack = new ArrayList<String>();
	}
	
	/**
	 * Adds a sentence to the stack.
	 * @param s
	 */
	public synchronized void addSentence(String s){
		if(s != null)
			stack.add(s);
	}
	
	/**
	 * Retrieves the oldest sentence in the stack.
	 * Removes this entry at the same time.
	 * @return
	 */
	public synchronized String getSentence(){
		String toReturn = "";
		if(stack.size() > 0){
			toReturn = stack.get(0);
			stack.remove(0);
		}
		return toReturn;
	}
	
	/**
	 * Returns the size of the stack.
	 * @return
	 */
	public synchronized int getStackSize(){
		return stack.size();
	}

}
