package game.colliders;

import java.awt.Rectangle;

import game.Entity;
import game.util.Vector2;

// Repesents a rectangle in world space capable of collision detection.
public class BoxCollider extends Collider {
	
	/// Fields
	public float x;
	public float y;
	public float width;
	public float height;
	
	/// Constructors
	public BoxCollider(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public BoxCollider(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/// Methods
	@Override
	public Boolean collide(Vector2 point) {
		return (point.x >= x 
		     && point.y >= y 
	 		 && point.x <= x + width 
			 && point.y <= y + height);
	}

	@Override
	public Boolean collide(Rectangle box) {
		return (x <= box.x + box.width
			 && y <= box.y + box.height
			 && x + width >= box.x
			 && y + height >= box.y);
	}

	@Override
	public Boolean collide(BoxCollider collider) {
		return (this.getLeft() <= collider.getRight()
			 && this.getTop() <= collider.getBottom()
			 && this.getRight() >= collider.getLeft()
		 	 && this.getBottom() >= collider.getTop()
				);
	} 
	
	public float getLeft() {
		return x;
	}
	
	public float getRight() {
		return x + width;
	}
	
	public float getTop() {
		return y;
	}
	
	public float getBottom() {
		return y + height;
	}
	
	public Vector2 getTopLeft() {
		return new Vector2(x, y);
	}
	
	public Vector2 getTopCenter() {
		return new Vector2((x + width) / 2, y);
	}
	
	public Vector2 getTopRight() {
		return new Vector2(x + width, y);
	}
	
	public Vector2 getCenterLeft() {
		return new Vector2(x, (y + height) / 2);
	}
	
	public Vector2 getCenter() {
		return new Vector2((x + width) / 2, (y + height) / 2);
	}
	
	public Vector2 getCenterRight() {
		return new Vector2(x + width, (y + height) / 2);
	}
	
	public Vector2 getBottomLeft() {
		return new Vector2(x, y + height);
	}
	
	public Vector2 getBottomCenter() {
		return new Vector2((x + width) / 2, y + height);
	}
	
	public Vector2 getBottomRight() {
		return new Vector2(x + width, y + height);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int)x, (int)y, (int)width, (int)height);
	}
	
	public void setLeft(float left) {
		this.x = left;
	}
	
	public void setCenterX(float center) {
		this.x = (2 * center) - this.width;
	}
	
	public void setRight(float right) {
		this.x = right - this.width;
	}
	
	public void setTop(float top) {
		this.y = top;
	}
	
	public void setCenterY(float center) {
		this.y = (2 * center) - this.height;
	}
	
	public void setBottom(float bottom) {
		this.y = bottom - this.height;
	}
	
	public void setTopLeft(Vector2 position) {
		setTop(position.y);
		setLeft(position.x);
	}
	
	public void setTopCenter(Vector2 position) {
		setTop(position.y);
		setCenterX(position.x);
	}
	
	public void setTopRight(Vector2 position) {
		setTop(position.y);
		setRight(position.x);
	}
	
	public void setCenterLeft(Vector2 position) {
		setCenterY(position.y);
		setLeft(position.x);
	}
	
	public void setCenter(Vector2 position) {
		setCenterY(position.y);
		setCenterX(position.x);
	}
	
	public void setCenterRight(Vector2 position) {
		setCenterY(position.y);
		setRight(position.x);
	}
	
	public void SetBottomLeft(Vector2 position) {
		setBottom(position.y);
		setLeft(position.x);
	}
	
	public void SetBottomCenter(Vector2 position) {
		setBottom(position.y);
		setCenterX(position.x);
	}
	
	public void SetBottomRight(Vector2 position) {
		setBottom(position.y);
		setRight(position.x);
	}
	
	public void SetRectangle(Rectangle rect) {
		x = rect.x;
		y = rect.y;
		width = rect.width;
		height = rect.height;
	}
	
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y + " Width: " + width + " Height: " + height;
	}
	

}
