package com.system.display.test;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.*;

public class DisplayWindow extends Frame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3977503728648274922L;

	/**
	 * Constructor for MyFrame that shows the Frame on screen with a MyCanvas
	 * in place. The frame is initialised with title, basic size and close handler
	 */
	public DisplayWindow(){
		addWindowListener (new WindowAdapter(){
			public void windowClosing(WindowEvent evt){
				close(evt);
			}
		});
		this.setTitle("JARVIS Prototype");
		this.setSize(740,480);
	//	this.setUndecorated(true);
	//	this.setBackground(new Color(30,30,30,100));
		
		DisplayCanvas displayContent = new DisplayCanvas(this);
		
		this.add(displayContent);
		
		this.setVisible(true);
	}

	/**
	 * We don't need to tidy up so just quit when close is pressed
	 * @param e the WindowEvent that caused the close
	 */
	private void close(WindowEvent e){
		System.exit(0);
	}
	
	/**
	 * Change the window background colour.
	 * @param c
	 */
	public void setBackgroundColour(Color c){
		this.setBackgroundColour(c);
	}
	
	/**
	 * Returns x of the window size
	 * @return
	 */
	public int getWindowX(){
		return this.getX();
	}
	
	/**
	 * Returns y of the window size
	 * @return
	 */
	public int getWindowY(){
		return this.getY();
	}
	
	/**
	 * Sets the window size as provided by x and y.
	 * @param x
	 * @param y
	 */
	public void resizeWindow(int x, int y){
		this.setSize(x, y);
	}
	
	/**
	 * Move the window to pos x and y
	 * @param x
	 * @param y
	 */
	public void moveWindow(int x, int y){
		this.setLocation(x, y);
	}
	
}
