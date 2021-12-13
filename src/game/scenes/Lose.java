package game.scenes;

import game.Entity;
import game.InteractiveButton;
import game.audio.AudioPlayer;
import game.graphics.Picture;
import game.graphics.Texture;
import game.util.Interfaces;
import game.util.Vector2;

// A template used for making scenes.
// do NOT inherit from this class. Instead copy the contents and add on to it.
public final class Lose extends Scene {

	/// add entity declarations and additional things here
	
	public Lose() {
		super();
		// do not touch!
	}
	
	@Override
	public void begin() {
		super.begin();
		AudioPlayer.reset();
		// add entity initiations, setup, etc.
		// add ALL entities to scene using this.add(entity)
		
		Entity dummy = new Entity(0, 0);
		Picture dummyImage = new Picture("losebg.png");
		dummy.add(dummyImage);
		add(dummy);
		
		InteractiveButton resetButton = new InteractiveButton(new Vector2(0, 0), this, new Texture("resetbutton.png"),
				new Texture("resetbutton.png"), Interfaces.Reset);

		add(resetButton);
		
		InteractiveButton exitButton = new InteractiveButton(new Vector2(500, 0), this, new Texture("exitbutton.png"),
				new Texture("exitbutton.png"), Interfaces.GoToMainMenu);
		
		add(exitButton);
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
	
}
