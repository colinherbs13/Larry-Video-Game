package game;

import java.util.Iterator;


import game.colliders.Collider;
import game.colliders.Hitbox;
import game.components.Component;
import game.components.ComponentList;
import game.scenes.Scene;
import game.util.Logger;
import game.util.Vector2;

// Represents a collection of components. The building block of game interactions.
public class Entity implements Iterable<Component> {
	
	/// Fields
	public Vector2 position;
	private ComponentList components;
	private int layer;
	private double depth;
	protected Scene scene;
	public Hitbox hitbox;
	
	public boolean isActive = true;
	public boolean isVisible = true;
	public boolean isCollidable = true;
	
	/// Constructors
	public Entity(Vector2 pos, Scene scene) {
		position = pos;
		setComponents(new ComponentList(this));
		added(scene);
		layer = 0;
		depth = 0.0;
		hitbox = new Hitbox(this);
		this.add(hitbox);
		Game.entityCount++;
	}
	
	public Entity() {
		this(new Vector2(0,0), null);
	}
	
	public Entity(Scene scene) {
		this(new Vector2(0, 0), scene);
	}
	
	public Entity(float x, float y) {
		this(new Vector2(x, y), null);
	}
	
	/// Methods
	public void add(Component component) {
		getComponents().add(component);
	}
	
	public void remove(Component component) {
		getComponents().remove(component);
	}
	
	public void added(Scene scene) {
		this.scene = scene;
	}
	
	public void removed(Scene scene) {
		this.scene = null;
	}
	
	public void update() {
		if (isActive)
			getComponents().update();
	}
	
	public void render() {
		if (isVisible)
			getComponents().render();
	}
	
	public void setHitbox(float width, float height) {
		this.hitbox.setDimensions(new Vector2(width, height));
	}
	
	public Boolean collide(Vector2 point) {
		return this.hitbox.collide(point);
	}
	
	public Boolean collide(Entity entity) {
		return this.hitbox.collide(entity);
	}
	
	public float getX() {
		return this.position.x;
	}
	
	public float getY() {
		return this.position.y;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
		scene.setDepth(this);
	}
	
	public double getDepth() {
		return depth;
	}
	
	public void setDepth(double depth) {
		this.depth = depth;
	}
	
	public void bringToFront() {
		setLayer(layer);
	}
	
	@Override
	public Iterator<Component> iterator() {
		return getComponents().iterator();
	}
	
	public float getWidth() {
		return this.hitbox.getWidth();
	}
	
	public float getHeight() {
		return this.hitbox.getHeight();
	}
	
	@Override
	public void finalize() {
		Logger.Log("Bing! Removing entity " + this.getClass().descriptorString());
	}

	public ComponentList getComponents() {
		return components;
	}

	public void setComponents(ComponentList components) {
		this.components = components;
	}
	

}
