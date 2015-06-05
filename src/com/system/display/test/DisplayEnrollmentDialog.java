package com.system.display.test;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DisplayEnrollmentDialog extends JDialog{
	
	String title, text;
	
	public DisplayEnrollmentDialog(String title, String text){
		super();
		this.title = title;
		this.text = text;
		
		this.setTitle(title);
		this.setSize(400, 300);
		this.setAlwaysOnTop(true);
		JLabel text1 = new JLabel(text);
		text1.setHorizontalAlignment(SwingConstants.CENTER);
		text1.setLocation(20, 20);

		JTextField input = new JTextField("", 20);
		input.setLocation(20, 50);
		
		JButton ok = new JButton("OK");
		ok.setLocation(50, 90);

		ok.setActionCommand("OK");
		
		JPanel panel = new JPanel();
		panel.add(text1);
		panel.add(input);
		panel.add(ok);
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		this.add(panel);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		this.setVisible(true);
	}
	
	

}
