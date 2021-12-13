package game.enums;

import java.awt.event.MouseEvent;

// Public enum for the mouse buttons. Use these values when checking input.
public enum Buttons {
	LeftButton(MouseEvent.BUTTON1),
	MiddleButton(MouseEvent.BUTTON2),
	RightButton(MouseEvent.BUTTON3);
	
	private int id;
	private Buttons(int id) { this.id = id; }
	public int getValue() { return id; }
}