package game.scenes;

import java.util.ArrayList;

import java.util.HashMap;

import game.util.Logger;

public class SceneManager {
	
	private static HashMap<String, Scene> scenes;
	private static Scene defaultScene;
	
	///STORE SCENES HERE!///
	private static OfficeScene o;
	private static TestScene2 t2;
	private static SceneExamine e;
	private static CorkboardScene c;
	private static MainMenu menu;
	private static Win w;
	private static NonogramScene n;
	private static Pause p;
	private static Lose l;
	private static SettingsMenu s;
	private static Tutorial t;
	
	/////STOP STORING//////
	
	public static void initialize() {
		scenes = new HashMap<String, Scene>();
		defaultScene = null;
		
		///INSTANTIATE SCENES HERE!///
		o = new OfficeScene();
		t2 = new TestScene2();
		e = new SceneExamine();
		c = new CorkboardScene();
		menu = new MainMenu();
		w = new Win();
		n = new NonogramScene();
		p = new Pause();
		l = new Lose();
		s = new SettingsMenu();
		t = new Tutorial();
		
		///ADD SCENES HERE!///
		addScene(o, "office");
		addScene(t2, "test2");
		addScene(e, "ex");
		addScene(c, "corkboard");
		addScene(menu, "mainMenu");
		addScene(w, "win");
		addScene(n, "nono");
		addScene(p, "pause");
		addScene(l, "lose");
		addScene(s, "settings");
		addScene(t, "tutorial");
		
		setDefault("mainMenu");
	}
	
	public static void addScene(Scene scene, String key) {
		if (scenes.isEmpty()) {
			defaultScene = scene;
		}
		scenes.put(key, scene);
		
	}
	
	public static Scene getScene(String key) {
		if (scenes.containsKey(key)) {
			return scenes.get(key);
		} 
		else {
			Logger.Log("Requested key: " + key +  " not paired to scene. Default scene returned.");
			return defaultScene;
		}
	}
	public static Scene getDefault() {
		return defaultScene;
	}
	
	public static void setDefault(String key) {
		if (scenes.containsKey(key) ) {
			defaultScene = scenes.get(key);
		}
	}
	
	public static CorkboardScene getCorkboardScene() {
		return c;
	}

}
