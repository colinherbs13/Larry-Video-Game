package gameLogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import game.Entity;
import game.Game;
import gameLogic.Ingress;
import game.audio.AudioPlayer;
import game.audio.BClip;
import game.components.Sprite;
import game.enums.larryState;
import game.graphics.Texture;
import game.scenes.SceneManager;
import game.util.Logger;
import game.util.Vector2;

import java.util.HashMap;
import java.util.Scanner;

//////////GameState Class Description///////////
/*
 * GameState is a non entity class that holds game logic variables so that they update regardless of
 * what scene the player is currently in. It has an update function that is used in relevant scenes
 * so larry moves around freely during other scenes.
 */

///////// CHANGE LOG //////////

/*
 * 11/10/2021 class created. 
 *
 * 11/13/2021 
 *   added hashmap for solutions
 *   added nightSetup function (incomplete) that sets the puzzle for each night and resets the Ingresses
 */

////////////////////////////////

public class GameState {
	// Game Data 
	public static int nightNumber;
	public static HashMap<Integer, ArrayList<ArrayList<Integer>>> solutions;
	public static Nonogram puzzle;
	public static ClueboardGraph clueboard;
	public static ClueboardGraph clueSolution;
	public static double timer;								//timer determines the length of the night, if the player survives larry 
	public static int volume = 5;							//they get the win screen
	public static boolean fullScreen = false;
	public static boolean pause = false;
	public static ArrayList<Clue> clues;
	private static ArrayList<ArrayList<Integer>> cSolution;
	public static float oxygen = 100;
	private static Entity border = new Entity(0, 0);
	
	// Ingresses
	public static Ingress windowright;
	public static Ingress doorright;
	public static Ingress windowleft;
	public static Ingress doorleft;
	public static Ingress vent;
	
	// Larry Data
	public static int larryGotcha;
	public static larryState larryLocation;
	public static Larry larry;
	private static BClip alertSound = new BClip("mgs.wav");
	private static BClip doorLarrySound = new BClip("doorCreak.wav");
	private static BClip ventSound = new BClip("ventAlert.wav");
	private static BClip leftSound = new BClip("knock.wav");
	private static BClip breathing = new BClip("heavyBreathing.wav");
	private static boolean larryNoticed = false;
	private static boolean alertPlayed = false;
	private static boolean ventPlayed = false;
	private static boolean leftDoorPlayed = false;
	private static boolean doorPlayed = false;
	
	public GameState() {
		
		
		
	}
	
	public static void initialize() {
		//sets up night 1
		
		GameState.timer = 600;
		
		GameState.windowleft = new Ingress("windowleft");
		GameState.windowright = new Ingress("windowright");
		GameState.doorleft = new Ingress("doorleft");
		GameState.doorright = new Ingress("doorright");
		GameState.vent = new Ingress("vent");
		
		getSolutions();
		
		cSolution = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 6; i++) {
			cSolution.add(new ArrayList<Integer>());
			for (int j = 0; j < 6; j++) {
				cSolution.get(i).add(0);
			}
		}
		addClueSolutions(0, 3, 1);
		addClueSolutions(1, 5, 0);
		addClueSolutions(2, 5);
		addClueSolutions(3, 0, 4);
		addClueSolutions(4, 3);
		addClueSolutions(5, 1, 2);
		Logger.Log(cSolution);
		
		clueboard = new ClueboardGraph();
		clueSolution = new ClueboardGraph(new ArrayList<Clue>(), cSolution);
		
		makeClues();
	}
	
	// getSolutions is for when we have multiple solutions to update 
	private static void getSolutions() {
		//fills in the solutions hashmap with our set solutions 
		//contained in a text file in the content folder.
		solutions = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
		try {
			File solFile = new File("content/goofLarrySolutions.txt");
			Scanner reader = new Scanner(solFile);
			ArrayList<ArrayList<Integer>> solution = new ArrayList<ArrayList<Integer>>();

			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				if (line.length() == 1) {
					
					Integer nightNum = Integer.parseInt(line);
					solutions.put(nightNum, solution);
					solution = new ArrayList<ArrayList<Integer>>();
				}
				else {
					solution.add(new ArrayList<Integer>());
					for (int i = 0; i < line.length(); i++) {
						if (line.charAt(i) == '1') {
							solution.get(solution.size() - 1).add(1);
						}
						else if (line.charAt(i) != '\n') {
							solution.get(solution.size() - 1).add(0);
						}
					}
				}
			}
			reader.close();
		}
		catch(FileNotFoundException e) {
			Logger.Log("Error loading solutions");
			e.printStackTrace();
		}
	}
	
	public static void newGame() {
		nightNumber = 1;
		clueboard.reset();
		clues.get(0).pinned = false;
		clues.get(0).position = new Vector2(0, 0);
		Logger.Log(clueboard.getConnections());
		nightSetup();
	}
	
	public static void nightSetup() {										//this sets up the nonogram and larry so that each new nigth has a new larry and 	
		puzzle = new Nonogram(solutions.get(nightNumber));
		larry = new Larry();
		for (int i = 0; i < nightNumber; i++) {
			clueboard.addClue(clues.get(i));
		}
		reset();	
		Game.instance.setScene(SceneManager.getScene("office"));
	}
	
	public static void winner() {											//regardless of scene, if the player wins they are sent to the win screen
		AudioPlayer.stopAll();
		Game.instance.setScene(SceneManager.getScene("win"));
		nightNumber += 1;
		
		BClip clip = new BClip("applause.wav");
		clip.setVolume(0.4f);
		clip.start();
		Logger.Log(nightNumber);
		clueboard.addClue(clues.get(nightNumber - 1));
		if (nightNumber == 6) {
			gameBeaten();
			Game.instance.setScene(SceneManager.getScene("mainMenu"));
			return;
		}
		SaveLoad.saveGame();
	}
	
	public static void loser() {											//regardless of scene, the player will be sent to the lose screen and all 
		AudioPlayer.stopAll();
		Logger.Log("Lose");
		larryGotcha = 1; 													//game logic variables will be set to default
		BClip clip = new BClip("scream2.wav");
		clip.setVolume(0.8f);
		clip.start();
		larry.pause = true;
		clueboard.reset();
		Logger.Log("Reset Clueboard");
		if (nightNumber != 1) {
			SaveLoad.loadData();
		}
		
		Game.instance.setScene(SceneManager.getScene("lose"));
		reset();
	}
	
	public static void gameBeaten() {
		Logger.Log("You beat the game! Congrats!");
	}
	
	public static void scoobyDoo() {
		Logger.Log("I would've gotten away with it too if it weren't for you meddling kids.");
		winner();
	}
	
	public static void update() {											//because GameState is not an entity owned by one scene, it needs its own
		if (pause) return;
		larry.update();														//update function that will be called during game logic segments
		timer -= 1.0/60.0;													//timer updates every frame 
		//Logger.Log(timer);
		if(timer < 0) {
			winner();
		}
		
		if (larryLocation == larryState.WINDOW) {
			larryNoticed = true;
		}
		
		if (larryNoticed == true && alertPlayed == false) {
			alertSound.start();
			alertPlayed = true;
		}
		
		if ((larryLocation == larryState.DOORRIGHT) && larryNoticed == true && doorPlayed == false) {
			doorLarrySound.setVolume(0.3f);
			doorLarrySound.setPan(0.8f);
			doorLarrySound.start();
			doorPlayed = true;
		}
		
		if (larryLocation == larryState.VENT && ventPlayed == false) {
			ventSound.start();
			ventPlayed = true;
		}
		
		if (larryLocation == larryState.DOORLEFT && leftDoorPlayed == false) {
			leftSound.setPan(-.5f);
			leftSound.start();
			leftDoorPlayed = true;
		}
		
		if (larryLocation == larryState.AETHER) {
			larryNoticed = false;
			alertPlayed = false;
			ventPlayed = false;
			leftDoorPlayed = false;
			doorPlayed = false;
		}
		
		if (vent.blocked == true) {
			oxygen -= 0.05f;
			if (!(breathing.getIsPlaying())) {
				breathing.setPan(0);
				breathing.setVolume(0.4f);
				breathing.start();
			}
			//Texture borderTexture = new Texture("breathBorderTest.png");
			//Sprite borderSprite = new Sprite();
			//borderSprite.addTexture(borderTexture);
			//border.add(borderSprite);
			//vent.getScene().add(border);
			Logger.Log(oxygen);
		}
		else {
			if (!(oxygen >= 100)) {
				oxygen += 0.05f;
				Logger.Log(oxygen);
			}
			breathing.stop();
		}
		
		if (oxygen <= 0) {
			loser();
		}
	}																		//this way, larry does not move before the game has started
	
	public static void reset() {											//scrubs the gamelogic objects so a fresh game can be played
		windowright.blocked = false;
		doorright.blocked = false;
		windowleft.blocked = false;
		doorleft.blocked = false;
		vent.blocked = false;
		
		larryGotcha = 0;
		timer = 600;
		oxygen = 100;
		
		puzzle.reset();		
	}
	
	public static void volumeUp() {
		if (volume == 10) {
			return;
		}
		else {
			volume += 1;
			BClip clip = new BClip("vine-boom.wav");
			clip.setVolume(0.4f);
			clip.start();
		}
	}
	
	public static void volumeDown() {
		if (volume == 0) {
			return;
		}
		else {
			volume -= 1;
			BClip clip = new BClip("vine-boom.wav");
			clip.setVolume(0.4f);
			clip.start();
		}
		Logger.Log(volume);
	}
	
	public static float getVolumeModifier() {
		return (volume / 5f);
	}
	
	public static void toggleFullscreen() {
		if (fullScreen == false) {
			//Game.bFrame.setUndecorated(true);
			Logger.Log("full");
		}
		else {
			//Game.bFrame.setUndecorated(false);
			Logger.Log("not full");
		}
	}
	
	private static void makeClues() {
		clues = new ArrayList<Clue>();
		Clue girlclue = new Clue(SceneManager.getCorkboardScene(), "sampleclue.png", 1, "girl");
		Clue gunclue = new Clue(SceneManager.getCorkboardScene(), "sampleclue2.png", new Vector2(1920/4, 0), 2, "gun");
		Clue badgeclue = new Clue(SceneManager.getCorkboardScene(), "badgeClue.png", 3, "badge");
		Clue tophatclue = new Clue(SceneManager.getCorkboardScene(), "tophatClue.png", new Vector2(1920/4, 0), 4, "tophat");
		Clue lgclue = new Clue(SceneManager.getCorkboardScene(), "larryGFClue.png", 5, "lg");
		Clue keyclue = new Clue(SceneManager.getCorkboardScene(), "keyClue.png", new Vector2(1920/4, 0), 6, "key");
		
		clues.add(girlclue);
		clues.add(gunclue);
		clues.add(badgeclue);
		clues.add(tophatclue);
		clues.add(lgclue);
		clues.add(keyclue);
	}
	
	private static void addClueSolutions(int clueNum, int...clueConnect) {
		for (int i : clueConnect) {
			cSolution.get(clueNum).set(i, 1);
		}
	}
}
