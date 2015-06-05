package com.system.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;

import javax.swing.*;

public class DisplayWindowV1 extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3318884103040822544L;
	
	class CustomDesktopMgr extends DefaultDesktopManager {

		  /**
		 * 
		 */
		private static final long serialVersionUID = -8107376241766960813L;

		// This is called anytime a frame is moved. This
		  // implementation keeps the frame from leaving the desktop.
		  public void dragFrame(JComponent f, int x, int y) {
		    if (f instanceof JInternalFrame) { // Deal only w/internal frames
		      JInternalFrame frame = (JInternalFrame) f;
		      JDesktopPane desk = frame.getDesktopPane();
		      Dimension d = desk.getSize();

		      // Nothing all that fancy below, just figuring out how to adjust
		      // to keep the frame on the desktop.
		      if (x < 0) { // too far left?
		        x = 0; // flush against the left side
		      } else {
		        if (x + frame.getWidth() > d.width) { // too far right?
		          x = d.width - frame.getWidth(); // flush against right side
		        }
		      }
		      if (y < 0) { // too high?
		        y = 0; // flush against the top
		      } else {
		        if (y + frame.getHeight() > d.height) { // too low?
		          y = d.height - frame.getHeight(); // flush against the
		                            // bottom
		        }
		      }
		    }

		    // Pass along the (possibly cropped) values to the normal drag handler.
		    super.dragFrame(f, x, y);
		  }
		}
	
	class TileAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 794637162555190634L;
		private JDesktopPane desk; // the desktop to work with

		  public TileAction(JDesktopPane desk) {
		    super("Tile Frames");
		    this.desk = desk;
		  }

		  public void actionPerformed(ActionEvent ev) {

		    // How many frames do we have?
		    JInternalFrame[] allframes = desk.getAllFrames();
		    int count = allframes.length;
		    if (count == 0)
		      return;

		    // Determine the necessary grid size
		    int sqrt = (int) Math.sqrt(count);
		    int rows = sqrt;
		    int cols = sqrt;
		    if (rows * cols < count) {
		      cols++;
		      if (rows * cols < count) {
		        rows++;
		      }
		    }

		    // Define some initial values for size & location.
		    Dimension size = desk.getSize();

		    int w = size.width / cols;
		    int h = size.height / rows;
		    int x = 0;
		    int y = 0;

		    // Iterate over the frames, deiconifying any iconified frames and then
		    // relocating & resizing each.
		    for (int i = 0; i < rows; i++) {
		      for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
		        JInternalFrame f = allframes[(i * cols) + j];

		        if (!f.isClosed() && f.isIcon()) {
		          try {
		            f.setIcon(false);
		          } catch (PropertyVetoException ignored) {
		          }
		        }

		        desk.getDesktopManager().resizeFrame(f, x, y, w, h);
		        x += w;
		      }
		      y += h; // start the next row
		      x = 0;
		    }
		  }
		}


	public DisplayWindowV1(){
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Fred");
		this.setSize(720, 400);
		
		JDesktopPane desk = new JDesktopPane();
		desk.setMinimumSize(new Dimension(400,300));
		this.setContentPane(desk);
		desk.setDesktopManager(new CustomDesktopMgr());
		
		JLabel l = new JLabel("Testing");
		l.setForeground(new Color(255,255,255));
		l.setVisible(true);
	    // Place the image in the lowest possible layer so nothing
	    // can ever be painted under it.
	    desk.add(l, new Integer(Integer.MIN_VALUE));
		
		JInternalFrame int1 = new JInternalFrame();
		int1.setSize(250, 150);
		int1.setTitle("Input Log");
		int1.setVisible(true);
		int1.setResizable(true);
		int1.setClosable(false);
		int1.setMaximizable(true);
		desk.add(int1);
		
		JInternalFrame int2 = new JInternalFrame();
		int2.setSize(250, 150);
		int2.setTitle("Input Log");
		int2.setVisible(true);
		int2.setResizable(true);
		int2.setClosable(false);
		desk.add(int2);
		
		desk.setLayout(new BorderLayout());
		desk.setBackground(new Color(40,40,45));
		desk.setDoubleBuffered(true);
		
		createMenuBar(desk);
		
		this.setVisible(true);
	}
	
	  // Create a menu bar to show off a few things.
	  protected void createMenuBar(JDesktopPane desk) {
	    JMenuBar mb = new JMenuBar();
	    JMenu menu = new JMenu("Frames");

	    menu.add(new TileAction(desk)); // add tiling capability

	    setJMenuBar(mb);
	    mb.add(menu);
	  }

	

}
