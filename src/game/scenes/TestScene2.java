package game.scenes;

import java.awt.Color;

import game.Entity;
import game.InteractiveButton;
import game.enums.Keys;
import game.graphics.Spritesheet;
import game.graphics.Texture;
import game.util.Input;
import game.util.Interfaces;
import game.util.Logger;
import game.util.Vector2;

public final class TestScene2 extends Scene {

	private InteractiveButton pal;
	private Spritesheet sheet;
	private Entity joemama;
	
	public TestScene2() {
		super();
	}
	
	@Override
	public void begin() {
		super.begin();
		
		pal = new InteractiveButton(new Vector2(1000, 100), this, 
				                   new Texture("changebutton.png"),
				                   new Texture("changebutton_pressed.png"),
				                   Interfaces.GoToMainMenu);
		sheet = new Spritesheet(new Texture("rightcurtain.png"), "rightcurtain.xml");
		joemama = new Entity(this);
		joemama.add(sheet.getSprite(0));
		add(pal);
		add(joemama);
		
	}
	
	@Override
	public void update() {
		super.update();

		
		if (Input.check(Keys.F)) {
			pal.position.x += 2f;
		}
	}
	
	@Override
	public void render() {
		super.render();
	}
	
}
