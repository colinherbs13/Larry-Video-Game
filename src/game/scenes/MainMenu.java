package game.scenes;

import java.awt.Rectangle;


import game.Entity;
import game.Game;
import game.InteractiveButton;
import game.audio.AudioPlayer;
import game.graphics.Picture;
import game.graphics.Texture;
import game.scenes.Scene;
import game.util.Interfaces;
import game.util.Logger;
import game.util.Vector2;
import gameLogic.GameState;
import gameLogic.NonogramCell;

// A template used for making scenes.
// do NOT inherit from this class. Instead copy the contents and add on to it.
public final class MainMenu extends Scene {
	
	private InteractiveButton newGame;
	private InteractiveButton loadGame;
	private InteractiveButton settings;
	private InteractiveButton exitGame;
	private Entity larryContainer;
	private NonogramCell testNonogram;
	private Picture larrySheet;
	private Picture background;
	private Entity bgEntity;

	public MainMenu() {
		super();
	}
	
	@Override
	public void begin() {
		super.begin();
		GameState.pause = false;
		AudioPlayer.stopAll();
		newGame = new InteractiveButton(new Vector2(90, 250), this, new Texture("startbutton.png"), new Texture("startbutton.png"), Interfaces.ToTutorial);
		loadGame = new InteractiveButton(new Vector2(90, 300), this, new Texture("loadbutton.png"), new Texture("loadbutton.png"), Interfaces.LoadGame);
		settings = new InteractiveButton(new Vector2(500, 250), this, new Texture("settingsbutton.png"), new Texture("settingsbutton.png"), Interfaces.GoToSettings);
		exitGame = new InteractiveButton(new Vector2(500, 300), this, new Texture("exitbutton.png"), new Texture("exitbutton.png"), Interfaces.GameExit);
		larrySheet = new Picture("titlescreen1.png");
		larryContainer = new Entity(0, 0);
		larryContainer.add(larrySheet);
		
		add(larryContainer);
		add(newGame);
		add(loadGame);
		add(settings);
		add(exitGame);
		this.renderer.camera.setBoundaries(new Rectangle(0, 0, 1280, 0));
        this.renderer.camera.setScale(2.75f);
	}
	
	@Override
	public void update() {
		super.update();
		// super.update() already updates entities. add any additional update here.
	}
	
	@Override
	public void render() {
		super.render();
		// don't touch unless you specifically need to add something.
	}
	
}
