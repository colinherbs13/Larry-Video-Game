package gameLogic;

import game.Entity;
import game.components.Sprite;
import game.graphics.Texture;
import game.scenes.Scene;
import game.util.Logger;
import game.util.Vector2;

public class ClueMarker extends Entity {
	private Clue parent;
	private Clue attached;
	private Sprite sprite;
	private int offset;
	
	public ClueMarker(Scene scene, Clue parent, Clue attached, int offset) {
		this.scene = scene;
		this.parent = parent;
		this.attached = attached;
		this.offset = offset;
		
		String filename;
		Logger.Log(parent.getName() + parent.getValue() + attached.getName() + attached.getValue());
		switch(this.parent.getValue() + this.parent.getMarkers()) {
			case (1):
				filename = "bluemarker.png";
				break;
			case (2):
				filename = "greenMarker.png";
				break;
			case (3):
				filename = "orangeMarker.png";
				break;
			case (4):
				filename = "redMarker.png";
				break;
			case (5):
				filename = "pinkMarker.png";
				break;
			case (6):
				filename = "purpleMarker.png";
				break;
			default:
				filename = "blueMarker.png";
		}
		this.sprite = new Sprite(new Texture(filename));
		add(this.sprite);
	}
	
	public void update() {
		this.position = new Vector2(attached.position.x + offset, attached.position.y);;
	}
	
	public Clue getParent() {
		return this.parent;
	}
	
	public Clue getAttached() {
		return this.attached;
	}
	
	public int getOffset() {
		return this.offset;
	}
}
