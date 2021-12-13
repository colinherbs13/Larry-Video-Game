package gameLogic;

import java.util.ArrayList;
import java.util.Collection;

import game.Entity;
import game.components.Sprite;
import game.enums.larryState;
import game.graphics.Animation;
import game.graphics.Texture;
import game.scenes.Scene;
import game.util.Logger;
import game.util.Vector2;

//describes the door, window, and vent class

public class Ingress extends Entity {
	//Fields
	public boolean blocked;
	public boolean larryPresent;
	public Sprite sprite;	
	public larryState location;
	
	//Constructor
	private Ingress() {
		super();
		blocked = false;
		this.location = larryState.AETHER; 
	}
	
	public Ingress(String name) {
		this();
		
		
		ArrayList<String> filenamesLarry = new ArrayList<String>();
		ArrayList<String> filenamesNoLarry = new ArrayList<String>();
		
		switch (name){
		
		case "windowright":											//to create a new kind of ingress, add to the switch case constructor.
																	//this constructor will load up textures into the entity appropriately, making animations and setting the default sprite
			Logger.Log("a window is born");							//animations have 2 flavors, larry or no larry
			
			filenamesLarry.add("rightwindowlarry1.png");
			filenamesNoLarry.add("rightwindowNOlarry1.png");
			
			setUpIngressSprites(new Vector2(735, 40), filenamesLarry, filenamesNoLarry, "rightwindowlarry1.png");
			
			this.location = larryState.WINDOW;
		
			break;
		case "windowleft":
			filenamesLarry.add("leftwindow.png");
			filenamesNoLarry.add("leftwindow.png");
			
			setUpIngressSprites(new Vector2(175, 36), filenamesLarry, filenamesNoLarry, "leftwindow.png");
			
			
			break;
		case "doorright":
			filenamesLarry.add("rightdoorspritelarry.png");
			filenamesNoLarry.add("rightdoorspriteNOlarry.png");
			
			setUpIngressSprites(new Vector2(1175, 45), filenamesLarry, filenamesNoLarry, "rightdoorspriteNOlarry.png");
			
			this.location = larryState.DOORRIGHT;
			
			break;
		case "doorleft":
			
			break;
		case "vent":
			filenamesLarry.add("ventLarry.png");
			filenamesNoLarry.add("ventNoLarry.png");
			
			setUpIngressSprites(new Vector2(1920/4 - 250, 250), filenamesLarry, filenamesNoLarry, "ventClosed.png");
			
			this.location = larryState.VENT;
			break;
		default:
			break;
		}
	}
	
	public void update() {
		super.update();
		
		if (GameState.larryLocation == this.location && this.location != larryState.AETHER) {
			this.larryPresent = true;
		}
		else
			this.larryPresent = false;
		
		if (blocked && (this.location == larryState.VENT || this.location == larryState.WINDOW)) {
			this.sprite.setCurrAnimation("blocked");
		}
		else if (larryPresent) {
			this.sprite.setCurrAnimation("larry");
		}
		else {
			this.sprite.setCurrAnimation("nolarry");
		}
	}
	
	public void updateSprite(Sprite newSprite) {
		this.sprite = newSprite;
	}
	
	public void changeState() {
		if (this.blocked) {
			this.blocked = false;
		}
		else {
			this.blocked = true;
		}
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	public void setUpIngressSprites(Vector2 position, ArrayList<String> filenamesLarry,  ArrayList<String> filenamesNoLarry, String...filenamesBlocked) {
		Vector2 zero = new Vector2(0, 0);
		ArrayList<Texture> larry = new ArrayList<Texture>();
		ArrayList<Texture> nolarry = new ArrayList<Texture>();
		ArrayList<Texture> blocked = new ArrayList<Texture>();
		
		for(String filename : filenamesLarry) {
		larry.add( new Texture(filename));
		}
		
		for(String filename : filenamesNoLarry) {
		nolarry.add( new Texture(filename));
		}
		
		for(String filename : filenamesBlocked) {
			blocked.add(new Texture(filename));
		}
		
		Animation larryAnime = new Animation(zero, 1, false, "larry", larry);
		Animation nolarryAnime = new Animation(zero, 1, false, "nolarry", nolarry);
		Animation blockedAnime = new Animation(zero, 1, false, "blocked", blocked);
		
		Sprite windowrightSprite = new Sprite(this, zero, "larry");
		windowrightSprite.addAnimation("nolarry", nolarryAnime);
		windowrightSprite.addAnimation("larry", larryAnime);
		windowrightSprite.addAnimation("blocked", blockedAnime);
		
		windowrightSprite.setCurrAnimation("nolarry");
		
		this.sprite = windowrightSprite;
		add(sprite);
		this.position = position;
	}
	
	public Scene getScene() {
		return this.scene;
	}
}
