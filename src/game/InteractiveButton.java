package game;

import java.awt.Rectangle;

import game.audio.BClip;
import game.colliders.BoxCollider;
import game.components.Sprite;
import game.enums.Buttons;
import game.graphics.Picture;
import game.graphics.Texture;
import game.scenes.Scene;
import game.util.Input;
import game.util.Logger;
import game.util.Vector2;
import game.util.Interfaces.*;

// Button entity that performs an action when pressed. Needs to be updated to use the Sprite class.
public class InteractiveButton extends Entity {
	
	/// Fields
	public Action action;
	private Sprite sprite;
	private BClip sound;
	
	/// Constructors
	public InteractiveButton(Vector2 pos, Scene scene, Sprite sprite, Action action) {
		super(pos, scene);
		position = pos;
		this.action = action;
		this.sprite = sprite;
		add(sprite);
		this.sound = null;
		this.setHitbox(sprite.getCurrAnimation().get(0).getWidth(), sprite.getCurrAnimation().get(0).getHeight());
	}
	
	public InteractiveButton(Vector2 pos, Scene scene, Texture normal, Texture pressed, Action action) {
		this(pos, scene, new Sprite(normal, pressed), action);
	}
	
	public InteractiveButton(Scene scene, Action action) {
		this(new Vector2(0, 0), scene, null, action);
	}
	
	public InteractiveButton(Vector2 pos, Scene scene, Action action, float width, float height) {
		super(pos, scene);
		position = pos;
		this.action = action;
		this.setHitbox(width, height);
		this.sound = null;
	}
	
	/// Methods
	private void invoke() {
		if (action != null) {
			action.function();
		}
	}
	
	public void setSound(BClip sound) {
		this.sound = sound;
	}
	
	@Override
	public void update() {
		super.update();
		
		if (this.collide(Input.getMousePos()) && Input.check(Buttons.LeftButton)) {
			if(this.sprite != null)
			this.sprite.setFrame(1);
		}
		else {
			if(this.sprite != null)
			this.sprite.setFrame(0);
		}
		
		if (this.collide(Input.getMousePos()) && Input.released(Buttons.LeftButton)) {
			if (sound != null) {
				sound.setVolume(0.4f);
				if (sound.getIsPlaying()) {
					sound.stop();
				}
				sound.start();
			}
			this.invoke();
		}
	}
	
	
}