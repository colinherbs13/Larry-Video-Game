package game.graphics;

import java.awt.Color;

import game.Entity;
import game.components.Component;
import game.util.Vector2;

// Represents a single texture assigned to an entity.
public class Picture extends Component {
	
	/// Fields
	public Vector2 offset;
	private Texture image;
	
	/// Constructors
	public Picture(Entity entity, Texture image, boolean isActive) {
		this.entity = entity;
		offset = new Vector2(0, 0);
		this.image = image;
		this.isActive = isActive;
		this.isVisible = true;
	}
	
	public Picture(Entity entity, Texture image){
		this(entity, image, false);
	}
	
	public Picture(Entity entity, String filename) {
		this(entity, new Texture(filename), false);
	}
	
	public Picture(Texture image) {
		this(null, image);
	}
	
	public Picture(String filename) {
		this(null, filename);
	}
	
	/// Methods
	private Vector2 getRenderPos() {
		if (this.getEntity() != null)
			return this.getEntity().position.subtract(offset);
		else
			return offset.inverse();
	}
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
	
	public Texture getTexture() {
		return this.image;
	}
	
	public void setTexture(Texture texture) {
		this.image = texture;
	}
	
	public void center() {
		offset.x = (float)getWidth() / 2;
		offset.y = (float)getHeight() / 2;
	}
	
	public Picture tint(Color color) {
		Picture p = new Picture(image);
		
		p.image = p.image.tint(color);
		
		return p;
	}
	
	public Picture border(Color color) {
		Picture p = new Picture(image);
		
		p.image = p.image.getBorder(color);
		
		return p;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		image.render(getRenderPos());
	}

}
