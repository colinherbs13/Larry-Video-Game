package game;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import game.colliders.BoxCollider;
import game.scenes.Scene;
import game.util.Vector2;


// Camera class that handles moving and zooming in world space.
// Refined since build 0.3.
public class Camera {
	
	private AffineTransform matrix;
	private Vector2 scale;
	
	public Rectangle boundaries;
	// Viewport in terms of pixels; The actual viewport on the screen.
	private BoxCollider viewportPixels;
	// Viewport in terms of world space units; The viewport in the game.
	private BoxCollider viewportWorldSpace;
	
	private Boolean isUpdated;
	
	private Renderer renderer;
	private Scene scene;
	
	public Camera(int width, int height, Renderer renderer) {
		matrix = new AffineTransform();
		
		boundaries = null;
		scale = new Vector2(1, 1);
		viewportPixels = new BoxCollider(0, 0, width, height);
		viewportWorldSpace = new BoxCollider(0, 0, width, height);
		isUpdated = false;
		this.renderer = renderer;
		this.scene = renderer.scene;
	}
	
	public Camera(Renderer renderer) {
		this(Game.getWidth(), Game.getHeight(), renderer);
	}
	
	// scales the current zoom by the given scale.
	public void scale(float scale) {
		this.scale.x *= scale;
		this.scale.y *= scale;
		isUpdated = false;
	}
	
	// sets the scale to a given number.
	public void setScale(float scale) {
		this.scale.x = scale;
		this.scale.y = scale;
		isUpdated = false;
	}
	
	// translates by a given x and y
	public void translate(float x, float y) {
		viewportWorldSpace.x += x;
		viewportWorldSpace.y += y;
		isUpdated = false;
	}
	
	// updates the matrix of the camera.
	public void updateMatrix() {
		matrix.setToIdentity();
		
		matrix.scale(scale.x, scale.y);
		matrix.translate(-viewportWorldSpace.x, -viewportWorldSpace.y);
		
		viewportWorldSpace.width = viewportPixels.width / (this.getScale().x);
		viewportWorldSpace.height = viewportPixels.height / (this.getScale().y);
		
		
		isUpdated = true;
	}
	
	// update called every frame
	public void update() {
		if (isUpdated == false) {
			checkBoundaries();
			updateMatrix();
		}
	}
	
	public AffineTransform getMatrix() {
		return matrix;
	}
	
	public void setBoundaries(Rectangle boundaries) {
		this.boundaries = boundaries;
	}
	
	public void removeBoundaries() {
		this.boundaries = null;
	}
	
	private void pixelToWorldSpace() {
		
	}
	
	// checks if the camera is out of bounds and snaps it to the correct position if it is
	private void checkBoundaries() {
		if (boundaries != null) {
			if (viewportWorldSpace.getRight() > boundaries.width + boundaries.x) {
				viewportWorldSpace.setRight(boundaries.width + boundaries.x);
			}
			if (viewportWorldSpace.getBottom() > boundaries.y + boundaries.height) {
				viewportWorldSpace.setBottom(boundaries.y + boundaries.height);
			}
			if (viewportWorldSpace.x < boundaries.x) {
				viewportWorldSpace.x = boundaries.x;
			}
			if (viewportWorldSpace.y < boundaries.y) {
				viewportWorldSpace.y = boundaries.y;
			}
		}
		
	}
	
	public Vector2 getPosition() {
		return new Vector2(viewportWorldSpace.x, viewportWorldSpace.y);
	}
	
	@Override
	public String toString() {
		return "Camera X: " + viewportWorldSpace.x + " Y: " + viewportWorldSpace.y + " Width: "
			+  viewportWorldSpace.width + " Height: " + viewportWorldSpace.height;
	}
	
	public Vector2 getScale() {
		return this.scale;
	}
	
	// sets the pixel viewport
	public void setViewportPixels(float width, float height) {
		viewportPixels.width = width;
		viewportPixels.height = height;
		isUpdated = false;
	}
	
	// sets the world space viewport
	public void setViewportWorldSpace(float width, float height) {
		viewportWorldSpace.width = width;
		viewportWorldSpace.height = height;
		isUpdated = false;
	}
	
	public int getWidthPixels() {
		return (int)viewportPixels.width;
	}
	
	public int getHeightPixels() {
		return (int)viewportPixels.height;
	}
	
	public int getWidthWorldSpace() {
		return (int)viewportWorldSpace.width;
	}
	
	public int getHeightWorldSpace() {
		return (int)viewportWorldSpace.height;
	}

	public Vector2 getCenterWorldSpace() {
		return new Vector2(viewportWorldSpace.x + (viewportWorldSpace.width / 2), 
						   viewportWorldSpace.y + (viewportWorldSpace.height / 2));
	}
}