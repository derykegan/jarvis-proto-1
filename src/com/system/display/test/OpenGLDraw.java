package com.system.display.test;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

/**
 * This class contains all the methods required for drawing onto the screen.
 * Everything here should be static - it is just a set of tools.
 * @author Michael Cashmore
 *
 */
public class OpenGLDraw {
	
	private static final short maxMass = 128;

	
	/*---------------------*/
	/* ACTUAL DRAW METHODS */
	/*---------------------*/
	
	/**
	 * Draw a solid coloured rectangle
	 * @param colour
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void fillRect(Color colour, float x, float y, float width, float height) {
		// store the current model matrix
		GL11.glPushMatrix();
			
		// ensure the texture is unbound
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	
		// set correct colour
		GL11.glColor4f(colour.getRed()/255f,colour.getGreen()/255f,
				colour.getBlue()/255f,colour.getAlpha()/255f);
		
		// translate to the right location and prepare to draw
		GL11.glTranslatef(x, y, 0);		
			
		// draw a quad
	    GL11.glBegin(GL11.GL_QUADS);
		{
		      GL11.glVertex2f(0, 0);
		      GL11.glVertex2f(0, height);
		      GL11.glVertex2f(width,height);
		      GL11.glVertex2f(width,0);
		}
		GL11.glEnd();
			
		// restore the model view matrix to prevent contamination
		GL11.glPopMatrix();
	}
		
	/**
	 * Draw an unfilled rectangle of a specified line-colour.
	 * @param colour
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void drawRect(Color colour, float x, float y, float width, float height) {
		// store the current model matrix
		GL11.glPushMatrix();
		
		// ensure the texture is unbound
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		// set correct colour
		GL11.glColor4f(colour.getRed()/255f,colour.getGreen()/255f,
				colour.getBlue()/255f,colour.getAlpha()/255f);
	    
		// translate to the right location and prepare to draw
		GL11.glTranslatef(x, y, 0);		
			
		// draw a quad
		GL11.glBegin(GL11.GL_LINE_LOOP);
		{
		      GL11.glVertex2f(0, 0);
		      GL11.glVertex2f(0, height);
		      GL11.glVertex2f(width,height);
		      GL11.glVertex2f(width,0);
		}
		GL11.glEnd();
			
		// restore the model view matrix to prevent contamination
		GL11.glPopMatrix();
	}
	
	/**
	 * Draw an unfilled arc of a specified line-colour.
	 * @param colour	The line-colour
	 * @param x			The xcoord of the center
	 * @param y			The ycoord of the center
	 * @param r			The radius of the arc
	 * @param start		The starting angle of the arc in degrees (clockwise from the rightmost point of the circle.)
	 * @param angle		The size of the arc to draw, in degrees. (360 will draw a complete circle)
	 */
	public static void drawArc(Color colour, float x, float y, float r, int start, int angle) {
		// store the current model matrix
		GL11.glPushMatrix();
		
		// ensure the texture is unbound
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		// set correct colour
		GL11.glColor4f(colour.getRed()/255f,colour.getGreen()/255f,
				colour.getBlue()/255f,colour.getAlpha()/255f);
	    		
		// translate to the right location and prepare to draw
		GL11.glTranslatef(x, y, 0);				

		// draw as a line loop
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for(int i=0;i<angle;i++) {
			float a = (float)(r*Math.cos(Math.toRadians(start+i)));
			float b = (float)(r*Math.sin(Math.toRadians(start+i)));
			GL11.glVertex2f(a,-b);
		}
		GL11.glEnd();
		
		// restore the model view matrix to prevent contamination
		GL11.glPopMatrix();
	}
	
	/**
	 * Draw a filled circle segment of a specified colour.
	 * @param colour	The colour
	 * @param x			The xcoord of the center
	 * @param y			The ycoord of the center
	 * @param r			The radius of the circle
	 * @param start		The starting angle of the arc in degrees (clockwise from the rightmost point of the circle.)
	 * @param angle		The size of the arc to draw, in degrees. (360 will draw a complete circle)
	 */
	public static void fillArc(Color colour, float x, float y, float r, int start, int angle) {
		// store the current model matrix
		GL11.glPushMatrix();
		
		// ensure the texture is unbound
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		// set correct colour
		GL11.glColor4f(colour.getRed()/255f,colour.getGreen()/255f,
				colour.getBlue()/255f,colour.getAlpha()/255f);
	    		
		// translate to the right location and prepare to draw
		GL11.glTranslatef(x, y, 0);	

		// draw as triangle fan
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glVertex2f(0,0);
		for(int i=0;i<angle;i++) {
			float a = (float)(r*Math.cos(Math.toRadians(start+i)));
			float b = (float)(r*Math.sin(Math.toRadians(start+i)));
			GL11.glVertex2f(a,-b);
		}
		GL11.glEnd();
		
		// restore the model view matrix to prevent contamination
		GL11.glPopMatrix();
	}
	
	/**
	 * Draw a fluid surface in one square box.  The result will be a smooth curve
	 * between the two vertical edges of the box based on the mass within and to the
	 * sides.  The lower portion will be coloured based on the mass in this box, the
	 * upper portion will be coloured based on the mass of any water above, or transparent
	 * if the above mass==0.
	 * @param x		The xcoord of the destination box
	 * @param y		The ycoord of the destination box
	 * @param w		The width of the destination box
	 * @param h		The height of the destination box
	 * @param left	The mass of water to the right of this box
	 * @param mass	The mass of water in the box
	 * @param right The mass of water to the right of this box
	 * @param above	The mass of water above this box
	 */
	public static void fillFluid(Color top, Color bottom,
			float x, float y, float w, float h,
			short l, short t, short r, short mass) {

		float level;
		
		// find edge heights and centre height
		float[] control = new float[3];
		control[0] = ((l+mass)/2f) / (float)maxMass;
		control[1] = mass / (float)maxMass;
		control[2] = ((r+mass)/2f) / (float)maxMass;
		
		for(int i=0;i<control.length;i++)
			if(control[i]>1)
				control[i] = 1f;
						
		// store the current model matrix
		GL11.glPushMatrix();
		
		// translate to the right location and prepare to draw
		GL11.glTranslatef(x, y, 0);	
		
		// draw upper portion
		if(t>0) {
			GL11.glColor4f(top.getRed()/255f, top.getGreen()/255f, 
					top.getBlue()/255f, top.getAlpha()/255f);
			
			GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			GL11.glVertex2f(0,0);
			for(float s=0f;s<=1f;s+=1/16f) {
				level = control[1] * 4*h*(s-s*s);
				if(s<0.5)
					level += control[0] * h*(1-4*(s-s*s));
				else
					level += control[2] * h*(1-4*(s-s*s));
				if(level>h) level = h;
				GL11.glVertex2f(w*s,h-level);
			}
			GL11.glVertex2f(w,0);
			GL11.glEnd();
		}
		
		// draw lower portion
		GL11.glColor4f(bottom.getRed()/255f, bottom.getGreen()/255f, 
				bottom.getBlue()/255f, bottom.getAlpha()/255f);
		
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glVertex2f(0,h);
		for(float s=0f;s<=1f;s+=1/16f) {
			level = control[1] * 4*h*(s-s*s);
			if(s<0.5)
				level += control[0] * h*(1-4*(s-s*s));
			else
				level += control[2] * h*(1-4*(s-s*s));
			if(level>h) level = h;
			GL11.glVertex2f(w*s,h-level);
		}
		GL11.glVertex2f(w,h);
		GL11.glEnd();
		
		// restore the model view matrix to prevent contamination
		GL11.glPopMatrix();
	}

	public static void drawLine(Color colour, float x1, float y1, float x2, float y2) {
		// store the current model matrix
		GL11.glPushMatrix();
			
		// ensure the texture is unbound
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	
		// set correct colour
		GL11.glColor4f(colour.getRed()/255f,colour.getGreen()/255f,
				colour.getBlue()/255f,colour.getAlpha()/255f);
		
		// translate to the right location and prepare to draw
		GL11.glTranslatef(x1, y1, 0);		
			
		// draw a quad
	    GL11.glBegin(GL11.GL_LINE_LOOP);
		{
			GL11.glVertex2f(0,0);
			GL11.glVertex2f(x2-x1,y2-y1);
		}
		GL11.glEnd();
			
		// restore the model view matrix to prevent contamination
		GL11.glPopMatrix();
		
	}	
}
