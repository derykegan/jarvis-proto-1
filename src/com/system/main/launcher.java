/**
 * 
 */
package com.system.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.io.IOException;

import com.in.SentenceStack;
import com.in.SpeechInput;
import com.out.OutputManager;
import com.system.filesystem.UserReader;

/**
 * @author deryk
 *
 */
public class launcher extends Frame{
	
//	OutputManager output;
//	SentenceStack input;
//	static SpeechInput listen;
//	static String in;
//	static BufferedReader stdIn;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6379288456184347305L;

	/**
	 * @param args
	 * @throws IOException 
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException{
		
		final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            
        }
        Graphics2D g = splash.createGraphics();
        
        renderSplashFrame(g, 1);
        splash.update();
		
		SentenceStack input = new SentenceStack();
		
		renderSplashFrame(g, 2);
		splash.update();
//		listen = new SpeechInput(input);
//		listen.Listen(true);
//		(new Thread (listen)).start();
		
		SpeechInput test = new SpeechInput(input);
		(new Thread (test)).start();
		
		renderSplashFrame(g, 3);
		splash.update();
		
		printToConsole(printWelcomeConsole());
		
		OutputManager output = new OutputManager();
		
		renderSplashFrame(g, 4);
		splash.update();
		
		// do enrollment or select existing user
		output.doStartupAction();
		
		output.setDisplayVisible(true);
		
		output.speak("Awaiting your commands. ");
		String currentInput = "";
		
		
		
		while(true){
			
			try {
				if(currentInput != null || currentInput.equals("")){
					currentInput = input.getSentence();
					
					output.addToInputLog(currentInput);
					if(currentInput.equals("exit")){
						exit();
					}
					else if(currentInput.equals("log")){
						output.writeSpeechLog();
					}
					else{
						output.speak(currentInput);
					}
					Thread.sleep(50);
					
					//dirty hack to prevent corruption of swing canvas
					output.repaint();
				}
			}
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Simple helper method to print the initial welcome message
	 * to the console output.
	 */
	private static String printWelcomeConsole(){
		String message =
	            "====================\n" +
	            "JARVIS Prototype 1\n" +
	            "====================\n" +
	            "\nInitialising....\n" +
	            "\nJava version: " +
	            System.getProperty("java.version") +
	            "\n\n";
	        
	    return message;
	}
	
	/**
	 * Simple helper method to print to console.
	 * 
	 */
	private static void printToConsole(String s){
		System.out.printf(s);
	}
	
	/**
	 * Closes the application.
	 */
	private static void exit(){
	//	output.close();
		
		System.exit(0); 
	}
	
	private static void renderSplashFrame(Graphics2D g, int frame) {
        final String[] comps = {
        		"Sentence Stack", 
        		"Speech Input", 
        		"Speech Output",
        		"Data"};
        
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
        		RenderingHints.KEY_RENDERING, 
        		RenderingHints.VALUE_RENDER_QUALITY);
   //     g.setBackground(new Color(255,255,255,50));
        g.setComposite(AlphaComposite.Clear);
   //     g.fillRect(200,500,200,100);
        g.fillRect(0,0,800,800);
             
        g.setPaintMode();
        
        GradientPaint gp = new GradientPaint(
        		0,0, new Color(0,0,200,150), 
        		1000,1000, new Color(0,0,255,150));
        g.setPaint(gp);
        int[] xPoints = {250,410,430,430,250};
        int[] yPoints = {500,500,510,600,600};
        g.fillPolygon(xPoints,yPoints, 5);

 //       g.setPaintMode();
        g.setColor(Color.WHITE);
        g.drawString("JRE:   " + System.getProperty("java.vendor")
        		+ " " + System.getProperty("java.version"), 5, 20);
        g.drawString("OS:    " + System.getProperty("os.name")
        		+ " " + System.getProperty("os.version")
        		+ " - " + System.getProperty("os.arch"),5,35);
        g.drawString("Loading "+comps[frame-1]+"...", 260, 540);
   //     g.drawString("Loading "+ loadingComponent + "...", 120, 150);
        
    }

}
