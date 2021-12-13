package game.scenes;

import java.util.ArrayList;


import game.graphics.Picture;
import game.graphics.Texture;
import game.util.Interfaces;
import game.util.Logger;
import game.util.Vector2;
import gameLogic.Clue;
import gameLogic.ClueMarker;
import gameLogic.GameState;
import game.*;

public final class CorkboardScene extends Scene{

	/// add entity declarations and additional things here
	
	public Clue selectedClue;
	public ArrayList<Clue> answerKey;
	private ArrayList<ClueMarker> markers = new ArrayList<ClueMarker>();
	private ArrayList<Clue> clues;
	
	public CorkboardScene() {
		super();
		// do not touch!
	}
	
	@Override
	public void begin() {
		super.begin();
		// add entity initiations, setup, etc.
		// add ALL entities to scene using this.add(entity)
		Entity mainscreen = new Entity();
		Picture mainscreenimage = new Picture("corkboard.png");
		
		mainscreen.add(mainscreenimage);
		
		InteractiveButton backButton = new InteractiveButton(new Vector2(0, 0), this, new Texture("backbutton.png"),
				new Texture("backbutton.png"), Interfaces.GoToGame	);
		clues = new ArrayList<Clue>();
		
		add(mainscreen);
		for (Clue c : GameState.clueboard.getClues()) {
			c.setScene(this);
			clues.add(c);
			add(c);
		}
		add(backButton);
		
		
		this.renderer.camera.setScale(3);
	}
	
	@Override
	public void update() {
		super.update();
		GameState.update();
		if (GameState.clueboard.getConnections().equals(GameState.clueSolution.getConnections())) {
			GameState.scoobyDoo();
		}
		
		for (ClueMarker c : markers) {
			c.getAttached().activeMarkers -= 1;
			remove(c);
		}
		markers.clear();
		
		for (int i = 0; i < GameState.clueboard.getConnections().size(); i++) {
			for (int j = 0; j < GameState.clueboard.getConnections().size(); j++) {
				if (GameState.clueboard.getConnections().get(i).get(j) == 1 && j >= i) {
					markers.add(new ClueMarker(this, clues.get(i), clues.get(j), 5 * clues.get(j).getMarkers()));
					markers.add(new ClueMarker(this, clues.get(i), clues.get(i), 5 * clues.get(i).getMarkers()));
					clues.get(j).activeMarkers += 1;
					clues.get(i).activeMarkers += 1;
				}
			}
		}
		for (ClueMarker c : markers) {
			add(c);
			c.position = new Vector2(c.getAttached().position.x + c.getOffset(), c.getAttached().position.y);
		}
		// super.update() already updates entities. add any additional update here.
	}
	
	@Override
	public void render() {
		super.render();
		// don't touch unless you specifically need to add something.
	}
	
}
