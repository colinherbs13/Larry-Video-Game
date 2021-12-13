package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveData implements Serializable{
	private int nightNum;
	private ArrayList<ArrayList<Integer>> connections;
	
	SaveData(int nightNum, ArrayList<ArrayList<Integer>> connections) {
		this.nightNum = nightNum;
		this.connections = connections;
	}
	
	public int getNightNum() {
		return this.nightNum;
	}
	
	public ArrayList<ArrayList<Integer>> getConnections(){
		return this.connections;
	}
}
