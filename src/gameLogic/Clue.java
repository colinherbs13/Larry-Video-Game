package gameLogic;

import java.util.ArrayList;

import game.Entity;
import game.components.Sprite;
import game.enums.Buttons;
import game.enums.Keys;
import game.graphics.Texture;
import game.scenes.CorkboardScene;
import game.scenes.Scene;
import game.util.Input;
import game.util.Logger;
import game.util.Vector2;


public class Clue extends Entity {
	private Sprite sprite;
	private boolean dragged;
	public boolean pinned;
	private CorkboardScene corkboardScene;
	private Integer graphVal;
	private String name;
	public int activeMarkers = 0;
	
	////Constructors////
	
	public Clue() {
		this.dragged = false;
		this.sprite = new Sprite();
		this.name = "No Name";
	}
	
	public Clue(Scene scene, Integer graphVal, String name) {
		this();
		this.scene = scene;
		this.corkboardScene = (CorkboardScene) scene;
		this.graphVal = graphVal;
		this.name = name;
	}
	
	public Clue(Scene scene, String filename, Integer graphVal, String name) {
		this();
		this.sprite = new Sprite(new Texture(filename));
		add(this.sprite);
		this.setHitbox(sprite.getCurrAnimation().get(0).getWidth(), sprite.getCurrAnimation().get(0).getHeight());
		this.graphVal = graphVal;
		this.scene = scene;
		this.corkboardScene = (CorkboardScene) scene;
		this.name = name;
	}
	
	public Clue(Scene scene, String filename, Vector2 position, Integer graphVal, String name) {
		this(scene, filename, graphVal, name);
		this.position = position;
		this.name = name;
	}
	
	
	//////////////////////
	
	public void addSprite(Sprite sprite) {
		this.sprite = sprite;
		add(this.sprite);
		this.setHitbox(sprite.getCurrAnimation().get(0).getWidth(), sprite.getCurrAnimation().get(0).getHeight());
		
	}
	
	public Integer getValue() {
		return this.graphVal;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void update() {
		super.update();
		///////CHANGELOG
		/*
		 * 11/11/2021 - click and drag implemented. Unstable, if the mouse is released at the wrong time it does not stop holding the clue
		 * BUG - if two clues are moved to overlap eachother they snap into the same hitbox and cannot be seperated
		 * 		- potential fixes: have sprites postion move with the mouse offset from the sprite
		 * 							have a function that moves a clues position to front when it is clicked on and only allow 
		 * 							clues on the topmost layer to be moved
		 * 
		 * 11/12/2021 - solution checking implemented. As expected, because the connection establishing is unstable due to a click being
		 * 				longer than a refresh, the solution tracking is also unstable. I dont want to implement a full flip flop to arbitrarily
		 * 				delay another check for drag but it might be necessary?
		 */
		if(this.dragged == true) {												//movement of the clue depends on if its being dragged, pinned, or free falling
			this.position = Input.getMousePos().subtract(new Vector2(10,10)) ;	//If its dragged, the clue will move with the cursor
			
			if(Input.released(Keys.Space)) {										//while its dragged, if the space bar is pressed the clue will be pinned in place
				this.pinned = true;
			}
		}

		if (this.collide(Input.getMousePos()) && Input.check(Buttons.LeftButton) && GameState.clueboard.currentlySelectedClue == null) {	//if the user clicks and holds on an image with the left button, it will be dragged
			Logger.Log(this.dragged);
			Logger.Log(this.pinned);
				GameState.clueboard.currentlySelectedClue = this;
				if (!this.pinned == true) {
					this.dragged = true;
					this.pinned = false;	
				}
				else if(Input.released(Keys.Space)) {
					this.pinned = false;
				}//the clue will no longer be pinned if the user unpins it 
		}	
		else {
			if(this.dragged == false && (this.position.y < (1080 - 80) / 4) && this.pinned == false )
				this.position = this.position.add(new Vector2(0, 5));				//while not pinned, the clue will fall to the bottom of the corkboard
		}
		if (this.collide(Input.getMousePos()) && Input.released(Buttons.LeftButton)) {
			this.dragged = false;													//when the left button is released, the image is no longer dragged
		}
		
		if (GameState.clueboard.currentlySelectedClue != null) {
			if (this.dragged == false && GameState.clueboard.currentlySelectedClue.equals(this)) {
				GameState.clueboard.currentlySelectedClue = null;
			}
			
		}
		////////////PUZZLE SOLVING SECTION////////////////
		
		if (this.collide(Input.getMousePos()) && Input.released(Buttons.RightButton)) {
			if(this.corkboardScene.selectedClue == null)											//if no clue is selected to be connected
				this.corkboardScene.selectedClue = this;
			else if(this.corkboardScene.selectedClue != this) {
				if(!GameState.clueboard.areConnected(this.corkboardScene.selectedClue, this)) {
					GameState.clueboard.connect(this, this.corkboardScene.selectedClue);
					Logger.Log("clue is connected to clue");
					this.corkboardScene.selectedClue = null;
				}
				else {
					GameState.clueboard.disconnect(this, this.corkboardScene.selectedClue);
					Logger.Log("clue is removed from clue");
					this.corkboardScene.selectedClue = null;
				}
			}
		}
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public int getMarkers(){
		return this.activeMarkers;
	}
}
	
	
	