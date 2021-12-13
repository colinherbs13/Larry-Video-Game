package gameLogic;

import java.util.ArrayList;

import game.audio.BClip;

public class Nonogram {
	/* This is the Nonogram class. This class contains the basic layout and functionality for
	 * the nonogram puzzle within the game. The class has methods that get the hints displayed on
	 * the side of the puzzle, as well as changing the state and checking that the nonogram is solved. 
	 * The solution for the nonogram is a 2D array list that is passed in as a parameter before each night.
	 * The size of the puzzle is also specified, and is only one integer since each puzzle is a square.
	 */
	
	//fields
	private ArrayList<ArrayList<Integer>> cells;
	private ArrayList<ArrayList<Integer>> solution;
	public final Integer size = 15; 
	public static BClip oof = new BClip("vine-boom.wav");
	//constructor
	public Nonogram(ArrayList<ArrayList<Integer>> solution) {
		cells = new ArrayList<ArrayList<Integer>>();
		if (solution.size() == size) {
			for (int i = 0; i < size; i++) {
				cells.add(new ArrayList<Integer>());
				for (int j = 0; j < size; j++) {
					cells.get(cells.size() - 1).add(0);
				}
			}
			this.solution = solution;
		}
		else {
			System.out.println("ERROR: entered solution does not equal specified puzzle size.");
		}
	}
	
	//methods	
	public boolean checkSolved() {
		//checks if the current cell state equals the solution.
		//cell state 2 counts the same as cells in state 0.
		for (int i = 0; i < this.cells.size(); i++) {
			for (int j = 0; j < this.cells.get(i).size(); j++) {
				if (this.cells.get(i).get(j) == 2 && this.solution.get(i).get(j) == 0) {
					continue;
				}
				else if (this.cells.get(i).get(j) != this.solution.get(i).get(j)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void changeCellState(int x, int y, int state) {
		// changes a cell at a specific coordinate to specified state
		this.cells.get(y).set(x, state); 
	}
	
	public ArrayList<ArrayList<Integer>> getCells() {
		// returns the current 2d array of cells.
		return this.cells;
	}
	
	public int getCellState(int x, int y) {
		//returns the current state of the specified cell given by coordinates
        return this.cells.get(y).get(x);
    }
	
	public void reset() {
		//resets every cell state to 0.
        for (int i = 0; i < this.cells.size(); i++) {
            for (int j = 0; j < this.cells.size(); j++) {
                this.changeCellState(i,  j,  0);
            }
        }
    }
	
	public ArrayList<Integer> getColumnHint(int colNum){
		//gets layout for vertical column (numbers indicating solution for that column)
		Integer prev = 0;
		ArrayList<Integer> hint = new ArrayList<Integer>();
		for (int i = this.solution.size() - 1; i >= 0; i--) {
			Integer cell = this.solution.get(i).get(colNum);
			if (cell == 1) {
				if (cell != prev) {
					hint.add(1);
					prev = 1;
				}
				else {
					hint.set(hint.size() - 1, hint.get(hint.size() - 1) + 1);
				}
			}
			else {
				if (cell != prev) {
					prev = 0;
				}
			}
		}
		return hint;
	}
	
	public ArrayList<Integer> getRowHint(int rowNum) {
		//gets the layout for a horizontal row (numbers indicating solutions for that row)
		Integer prev = 0;
		ArrayList<Integer> hint = new ArrayList<Integer>();
		for(int i = this.solution.get(rowNum).size() - 1; i >= 0 ; i--) {
			Integer cell = this.solution.get(rowNum).get(i);
			if (cell == 1) {
				if (cell != prev) {
					hint.add(1);
					prev = 1;
				}
				else {
					hint.set(hint.size() - 1, hint.get(hint.size() - 1) + 1);
				}
			}
			else {
				if (cell != prev) {
					prev = 0;
				}
			}
		}
		return hint;
	}
}