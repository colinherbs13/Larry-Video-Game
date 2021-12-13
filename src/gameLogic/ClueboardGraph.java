package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;

import game.Game;
import game.scenes.CorkboardScene;
import game.scenes.SceneManager;
import game.util.Logger;

public class ClueboardGraph implements Serializable{
	/* Clueboard Graph is a container for the clues and connections
	 * that are currently available to the player. It stores a list
	 * of clues that are accessible, as well as a matrix where each row
	 * represents a root clue and each column represents a potential clue
	 * it is connected to. (EXAMPLE: Clue 1 is connected to Clue 2 -> matrix(1, 2) = 1.
	 */
	private ArrayList<Clue> clues;
	public Clue currentlySelectedClue = null;
	private ArrayList<ArrayList<Integer>> connections;
	private ArrayList<ClueMarker> markers;
	
	public ClueboardGraph() {
		Logger.Log("Constructor Called");
		this.clues = new ArrayList<Clue>();
		this.connections = new ArrayList<ArrayList<Integer>>();
	}
	
	public ClueboardGraph(ArrayList<Clue> clues) {
		this.clues = clues;

		//initialize connections to match the size of accessible clues.
		this.connections = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < clues.size(); i++) {
			this.connections.add(new ArrayList<Integer>());
			for (int j = 0; j < clues.size(); j++) {
				this.connections.get(-1).add(0);
			}
		}
		
	}
	
	public ClueboardGraph(ArrayList<Clue> clues, ArrayList<ArrayList<Integer>> connections) {
		this.clues = clues;
		this.connections = connections;
	}
	
	public void setConnections(ArrayList<ArrayList<Integer>> connections) {
		this.connections = connections;
	}
	
	public void addClue(Clue clue) {
		// adds a clue to the corkboard from the scene
		
		// when loading, we have the connections, but not the clues, so don't add clues if 
		// size of matrix doesn't match quantity of clues.
		if (this.connections.size() > this.clues.size()) {
			Logger.Log("foo");
			return;
		}
		for (int i = 0; i < this.clues.size(); i++) {
			if (clue.getName().equals(this.clues.get(i).getName())) {
				this.clues.set(i, clue);
				return;
			}
		}
		this.clues.add(clue);
		Logger.Log(this.clues.size());
		
		// adjust size of matrix accordingly
		this.connections.add(new ArrayList<Integer>());
		if (this.clues.size() == 1) {
			this.connections.get(0).add(0);
		}
		else {
			// add a cell to each row
			for (int i = 0; i < this.clues.size() - 1; i++) {
				this.connections.get(i).add(0);
			}
			// fill in additional row added for new clue with 0's
			for (int j = 0; j < this.clues.size(); j++) {
				this.connections.get(this.connections.size() - 1).add(0);
			}
		}
		Logger.Log("added clue" + clue.getName());
		Logger.Log(clue);
	}
	
	public void connect(Clue clue1, Clue clue2) {
		// connects two clues together
		Logger.Log(clues.get(0).getName());
		Logger.Log(clues.get(1).getName());
		// make sure clues are aready on board
		if (!contains(clue1)) {
			Logger.Log("ERROR: Tried to connect a clue that wasn't on the board (Clue 1)");
			Logger.Log(clue1 + ":" + clue1.getName() + " " + clue2 + ":" + clue2.getName());
			return;
		}
		if (!contains(clue2)) {
			Logger.Log("ERROR: Tried to connect a clue that wasn't on the board (Clue 2)");
			Logger.Log(clue1 + " " + clue2);
			return;
		}
		// change appropriate cells to 1.
		this.connections.get(clue1.getValue() - 1).set(clue2.getValue() - 1, 1);
		this.connections.get(clue2.getValue() - 1).set(clue1.getValue() - 1, 1);
		Logger.Log(clue1.getName() + " is connected to " + clue2.getName());
		Logger.Log(this.getConnections());
	}
	
	public void disconnect(Clue clue1, Clue clue2) {
		// disconnect two clues
		
		// make sure clues are already on board
		if (!contains(clue1) || !contains(clue2)) {
			Logger.Log("ERROR: Tried to disconnect a clue that wasn't on the board");
			return;
		}
		
		// change appropriate cells to 0
		this.connections.get(clue1.getValue() - 1).set(clue2.getValue() - 1, 0);
		this.connections.get(clue2.getValue() - 1).set(clue1.getValue() - 1, 0);
		Logger.Log(clue1.getName() + " disconneted from " + clue2.getName());
	}
	
	public ArrayList<ArrayList<Integer>> getConnections() {
		return this.connections;
	}
	
	public ArrayList<Clue> getClues(){
		return this.clues;
	}
	
	public boolean contains(Clue clue) {
		// see if the corkboard has a clue on it.
		if (this.clues.contains(clue)){
			return true;
		}
		return false;
	}
	
	public boolean areConnected(Clue clue1, Clue clue2) {
		// check if two clues are connected.
		Logger.Log(clue1.getValue() + " " + clue2.getValue());
		if (this.connections.size() < Math.max(clue1.getValue(), clue2.getValue())) {
			// one of the clues doesn't exist
			Logger.Log("fail2");
			return false;
		}
		else if (this.connections.get(clue1.getValue() - 1).get(clue2.getValue() - 1) == 1) {
			
			return true;
		}
		
		Logger.Log("fail");
		return false;
	}
	
	public void reset() {
		this.connections.clear();
		this.clues.clear();
		//this.connections.add(new ArrayList<Integer>());
		//this.connections.get(0).add(0);
	}
	
}
