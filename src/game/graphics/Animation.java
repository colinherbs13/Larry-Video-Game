package game.graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import game.util.Vector2;

// Holds multiple related textures. 
// Actual swapping of textures takes place in Sprite class.
// A non-positive timeDelay means that textures can only be swapped manually.
public class Animation {

	/// Fields
	private ArrayList<Texture> textures;
	private Vector2 offset;
	private float timeDelay;
	public Boolean isLooping;
	public String name;
	
	/// Constructors
	private void setup() {
		textures = new ArrayList<Texture>();
		offset = new Vector2(0, 0);
		timeDelay = 0f;
		isLooping = false;
		name = "default";
	}
	
	public Animation(Vector2 offset, float timeDelay, Boolean isLooping, String name, Texture... textures) {
		setup();
		this.offset = offset;
		this.timeDelay = timeDelay;
		this.isLooping = isLooping;
		this.name = name;
		for (var t : textures) {
			this.textures.add(t);
		}
	}
	
	public Animation(Vector2 offset, float timeDelay, Boolean isLooping, String name, Collection<Texture> textures) {
		setup();
		this.offset = offset;
		this.timeDelay = timeDelay;
		this.isLooping = isLooping;
		this.name = name;
		for (var t : textures) {
			this.textures.add(t);
		}
	}
	
	
	
	public Animation(Vector2 offset, float timeDelay, Boolean isLooping, String name) {
		setup();
		this.offset = offset;
		this.timeDelay = timeDelay;
		this.isLooping = isLooping;
		this.name = name;
	}
	
	public Animation(float timeDelay, Boolean isLooping) {
		this(new Vector2(0,0), timeDelay, isLooping, "default");
	}
	
	public Animation(float timeDelay) {
		this(new Vector2(0, 0), timeDelay, false, "default");
	}
	
	// Noarg Animation creates a manual, non-looping, no offset, animation named "default".
	public Animation() {
		this(-1f);
	}
	
	/// Methods
	public void addTexture(Texture texture) {
		textures.add(texture);
	}
	
	public void addTexture(Texture...textures) {
		for (var t : textures) {
			this.textures.add(t);
		}
	}
	// returns the amount of textures
	public int size() {
		return this.textures.size();
	}
	
	
	public float getTimeDelay() {
		return this.timeDelay;
	}
	
	public void setTimeDelay(float timeDelay) {
		this.timeDelay = timeDelay;
	}
	
	public String getName() {
		return this.name;
	}
	
	// gets the texture of the specified index
	public Texture get(int index) {
		return this.textures.get(index);
	}
	
	public Vector2 getOffset() {
		return this.offset;
	}
	
	public void setOffset(Vector2 offset) {
		this.offset = offset;
	}
	
	// tints the animation by a certain color
	public Animation tint(Color color) {
		Animation temp = new Animation(this.offset, this.timeDelay, this.isLooping, this.name);
		for (var t : textures) {
			temp.addTexture(t.tint(color));
		}
		return temp;
	}
	
	// brightens the animation by a value
	public Animation brighten(int value) {
		Animation temp = new Animation(this.offset, this.timeDelay, this.isLooping, this.name);
		for (var t : textures) {
			temp.addTexture(t.brighten(value));
		}
		return temp;
		
	}
	
}
