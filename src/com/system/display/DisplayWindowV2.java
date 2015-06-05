package com.system.display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.out.OutputManager;
import com.system.user.User;

public class DisplayWindowV2 extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 79420662532621331L;
	private JSplitPane splitPane, splitPaneInput, splitPaneOutput;
	private JTextArea inputLog, inputLogTitle, outputLog, outputLogTitle;
	private OutputManager parent; 		// reference to the parent of this display
	private User currentUser;
	
	public DisplayWindowV2(OutputManager parent){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("JARVIS Prototype 1");
		this.setSize(720, 400);
		this.parent = parent;
		
		inputLog = new JTextArea();
		inputLog.setEditable(false);
		inputLogTitle = new JTextArea();
		inputLogTitle.setEditable(false);
		inputLogTitle.setEnabled(false);
		inputLogTitle.setText("Input Log");
		inputLogTitle.setBackground(new Color(0,0,0,0));
		inputLogTitle.setForeground(new Color(255,255,255));
		JScrollPane inputLogScroll = setTextAreaStyle(inputLog);
		
		splitPaneInput = new BackgroundPane(JSplitPane.VERTICAL_SPLIT,
                inputLogTitle, inputLogScroll);
		splitPaneInput.setEnabled(false);
		
		outputLog = new JTextArea();
		outputLog.setEditable(false);
		outputLogTitle = new JTextArea();
		outputLogTitle.setEditable(false);
		outputLogTitle.setEnabled(false);
		outputLogTitle.setText("Output Log");
		outputLogTitle.setBackground(new Color(0,0,0,0));
		outputLogTitle.setForeground(new Color(255,255,255));
		
		JScrollPane outputLogScroll = setTextAreaStyle(outputLog);
		
		splitPaneOutput = new BackgroundPane(JSplitPane.VERTICAL_SPLIT,
                outputLogTitle, outputLogScroll);
		splitPaneOutput.setEnabled(false);
		
		//Create a split pane with the two scroll panes in it.
		splitPane = new BackgroundPane(JSplitPane.HORIZONTAL_SPLIT,
		                           splitPaneInput, splitPaneOutput);
		splitPane.setOneTouchExpandable(false);
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(this.getWidth()/2);

	//	inputLog.setText("Testing 123");
		
		JMenuBar menu = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		
		JMenuItem logButton = new JMenuItem("Log");
		logButton.addActionListener(this);
		menu1.add(logButton);
		
		JMenuItem exitButton = new JMenuItem("Exit");
		exitButton.addActionListener(this);
		menu1.add(exitButton);
		
		menu.add(menu1);
		this.setJMenuBar(menu);
		this.setContentPane(splitPane);
		this.pack();
		
	//	this.setVisible(true);
	}
	
	/**
	 * Set the text area to the default style and returns in a 
	 * JScrollPane
	 * @param t
	 */
	private synchronized JScrollPane setTextAreaStyle(JTextArea t){
		Dimension minimumSize = new Dimension(100, 50);
		t.setMinimumSize(minimumSize);
//		t.setBackground(new Color(40,40,40));
		t.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		t.setForeground(new Color(255,255,255));
//		t.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()));
		t.setAutoscrolls(true);
		t.setWrapStyleWord(true);
		t.setLineWrap(true);
		t.setBackground(new Color(0, 0, 0, 0));
		
		JScrollPane sp = new JScrollPane(t);
		sp.setOpaque(false);
		
		return sp;
	}
	
	/**
	 * Adds the given string to the input log.
	 * Equivalent to printf()
	 * @param s
	 */
	public synchronized void addToInputLog(String s){
		inputLog.append(s);
	}
	
	/**
	 * Adds the given string to the output log.
	 * Equivalent to printf()
	 * @param s
	 */
	public synchronized void addToOutputLog(String s){
		outputLog.append(s);
	}
	
	/**
	 * Closes output window, but does not close application.
	 */
	public void close(){
		this.dispose();
	}
	
	
	/**
	 * Inline class to render background component which
	 * makes up window.
	 * @author deryk
	 *
	 */
	class BackgroundPane extends JSplitPane{

		/**
		 * 
		 */
		private static final long serialVersionUID = -7835184990520113323L;

		public BackgroundPane(int newOrientation, 
				Component newLeftComponent, Component newRightComponent){
			super(newOrientation, newLeftComponent, newRightComponent);
	//		this.setLayout(new BorderLayout());
		}
		
		 @Override
	    protected void paintComponent(Graphics g) {
			super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        int w = getWidth();
	        int h = getHeight();
	        Color color1 = new Color(0,0,0,200);
	        Color color2 = new Color(80,80,80,200);
	        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
	        g2d.setPaint(gp);
	        g2d.fillRect(0, 0, w, h);
	    }
		
	}

	/**
	 * Menu bar actions.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="Exit"){
			System.exit(0);
		}
		else if(e.getActionCommand()=="Log"){
			addToOutputLog(parent.getSpeechLog());
		}
	}
	
	/**
	 * Set the current user.
	 * @param u
	 */
	public void setCurrentUser(User u){
		currentUser = u;
	}

}
