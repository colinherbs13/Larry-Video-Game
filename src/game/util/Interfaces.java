package game.util;

import game.Game;
import game.scenes.Pause;
import game.scenes.SceneManager;
import game.scenes.Tutorial;
import gameLogic.GameState;
import gameLogic.SaveLoad;

// Stores general use interfaces such as Action and Predicate.
public class Interfaces {
	
	/// Interfaces
	public interface Action {
		void function();
	}
	
	public interface Predicate<T> {
		boolean function(T item);
	}
	
	
	
	/// Static prefabs
	public static Action GameExit = () -> { Game.exit(); };
	public static Action ToTutorial = () -> {Game.instance.setScene(SceneManager.getScene("tutorial"));};
	public static Action NewGame = () -> { GameState.newGame(); };
	public static Action GoToGame = () -> { Game.instance.setScene(SceneManager.getScene("office")); };
	public static Action GoToCorkboard = () -> {Game.instance.setScene(SceneManager.getScene("corkboard")); };
	public static Action GoToNonogram = () -> {Game.instance.setScene(SceneManager.getScene("nono")); };
	public static Action GoToMainMenu = () -> {Game.instance.setScene(SceneManager.getScene("mainMenu")); };
	public static Action GoToSettings = () -> {Game.instance.setScene(SceneManager.getScene("settings"));};
	public static Action GoToPause = () -> {Game.instance.setScene(SceneManager.getScene("pause"));};
	public static Action Win = () -> {Game.instance.setScene(SceneManager.getScene("win"));	};
	public static Action LoadGame = () -> {SaveLoad.loadData(); };
	public static Action VolumeUp = () -> {GameState.volumeUp();};
	public static Action VolumeDown = () -> {GameState.volumeDown();};
	public static Action Fullscreen = () -> {GameState.toggleFullscreen();};
	public static Action Reset = () -> {GameState.nightSetup();};
	public static Action Unpause = () -> {Pause.unpause();};
	public static Action nextTutorial = () -> {Tutorial.nextScreen();};
	public static Action prevTutorial = () -> {Tutorial.prevScreen();};
	
	public static Action barricadeLeftDoor = () -> {GameState.doorleft.blocked = true; } ;
	public static Action barricadeRightDoor = () -> {GameState.doorright.blocked = true; } ;
	public static Action blockVent = () -> {GameState.vent.changeState();};
	public static Action blockWindow = () -> {GameState.windowright.changeState();};
	
	public static Action removeBarricadeLeftDoor = () -> {GameState.doorleft.blocked = false; };
	public static Action removeBarricadeRightDoor = () -> {GameState.doorright.blocked = false; };
}
