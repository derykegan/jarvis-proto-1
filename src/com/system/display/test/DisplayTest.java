package com.system.display.test;

import static com.system.display.test.OpenGLDraw.drawRect;
import static com.system.display.test.OpenGLDraw.fillRect;

import java.awt.Color;

public class DisplayTest {
	
	int[]SCREEN_SIZE = {800,600};
	int[]VIEW_SIZE = {800,600};
	int[]GRIDSIZE = {20,20};
	int[]TILESIZE = {20,20};

	/* colour of the background */
	static final Color grid = new Color(60,60,60);
	static final Color back = new Color(20,20,20);
	
	/* ratio of SCREEN_SIZE/VIEW_SIZE */
	float scale;
	/* offset to center the screen when full-screen */
	float offX = 0;
	float offY = 0;
	/* timer for visual stuff */
	int timer=0;
	
	/**
	 * Draw the game.
	 * @param board	the model to be drawn
	 * @param drawHUD	true if the size-bars are to be drawn
	 */
	public void draw() {
		
		timer++;
		if(timer>720)
			timer=0;
		
		scale = SCREEN_SIZE[1]/VIEW_SIZE[1];
		if(SCREEN_SIZE[0]/VIEW_SIZE[0] < scale) {
			scale = SCREEN_SIZE[0]/VIEW_SIZE[0];
			offY = (SCREEN_SIZE[1] - VIEW_SIZE[1]*scale)/2f;
			offX = 0f;
		} else {
			offY = 0f;
			offX = (SCREEN_SIZE[0] - VIEW_SIZE[0]*scale)/2f;
		}
		
		fillRect(back, 0, 0, SCREEN_SIZE[0], SCREEN_SIZE[1]);
		
		/* DRAW GRID BACKGROUND */
		for(int i=0;i<GRIDSIZE[0];i++) {
		for(int j=0;j<GRIDSIZE[1];j++) {
			int r = 80;
			int g = 80;
			int b = 80;
			fillRect(new Color(20+r,20+g,20+b),
					i*TILESIZE[0]*scale + offX,j*TILESIZE[1]*scale + offY,
					TILESIZE[0]*scale,TILESIZE[1]*scale);
		}}
			
		for(int i=0;i<GRIDSIZE[0];i++)
			drawRect(grid,
					i*TILESIZE[0]*scale + offX,offY,
					TILESIZE[0]*scale,VIEW_SIZE[1]*scale);
		for(int j=0;j<GRIDSIZE[1];j++)
			drawRect(grid,
					offX,j*TILESIZE[1]*scale + offY,
					VIEW_SIZE[0]*scale,TILESIZE[1]*scale);
		
		
	}

	
	
	
	
}
