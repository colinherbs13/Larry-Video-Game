package game.components;

import game.Entity;

// Base abstract class for all components. 
public abstract class Component {
	
	/// Fields
	public boolean isVisible;
	public boolean isActive;
	protected Entity entity;
	
	/// Constructors
	public Component(boolean isVisible, boolean isActive, Entity entity) {
		this.isVisible = isVisible;
		this.isActive = isActive;
		this.entity = entity;
	}
	
	public Component(boolean isVisible, boolean isActive) {
		this(isVisible, isActive, null);
	}
	
	public Component() {
		this(true, true, null);
	}
	
	/// Abstract methods
	public abstract void update();
	public abstract void render();
	
	/// Methods
	public void added(Entity entity) {
		this.entity = entity;
	}
	
	public void removed(Entity entity) {
		this.entity = null;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void removeSelf() {
		if (entity != null)
			this.entity = null;
	}
		
}
