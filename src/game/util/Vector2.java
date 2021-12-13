package game.util;

import java.lang.Math;

///Represents a 2 dimensional point/vector on the xy plane.
public class Vector2 {
	///Fields
	public float x;
	public float y;
	
	///Constructors
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	///Methods
	public Boolean equals(Vector2 other) {
		return (this.x == other.x && this.y == other.y);
	}
	
	public Vector2 add(Vector2 other) {
		return new Vector2(this.x + other.x, this.y + other.y);
	}
	
	public Vector2 subtract(Vector2 other) {
		return new Vector2(this.x - other.x, this.y - other.y);
	}
	
	public float magnitude() {
		return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public Vector2 inverse() {
		return new Vector2(-this.x, -this.y);
	}
	
	///Static methods
	public static Vector2 add(Vector2 v1, Vector2 v2)
	{
		return v1.add(v2);
	}
	
	public static Vector2 subtract(Vector2 v1, Vector2 v2) {
		return v1.subtract(v2);
	}
	
	///toString
	@Override
	public String toString() {
		return "X: " + this.x + " Y: " + this.y;
	}

}