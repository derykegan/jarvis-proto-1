package com.system.display.test;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class BackgroundGrid {
	
	static int WIDTH, HEIGHT, GRIDSIZE;
	static int INTERNALSIZE = 500;
	static int LENGTH = 5;
	GridSquare[][] colourgrid;
	
	public enum lineDirection{
		NORTH, SOUTH, EAST, WEST
	}
	
	private class GridSquare{
		int r,g,b, alpha;
		lineDirection direction;
		boolean head = false;
		
		public GridSquare(){
			this.r = 0;
			this.g = 0;
			this.b = 0;
			alpha = 255;
			direction = lineDirection.EAST;
		}
		
		public GridSquare(int r, int g,int b){
			this.r = r;
			this.g = g;
			this.b = b;
			alpha = 255;
			direction = lineDirection.EAST;
		}
		
		public GridSquare(int r, int g,int b, int alpha){
			this.r = r;
			this.g = g;
			this.b = b;
			this.alpha = alpha;
			direction = lineDirection.EAST;
		}
		
		public Color getColour(){
			return new Color(r,g,b, alpha);
		}
		
		public boolean getHead(){
			return head;
		}
		
		public void setHead(boolean head){
			this.head = head;
		}
		
		public void setColour(int r, int g, int b){
			this.r = r;
			this.g = g;
			this.b = b;
			alpha = 255;
		}
		
		public void setColour(int r, int g, int b, int alpha){
			this.r = r;
			this.g = g;
			this.b = b;
			this.alpha = alpha;
		}
		
		public void setColour(Color c){
			this.r = c.getRed();
			this.g = c.getGreen();
			this.b = c.getBlue();
			this.alpha = c.getAlpha();
		}
		
	}
	
	public BackgroundGrid(int grid){
		GRIDSIZE = grid;
		colourgrid = new GridSquare[INTERNALSIZE][INTERNALSIZE];
		
	//	setRandomStart();
	}
	
	public void drawGrid(Graphics g, int width, int height){
		int boxHor = (int) (width / GRIDSIZE);
		int boxVert = (int) (width / GRIDSIZE);
		
		Color oldColor = g.getColor(); 
		
		int x = 0;
		int y = 0;
		g.setColor(new Color(225,225,225,40));
		// traverse vertically
		for(int i=0; i < boxVert; i++){
			
			//traverse horizontally
			for(int j=0; j < boxHor; j++){
				g.drawRect(x, y, (int)GRIDSIZE, (int)GRIDSIZE);
				
				x += (int) GRIDSIZE;
			}
			y += (int) GRIDSIZE;
			x=0;
		}
		
		g.setColor(oldColor);
	}
	
	private void setRandomStart(){
		Random r = new Random();
		Color head = new Color(230,230,230);
		
		//init grid
		for (GridSquare[] i : colourgrid){
			for (GridSquare j : i){
				j = new GridSquare(0,0,0,100);
			}
		}
		
		
		for (int i = 0; i < INTERNALSIZE; i++){
			int t = r.nextInt(INTERNALSIZE);
			System.out.println(t);
			// set head
			colourgrid[i][t].setHead(true);
			colourgrid[i][t].setColour(head);
			
			// set body
			for(int z=0; z < LENGTH; z++){
				// normal op
				if(t > 0){
					t -= 1;
					colourgrid[i][t].setColour(colourgrid[i][t - (-(z+1))].getColour().brighter());
				}
				// edge wrapping
				else{
					t = INTERNALSIZE-1;
					colourgrid[i][t].setColour(colourgrid[i][0].getColour().brighter());
				}
			}
		}
	}
	
	private void iterateGrid(){
		Color head = new Color(230,230,230);
		
		// first iterate to set head for each row
		for (GridSquare[] i : colourgrid){
			for (int j = 0; j < INTERNALSIZE; j++){
				if(i[j].getHead()){
					// false head
					i[j].setHead(false);
					
					// set new head
					if(j < INTERNALSIZE){
						i[j+1].setHead(true);
						i[j+1].setColour(head);
						
						// now set rest
						for(int z = 0; z < LENGTH; z++){
							i[(j+1)-(z+1)].getColour().brighter();
						}
					}
					else{
						i[0].setHead(true);
						i[0].setColour(head);

						// todo : finish case
					}
				}
			}
			
		}
	}

}
