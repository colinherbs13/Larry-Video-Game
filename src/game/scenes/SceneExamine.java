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

import game.Entity;
import game.Game;
import game.colliders.BoxCollider;
import game.colliders.Hitbox;
import game.components.Sprite;
import game.enums.Keys;
import game.graphics.Picture;
import game.util.Interfaces.*;
import gameLogic.GameState;
import gameLogic.Larry;
import game.util.Draw;
import game.util.Input;
import game.util.Interfaces;
import game.util.Logger;
import game.util.Vector2;

public class SceneExamine extends Scene {
	
	private Entity dummy;
	private Larry larry;
	private Picture dummyImage;
	int a;
	
	
	
	
	public SceneExamine() {
		super();
	}
	
	@Override
	public void begin() {
		super.begin();
		this.renderer.camera.setViewportPixels(2000, 1200);
		
		dummy = new Entity(0, 0);
		dummyImage = new Picture("background.png");
		dummy.add(dummyImage);

		add(GameState.windowright);
		
		/////
		larry = new Larry();
		
		add(larry);
		
		this.renderer.camera.setBoundaries(new Rectangle(0, 0, 2240, 0));
		
		this.renderer.camera.scale(1);
		this.renderer.camera.setViewportPixels(1920*2, 720*2);
	}
	
	@Override
	public void update() {
		super.update();
		
		if (Input.pressed(Keys.Escape)) {
			Game.exit();
		}
	}
	
	@Override
	public void render() {
		super.render();
	}
		
}
