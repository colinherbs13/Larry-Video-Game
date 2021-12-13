package game.components;

import java.awt.Color;
import java.util.HashMap;

import game.Entity;
import game.Game;
import game.graphics.Animation;
import game.graphics.Texture;
import game.util.Logger;
import game.util.Vector2;

// Stores animations for an entity.
// Default Sprite
public class Sprite extends Component{

	/// Fields
	private HashMap<String, Animation> animations;
	private Animation currAnimation;
	private Vector2 offset;
	private int currFrame;
	private float currTime;
	public String name;
	
	/// Constructors
	public Sprite(Entity entity, Vector2 offset, String name) {
		animations = new HashMap<String, Animation>();
		this.addAnimation(new Animation());
		this.entity = entity;
		currFrame = 0;
		currTime = 0;
		currAnimation = animations.get("default");
		this.offset = offset;
		this.name = name;
	}
	
	public Sprite(Entity entity, Vector2 offset, String name, Texture... textures) {
		this(entity, offset, name);
		Animation temp = new Animation(-1f);
		for (var t : textures) {
			temp.addTexture(t);
		}
		this.addAnimation(temp);
	}
	
	public Sprite(Texture... textures) {
		this();
		for (var t : textures) {
			this.getCurrAnimation().addTexture(t);
		}
	}
	
	public Sprite(String... filenamesOfTextures) {
		this();
		for (var f : filenamesOfTextures) {
			Texture temp = new Texture(f);
			this.getCurrAnimation().addTexture(temp);
		}
	}
	
	// Noarg constructor creates a sprite with no offset, is named "default",
	// and has a single animation called "default".
	public Sprite() {
		this(null, new Vector2(0,0), "default");
	}
	
	/// Methods
	
	// Upon adding an animation, the default will be removed.
	public void addAnimation(String name, Animation animation) {
		if (animations.containsKey("default")) {
			animations.remove("default");
			Logger.Log("NOTICE: Default animation is being removed. Sprite_addAnimation");
		}
		animations.put(name, animation);
		if (currAnimation == null || currAnimation.name == "default") {
			currAnimation = animations.get(name);
		}
	}
	
	public void addAnimation(Animation animation) {
		addAnimation(animation.name, animation);
	}
	
	public void addAnimationSetCurrent(Animation animation) {
		addAnimation(animation);
		setCurrAnimation(animation.name);
	}
	
	public void addTexture(String name, Texture texture) {
		if (animations.containsKey(name)) {
			animations.get(name).addTexture(texture);
		} else {
			Logger.Log("WARNING: Could not find animation. Sprite_addTexture");
		}
	}
	
	public void addTexture(Texture t) {
		this.currAnimation.addTexture(t);
	}
	
	public Animation get(String name) {
		if (animations.containsKey(name)) {
			return animations.get(name);
		}
		return null;
	}
	
	public Animation getCurrAnimation() {
		return this.currAnimation;
	}
	
	public void setCurrAnimation(String name) {
		if (animations.containsKey(name)) {
			currAnimation = animations.get(name);
		} else {
			Logger.Log("WARNING: Animation not found. " + this.entity.hashCode());
		}
	}
	
	public void setFrame(int index) {
		if (index < 0) {
			currFrame = 0;
		}
		else if (index >= currAnimation.size()) {
			index = currAnimation.size() - 1;
		} else {
			currFrame = index;
		}
		currTime = 0;
	}
	
	private Vector2 getRenderPos() {
		return new Vector2(this.entity.getX() - (offset.x + currAnimation.getOffset().x), 
						   this.entity.getY() - (offset.y + currAnimation.getOffset().y));
	}
	
	public void setOffset(Vector2 offset) {
		this.offset = offset;
	}
	
	private void nextFrame() {
		currFrame += 1;
		if (currAnimation.isLooping) {
			if (currFrame > currAnimation.size() - 1) {
				currFrame = 0;
			}
		} else {
			if (currFrame > currAnimation.size() - 1) {
				currFrame = currAnimation.size() - 1;
			}
		}
	}
	
	@Override
	public void update() {
		if (currAnimation.getTimeDelay() > 0) {
			currTime += Game.deltaTime;
			if (currTime > currAnimation.getTimeDelay()) {
				nextFrame();
				currTime -= currAnimation.getTimeDelay();
			}
		}
	}

	@Override
	public void render() {
		currAnimation.get(currFrame).render(getRenderPos());
	}
	
	public Sprite tint(Color color) {
		Sprite temp = new Sprite(this.entity, this.offset, this.name);
		for (var a : animations.keySet()) {
			temp.addAnimation(animations.get(a).name, animations.get(a).tint(color));
		}
		
		return temp;
	}
	
	public Sprite brighten(int value) {
		Sprite temp = new Sprite(this.entity, this.offset, this.name);
		for (var a : animations.keySet()) {
			temp.addAnimation(animations.get(a).name, animations.get(a).brighten(value));
		}
		
		return temp;
	}
	
	

}
