package game;

import game.scenes.Scene;
import game.util.Logger;

// Renderer class. Will probably be removed due to lack of necessity.
public class Renderer {
	
	protected Scene scene;
	public Camera camera;
	
	public Renderer(Scene scene) {
		this.scene = scene;
		camera = new Camera(this);
	}
	
	public void update() {
		camera.update();
	}
	
	public void render() {
		if (scene != null) {
			scene.render();
		}
	}
	
}
