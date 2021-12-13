package game.colliders;

import java.awt.Rectangle;

import game.*;
import game.util.Vector2;

// Abstract collider class. Probably will be removed soon.
public abstract class Collider {
	
	public abstract Boolean collide(Vector2 point);
	public abstract Boolean collide(Rectangle box);
	public abstract Boolean collide(BoxCollider collider);
	
	public Boolean collide(Hitbox hitbox) {
		return collide(hitbox.getCollider());
	}
	
	public Boolean collide(Entity entity) {
		return collide(entity.hitbox);
	}
	
	public Boolean collide(Collider collider) {
		if (collider instanceof BoxCollider) {
			return collide((BoxCollider)collider);
		}
		return false;
	}
	
	
}
