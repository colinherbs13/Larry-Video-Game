package game.scenes;

import java.awt.Rectangle;
import java.util.ArrayList;

import game.Entity;
import game.InteractiveButton;
import game.graphics.Picture;
import game.graphics.Texture;
import game.util.Interfaces;
import game.util.Vector2;
import gameLogic.GameState;

// A template used for making scenes.
// do NOT inherit from this class. Instead copy the contents and add on to it.
public final class SettingsMenu extends Scene {

	/// add entity declarations and additional things here
	private InteractiveButton volumeUp;
	private InteractiveButton volumeDown;
	private ArrayList<Picture> volumeIndicators;
	
	private InteractiveButton fullscreenButton;
	
	private InteractiveButton backButton;
	private String type;
	
	public SettingsMenu() {
		super();
		// do not touch!
	}
	
	@Override
	public void begin() {
		super.begin();
		Entity mainscreen = new Entity();
		Picture mainscreenimage = new Picture("settingsBG.png");
		
		mainscreen.add(mainscreenimage);
		add(mainscreen);
		
		Entity settings = new Entity((mainscreenimage.getWidth() / 2) - (new Picture("settingsTitle.png").getWidth() / 2), 0);
		Picture sp = new Picture("settingsTitle.png");
		settings.add(sp);
		volumeUp = new InteractiveButton(new Vector2(405, 150), this, new Texture("volup.png"), new Texture("volup.png"), Interfaces.VolumeUp);
		volumeDown = new InteractiveButton(new Vector2(205, 150), this, new Texture("voldown.png"), new Texture("voldown.png"), Interfaces.VolumeDown);
		volumeIndicators = new ArrayList<Picture>();
		setVolumeIndicators();
		
		fullscreenButton = new InteractiveButton(new Vector2(275, 200), this, new Texture("fullscreenON.png"), new Texture("fullscreenOFF.png"), Interfaces.Fullscreen);
		
		if (GameState.pause == false) {
			backButton = new InteractiveButton(new Vector2(275, 250), this, new Texture("backbutton.png"), new Texture("backbutton.png"), Interfaces.GoToMainMenu);
		}
		else {
			backButton = new InteractiveButton(new Vector2(275, 250), this, new Texture("backbutton.png"), new Texture("backbutton.png"), Interfaces.GoToPause);
		}
		add(backButton);
		add(settings);
		add(volumeUp);
		add(volumeDown);
		//add(fullscreenButton);
		this.renderer.camera.setBoundaries(new Rectangle(0, 0, 1280, 0));
        this.renderer.camera.setScale(2.75f);
	}
	
	@Override
	public void update() {
		super.update();
		// super.update() already updates entities. add any additional update here.
		for (int i = 0; i < volumeIndicators.size(); i++) {
			if (i == GameState.volume) {
				volumeIndicators.get(i).setTexture(new Texture("volumebig.png"));
			}
			else {
				volumeIndicators.get(i).setTexture(new Texture("volumesmall.png"));
			}
		}
	}
	
	@Override
	public void render() {
		super.render();
		// don't touch unless you specifically need to add something.
	}
	
	private void setVolumeIndicators() {
		for (int i = 0; i < 11; i++) {
			Entity newInd = new Entity(255 + 10 * i, 140);
			if (i == GameState.volume) {
				Picture ind = new Picture("volumesmall.png");
				volumeIndicators.add(ind);
				newInd.add(ind);
			}
			else {
				Picture ind = new Picture("volumebig.png");
				volumeIndicators.add(ind);
				newInd.add(ind);
			}
			add(newInd);
		}
	}
}
