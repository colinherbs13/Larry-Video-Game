package game.scenes;

import java.awt.Color;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.DoorButton;
import game.Entity;
import game.Game;
import game.InteractiveButton;
import game.audio.BClip;
import game.colliders.BoxCollider;
import game.colliders.Hitbox;
import game.components.SoundEmitter;
import game.components.Sprite;
import game.enums.Keys;
import game.graphics.Picture;
import game.graphics.Spritesheet;
import game.graphics.Texture;
import game.util.Interfaces.*;
import game.util.Draw;
import game.util.Input;
import game.util.Interfaces;
import game.util.Logger;
import game.util.Vector2;
import gameLogic.GameState;
import gameLogic.Ingress;
import gameLogic.Larry;

public class OfficeScene extends Scene {
	
	private Entity background;
	private Picture bgImage;
	private Entity listener;
	private InteractiveButton button;
	private SoundEmitter emitter;
	Clip clip = null;
	FloatControl gControl;
	int a;
	BClip miasmajesty;
	BClip wolves;
	
	public OfficeScene() {
		super();
	}
	
	@Override
	public void begin() {
		super.begin();
		this.renderer.camera.setViewportPixels(1920, 720);
		
		background = new Entity(0, 0);
		listener = new Entity(this.renderer.camera.getCenterWorldSpace(), this);
		add(listener);
		bgImage = new Picture("background.png");
		background.add(bgImage);
		add(background);
		
		InteractiveButton corkboardButton = new InteractiveButton(new Vector2(1920/4 + 70, 50), this, Interfaces.GoToCorkboard, new Texture("invisibleCorkButton.png").getWidth(), new Texture("invisibleCorkButton.png").getHeight());
		add(corkboardButton);
		InteractiveButton puzzleButton = new InteractiveButton(new Vector2(1920/4 - 35, 120), this, Interfaces.GoToNonogram, new Texture("invisibleNonoButton.png").getWidth(), new Texture("invisibleNonoButton.png").getHeight());
		add(puzzleButton);
		
		InteractiveButton boxButton = new InteractiveButton(new Vector2(1920/4 + 100, 200), this, new Texture("lockbox.png"),
				new Texture("lockbox.png"), null);
		boxButton.setSound(new BClip("boxUnlock.wav"));
		add(boxButton);
		
		add(GameState.windowright);
		add(GameState.windowleft);
		add(GameState.doorright);
		add(GameState.vent);
		DoorButton leftDoor = new DoorButton(new Vector2(0,0), this, Interfaces.barricadeLeftDoor, Interfaces.removeBarricadeLeftDoor, 130, 360);
		DoorButton rightDoor = new DoorButton(new Vector2(1140,0), this, Interfaces.barricadeRightDoor, Interfaces.removeBarricadeRightDoor, 130, 360);
		InteractiveButton vent = new InteractiveButton(new Vector2(GameState.vent.getX(), GameState.vent.getY()), this, Interfaces.blockVent, new Texture("ventNoLarry.png").getWidth(), new Texture("ventNoLarry.png").getHeight());
		vent.setSound(new BClip("ventClose.wav"));
		add(rightDoor);
		add(leftDoor);
		add(vent);
		
		Spritesheet sheet = new Spritesheet(new Texture("rightcurtain.png"), "rightcurtain.xml");
		Entity windowCurtain = new Entity(new Vector2(50 , 25), this);
		windowCurtain.add(sheet.getSprite(0));
		add(windowCurtain);
		
		
		// SOUNDS
		emitter = new SoundEmitter(new Vector2(corkboardButton.getWidth() / 2, corkboardButton.getHeight() / 2));

		miasmajesty = new BClip("rainloop.wav");
		wolves = new BClip("wolves.wav");
		miasmajesty.setPan(0f);
		miasmajesty.setVolume(0.6f);
		miasmajesty.isLooping = true;
		wolves.setPan(0);
		wolves.setVolume(0.4f);
		emitter.add(miasmajesty);
		emitter.add(wolves);
		emitter.setTarget(listener);
		corkboardButton.add(emitter);
		emitter.startOnce(1);
		emitter.start(0);
		
		this.renderer.camera.setBoundaries(new Rectangle(0, 0, 1280, 0));
		this.renderer.camera.setScale(2);
	}
	
	@Override
	public void update() {
		super.update();
		
		listener.position = this.renderer.camera.getCenterWorldSpace();
		
		if (Input.pressed(Keys.Escape)) {
			Game.exit();
		}
		
		if (Input.check(Keys.A)) {
			renderer.camera.translate(-4, 0);
		} 
		if (Input.check(Keys.D)) {
			renderer.camera.translate(4, 0);
		} 
		if (Input.check(Keys.W)) {
			renderer.camera.translate(0, -4);
		}
		if (Input.check(Keys.S)) {
			renderer.camera.translate(0, 4);
		}
		
		if(Input.check(Keys.Space)) {
			GameState.pause = true;
			Game.instance.setScene(SceneManager.getScene("pause"));	
		}
		GameState.update();
	}
	
	@Override
	public void render() {
		super.render();
	}
}
