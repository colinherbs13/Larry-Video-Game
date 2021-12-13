package game.util;

import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import game.*;
import game.enums.*;

// BertButEt Frame. Creates the actual window of the application.
public class BFrame extends JFrame {
	
	/// Fields
	private Color bgColor;
	private boolean isClosing = false;
	
	/// Constructors
	public BFrame() {
		addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	isClosing = true;
	        	Logger.Log("Window is closing!");
	            Game.exit();
	        }
	    });
		
		this.getContentPane().setBackground(new Color(255, 100, 125));
		this.getContentPane().setBackground(Color.black);
		setVisible(false);
		setResizable(false);
		setLocationRelativeTo(null);
		toFront();
		requestFocus();
		
	}
	
	public BFrame(BScreen screen) {
		this();
		addScreen(screen);

	}
	
	/// Methods
	public void addScreen(BScreen screen) {
		this.getContentPane().add(screen);
		pack();
	}
	
	public void centerScreen(int index) {
		Component screen = this.getContentPane().getComponent(index);
		
		screen.setLocation((this.getContentPane().getWidth() / 2) - (screen.getWidth() / 2), 
				           (this.getContentPane().getHeight() / 2) - (screen.getHeight() / 2));
		
		
	}
	
	public Boolean getIsClosing() {
		return isClosing;
	}
	
	public Color getBGColor() {
		return this.bgColor;
	}
	
	public void setBGColor(Color color) {
		this.bgColor = color;
	}

}
