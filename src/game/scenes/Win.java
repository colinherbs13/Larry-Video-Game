package game.scenes;

import game.Entity;
import game.InteractiveButton;
import game.graphics.Picture;
import game.graphics.Texture;
import game.util.Interfaces;
import game.util.Vector2;

// A template used for making scenes.
// do NOT inherit from this class. Instead copy the contents and add on to it.
public final class Win extends Scene {

	/// add entity declarations and additional things here
	
	public Win() {
		super();
		// do not touch!
	}
	
	@Override
	public void begin() {
		super.begin();

		Entity background = new Entity(0, 0);
		Picture bgImage = new Picture("winbg.png");
		background.add(bgImage);
		add(background);
		
		InteractiveButton resetButton = new InteractiveButton(new Vector2(450, 250), this, new Texture("resetButton.png"),
				new Texture("resetButton.png"), Interfaces.Reset);

		add(resetButton);
		
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
