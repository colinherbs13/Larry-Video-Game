package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import game.audio.AudioPlayer;
import game.scenes.Scene;
import game.scenes.SceneManager;
import game.util.BFrame;
import game.util.Draw;
import game.util.Input;
import game.util.Logger;
import game.util.Vector2;
import gameLogic.GameState;
import game.util.BScreen;
import game.util.Calc;

// The skeleton of the actual game. 
// The goal is to change the insides of this as little as possible. Ideally it won't be changed at all.
// Needs work regarding screen size / camera issues.
public class Game {
	
	public static BFrame bFrame;
	public BScreen background;
	public BScreen gameScreen;
	
	public final static String directoryPath = System.getProperty("user.dir");
	public final static String contentPath = directoryPath + "\\content" + "/";
	public final static String dataPath = directoryPath + "\\data" + "/";
	public final static String saveFilePath = dataPath + "LarrySave.ser";
	private static String title; 
	private Boolean isRunning;
	private final double DESIRED_FPS = 60.0;
	public static double deltaTime = 0.0;
	public double rawTimeElapsed = 0.0;
	int fps = 0;
	long memory;
	
	public static Game instance;
	
	public Scene scene;
	public Scene nextScene;
	private static int width;
	private static int height;
	
	public static int entityCount = 0;
	
	
	public Game() {
		instance = this;
		isRunning = true;
		
		width = 1920;
		height = 1080;
		
		//Sets the application to use the actual dimensions in pixels, and ignore the resolution of the user.
		System.setProperty("sun.java2d.uiScale", "1");
		
		
		
		Input.initialize();
		Draw.initialize();
		SceneManager.initialize();
		GameState.initialize();
		
		background = new BScreen(width, height);
		gameScreen = new BScreen(400, 360);
		bFrame = new BFrame(gameScreen);
		bFrame.addScreen(background);
		bFrame.centerScreen(0);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		bFrame.setLocation(dim.width/2 - width/2, dim.height/2 - height/2);
		setTitle("FPS: " + fps);
		
		
		scene = null;
		nextScene = null;
		
		setScene(SceneManager.getDefault());
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Scene getNextScene() {
		return nextScene;
	}
	
	public void setScene(Scene scene) {
		if (scene == null) {
			this.scene = scene;
		} else {
			nextScene = scene;
		}
	}
	
	public void updateScenes() {
		if (nextScene != null) {
			if (scene != null) {
				scene.end();
			}
			Logger.Log("Swapping from scene to new scene");
			scene = nextScene;
			if (scene != null) {
				scene.begin();
				bFrame.centerScreen(0);
				if (this.bFrame.isVisible() == false)
					bFrame.setVisible(true);
			}
			nextScene = null;
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long currTime;
		double frameDuration = 1 / DESIRED_FPS;
		long timer = System.currentTimeMillis();
		double delta = 0;
		
		while(isRunning) {
			if (bFrame.getIsClosing()) {
				break;
			}
			
			
			currTime = System.nanoTime();
			delta += (currTime - lastTime) / (double)1000000000;
			lastTime = currTime;
			rawTimeElapsed += delta;
			if (delta >= frameDuration) {
				deltaTime = delta;
				update();
				fps++;
				while (delta >= frameDuration) {
                    delta -= frameDuration;
                }
			}
			
			render();
			
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				
				
				
				memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				setTitle("FPS: " + fps + " | MB: " + String.format("%.2f", (memory / 1000000f)));
				fps = 0;
			}
			
		}
		
		isRunning = false;
	}
	
	private void update() {
		Input.update();
		AudioPlayer.update();
		//Logger.Log(Game.entityCount);
		
		if (scene != null) {
			if (scene.renderer.camera.getWidthPixels() != gameScreen.getWidth()
		    || scene.renderer.camera.getHeightPixels() != gameScreen.getHeight()) {
				gameScreen.setBounds(0, 0, Calc.min(scene.renderer.camera.getWidthPixels(), background.getWidth()),
						                   Calc.min(scene.renderer.camera.getHeightPixels(), background.getHeight()));
				bFrame.centerScreen(0);
			}
			scene.update();
		}
		
		updateScenes();
	}
	
	private void render() {
		BufferStrategy bs = gameScreen.getBufferStrategy();
		if (bs == null) {
			gameScreen.createBufferStrategy(3);
			return;
		}
		
		Draw.g2d = (Graphics2D)bs.getDrawGraphics();
		Draw.g2d.clearRect(0, 0, gameScreen.getWidth(), gameScreen.getHeight());
		////////////////////RENDER///////////////////
		
		if (scene != null) {
			Draw.g2d.setTransform(scene.renderer.camera.getMatrix());
			scene.renderer.render();
		}
		
		////////////////////////////////////////////
		Draw.g2d.dispose();
		bs.show();
	}
	
	public static void exit() {
		Logger.Log("Game is exiting!");
		System.exit(0);
	}
	
	private void setTitle(String title) {
		this.title = title;
		if (bFrame != null) {
			bFrame.setTitle(title);
		}
	}
	
	public static Vector2 getCameraPos() {
		if (instance.scene != null) {
			return instance.scene.renderer.camera.getPosition();
		}
		return new Vector2(0, 0);
	}
	
	public static Vector2 getCameraScale() {
		if (instance.scene != null) {
			return instance.scene.renderer.camera.getScale();
		}
		return new Vector2(1, 1);
	}
	

}
