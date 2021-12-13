package game.scenes;

import java.awt.Rectangle;

import game.*;
import game.graphics.Picture;
import game.graphics.Texture;
import game.util.Interfaces;
import game.util.Logger;
import game.util.Vector2;
import gameLogic.GameState;

// A template used for making scenes.
// do NOT inherit from this class. Instead copy the contents and add on to it.
public final class Pause extends Scene {

	/// add entity declarations and additional things here
	private Entity pauseText;
	private Picture pauseTextSprite;
	private InteractiveButton continueButton;
	private InteractiveButton quitButton;
	private InteractiveButton settingsButton;
	
	public Pause() {
		super();
		// do not touch!
	}
	
	@Override
	public void begin() {
		super.begin();
		// add entity initiations, setup, etc.
		// add ALL entities to scene using this.add(entity)
		GameState.pause = true;
		Entity background = new Entity();
		Picture backgroundimage = new Picture("settingsBG.png");
		background.add(backgroundimage);
		add(background);
				
		Entity holder = new Entity(210, 19);
		Picture menuHolder = new Picture("pauseMenuHolder.png");
		holder.add(menuHolder);
		add(holder);
		
		pauseTextSprite = new Picture("pauseTitle.png");
		pauseText = new Entity(217f, 15f);
		pauseText.add(pauseTextSprite);
		add(pauseText);
		
		continueButton = new InteractiveButton(new Vector2(pauseText.getX() + ((new Texture("continuebutton.png").getWidth() - pauseText.getWidth()) / 2) + 15, pauseText.getY() + 125), this, new Texture("continuebutton.png"), new Texture("continuebutton.png"), Interfaces.Unpause);
		settingsButton = new InteractiveButton(new Vector2(continueButton.getX(), continueButton.getY() + 50), this, new Texture("settingsButton.png"), new Texture("settingsButton.png"), Interfaces.GoToSettings);
		quitButton = new InteractiveButton(new Vector2(settingsButton.getX(), settingsButton.getY() + 50), this, new Texture("exitButton.png"), new Texture("exitButton.png"), Interfaces.GoToMainMenu);
		
		add(continueButton);
		add(settingsButton);
		add(quitButton);
		this.renderer.camera.setScale(3);
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
	
	public static void unpause() {
		GameState.pause = false;
		Game.instance.setScene(SceneManager.getScene("office")); 
	}
}
