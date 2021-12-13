package game.util;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

//BertButEt Screen. Holds the actual game inside the window.
public class BScreen extends Canvas {
	
	/// Constructors
	private BScreen() {
		addKeyListener(Input.keyListener);
		addMouseListener(Input.mouseListener);
		setFocusable(true);
	}
	
	public BScreen(Dimension preferredSize) {
		this();
		setPreferredSize(preferredSize);
	}
	
	public BScreen(int width, int height) {
		this();
		setPreferredSize(width, height);
	}
	
	/// Methods
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(new Dimension(preferredSize.width, preferredSize.height));
	}
	
	public void setPreferredSize(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
	}
	
	@Override
	public void paint(Graphics g) {
		// Leave blank
	}
	
	@Override
	public void repaint() {
		// Leave blank
	}
	
	
	
	
}
