package com.system.display.test;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DisplayCanvas extends Canvas implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5910196017625729291L;
	static boolean running = true,
			antialias = true;
	static int GRIDSIZE = 20;
	Frame f;
	
	BackgroundGrid grid;
	
	public DisplayCanvas(Frame f){
		super();
		
		// set black background
		setBackground(new Color(20,20,20,10));
		grid = new BackgroundGrid(GRIDSIZE);
		this.f = f;
		
	}
	
	public void paint(Graphics g){
		Color oldColor = g.getColor(); 
		
		// use antialiasing if set
		if(antialias){
			((Graphics2D) g).setRenderingHint
			(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON); 
		}
		
	//	Image bufferImage = createImage(getWidth(), getHeight());
	//	Graphics bufferGraphics = bufferImage.getGraphics();
		
		//gradient background
		drawBG(g);
	//	drawGrid(g);
		
		g.setColor(new Color(255,255,255,190));
		g.drawString("Test", 10, 10);
		g.setColor(oldColor);
		
		drawTextArea(g, 5, 5, 200, 150);
	//	g.drawImage(bufferImage, 0, 0, null);
	}

	@Override
	public void run() {
		try {
			while (running) {
				paint(getGraphics());
				Thread.sleep(200);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Draws the gradient background.
	 */
	private void drawBG(Graphics g){
		GradientPaint gra = new GradientPaint(0, 0, 
				new Color(170,170,170), getWidth(), getHeight(), 
				new Color(110,110,110), false);
	    ((Graphics2D) g).setPaint(gra);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	/**
	 * Draws the gradient background.
	 */
	private void drawTextArea(Graphics g, int x, int y, int w, int h){
		int xPoints[] = {x+1, w -20, w, w, x+1};
		int yPoints[] = {y+1, y+1, y +20, y+h, y+h};
		int nPoints = 5;
		
		int xPointsBord[] = {x, w -20, w, w, x};
		int yPointsBord[] = {y, y, y +20, y+h, y+h};
		
		Color oldColor = g.getColor();
		g.setColor(new Color(10,10,10,230));
		
		g.drawPolyline(xPointsBord, yPointsBord, nPoints);
		
		GradientPaint gra = new GradientPaint(0, 0, 
				new Color(200,200,200, 190), w, h, 
				new Color(130,130,130, 190), false);
	    ((Graphics2D) g).setPaint(gra);
	    
	    JTextArea text = new JTextArea();
	    text.setEditable(false);
	    text.setText("cheese");
	    JPanel panel = new JPanel();
	  //  panel.add(text);
	    panel.setEnabled(true);
	    panel.setLayout(new BorderLayout());
        panel.add(text, BorderLayout.CENTER);
        
        //... Set window characteristics.
        f.add(panel);
	    g.fillPolygon(xPoints, yPoints, nPoints);
	    g.setColor(oldColor);
	}
	
	
	/**
	 * Draw the grid background
	 * @param g
	 */
	private void drawGrid(Graphics g){
		grid.drawGrid(g, getWidth(), getHeight());
	}

}
