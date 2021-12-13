package gameLogic;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import game.Game;
import game.util.Logger;

/* Handler for saving and loading data from game
 * 
 * Change Log:
 * 11/13/2021: 
 * 		Created Class
 * 		Added createSaveData, saveGame, loadData functions
 */

public class SaveLoad {
	
	public SaveLoad() {
		
	}
	
	public static void saveGame() {
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut= null;
		SaveData s = new SaveData(GameState.nightNumber, GameState.clueboard.getConnections());
		
		try {
			fileOut = new FileOutputStream(Game.saveFilePath);		//the Employee object makes its way to serial data in the file Employee.ser
			objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(s);
			objOut.close();
			fileOut.close();
			Logger.Log("Saved");
		}
		catch (IOException e) {
			Logger.Log("Error when saving data to file LarrySave.ser");
			e.printStackTrace();
		}
	}
	
	public static void loadData() {
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		SaveData s = null;
			
		try
		{
			Logger.Log("Bing");
			fileIn = new FileInputStream(Game.saveFilePath);
			Logger.Log("Ping");
			objIn = new ObjectInputStream(fileIn);
			Logger.Log("Ting");
			s = (SaveData) objIn.readObject();
			objIn.close();
			fileIn.close();
		}
		catch(IOException i)
		{
			Logger.Log("Error loading data from LarrySave.ser");
			i.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}  
		
		if (s != null) {
			GameState.nightNumber = s.getNightNum();
			for (int i = 0; i < GameState.nightNumber; i++) {
				GameState.clueboard.addClue(GameState.clues.get(i));
			}
			GameState.clueboard.setConnections(s.getConnections());
		} else {
			Logger.Log("There is no previously saved data. Putting default values. SaveLoad_loadData");
			GameState.nightNumber = 1;
			GameState.clueboard.reset();
		}
		
		
		Logger.Log(GameState.clueboard.getConnections());
		Logger.Log(GameState.nightNumber);
		GameState.nightSetup();

	}
}
