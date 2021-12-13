package game.scenes;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import game.Entity;
import game.InteractiveButton;
import game.graphics.Picture;
import game.graphics.Texture;
import game.scenes.Scene;
import game.util.Interfaces;
import game.util.Logger;
import game.util.Vector2;
import gameLogic.GameState;
import gameLogic.NonogramCell;

public final class NonogramScene extends Scene {

	/// add entity declarations and additional things here
	private ArrayList<ArrayList<NonogramCell>> nonogramCells;
	private Picture computerBackground;
	private Entity bgEntity;
	private Entity min1;
	private Entity min0;
	private Entity sec1;
	private Entity sec0;
	private Picture tmin1;
	private Picture tmin0;
	private Picture tsec1;
	private Picture tsec0;
	//private final HashMap<Integer, Entity> hintNumbers;
	
	public NonogramScene() {
		super();
		//initialize hint entities
	}
	
	@Override
	public void begin() {
		super.begin();
		// add entity initiations, setup, etc.
		// add ALL entities to scene using this.add(entity)
		computerBackground = new Picture("pcscreen.png");
		bgEntity = new Entity();
		bgEntity.add(computerBackground);
		add(bgEntity);
		nonogramCells = new ArrayList<ArrayList<NonogramCell>>();
		this.layoutCells(new Vector2(175, 95));
		
		InteractiveButton backButton = new InteractiveButton(new Vector2(0 , 0), this, new Texture("backbutton.png"),
				new Texture("backbutton.png"), Interfaces.GoToGame);

		add(backButton);
		
		//initialize timer
		setTimer();

		this.renderer.camera.setBoundaries(new Rectangle(0, 0, 1280, 0));
        this.renderer.camera.setScale(3f);
	}
	
	@Override
	public void update() {
		super.update();
		// super.update() already updates entities. add any additional update here.
		int minutes = calculateTime().get(0);
		int seconds = calculateTime().get(1);
		updateTimer(minutes, seconds);
		for (int i = 0; i < GameState.puzzle.size; i++) {
			for (int j = 0; j < GameState.puzzle.size; j++) {
				GameState.puzzle.changeCellState(i, j, nonogramCells.get(j).get(i).getState());
			}
		}
		if (GameState.puzzle.checkSolved()) {
			GameState.winner();
		}
		GameState.update();
	}
	
	@Override
	public void render() {
		super.render();
		// don't touch unless you specifically need to add something.
	}
	
	private void layoutCells(Vector2 startPos) {
		Texture marked = new Texture("marked.png");
		Texture checked = new Texture("checked.png");
		Texture unmarked = new Texture("unmarked.png");
		for (int i = 0; i < GameState.puzzle.size; i++) {
			nonogramCells.add(new ArrayList<NonogramCell>());
			for (int j = 0; j < GameState.puzzle.size; j = j + 1) {
				nonogramCells.get(i).add(new NonogramCell(new Vector2(startPos.x + ((marked.getWidth() + 2) * j), startPos.y + ((marked.getHeight() + 2) * i)), this, unmarked, checked, marked));
				nonogramCells.get(i).get(j).swapTexture(GameState.puzzle.getCellState(j, i));
				add(nonogramCells.get(i).get(j));
			}
		}
		setHints(startPos);
	}
	
	private void setHints(Vector2 startPos) {
		for (int i = 0; i < GameState.puzzle.size; i++) {
			//Column hints
			ArrayList<Integer> cHint = GameState.puzzle.getColumnHint(i);
			Picture texture = null;
			if (cHint.size() == 0) {
				texture = new Picture("0.png");
				Entity nonoNum = new Entity(startPos.x + ((texture.getWidth() + 2) * i), startPos.y - ((texture.getHeight() + 2)));
				nonoNum.add(texture);
				add(nonoNum);
			}
			for (int j = 0; j < cHint.size(); j++) {
				texture = new Picture(cHint.get(j) + ".png");
				Entity nonoNum = new Entity(startPos.x + ((texture.getWidth() + 2) * i), startPos.y - ((2 + texture.getHeight()) * (j + 1)));
				nonoNum.add(texture);
				add(nonoNum);
			}
			
			//Row hints
			ArrayList<Integer> rHint = GameState.puzzle.getRowHint(i);
			if (rHint.size() == 0) {
				texture = new Picture("0.png");
				Entity nonoNum = new Entity(startPos.x - ((texture.getWidth() + 2)), startPos.y + ((texture.getHeight() + 2) * i));
				nonoNum.add(texture);
				add(nonoNum);
			}
			else {
				for (int k = 0; k < rHint.size(); k++) {
					texture = new Picture(rHint.get(k) + ".png");
					Entity nonoNum = new Entity(startPos.x - ((texture.getWidth() + 2) * (k + 1)), startPos.y + ((texture.getHeight() + 2) * i));
					nonoNum.add(texture);
					add(nonoNum);
				}
			}
		}
	}
	
	private void setTimer() {
		tmin1 = new Picture("timer1.png");
		tmin0 = new Picture("timer0.png");
		tsec1 = new Picture("timer0.png");
		tsec0 = new Picture("timer0.png");
		min1 = new Entity(new Vector2(370, 15), this);
		min0 = new Entity(new Vector2(370 + tmin1.getWidth(), 15), this);
		sec1 = new Entity(new Vector2(370 + tmin1.getWidth() * 3, 15), this);
		sec0 = new Entity(new Vector2(370 + tmin1.getWidth() * 4, 15), this);
		Entity colon = new Entity(new Vector2(370 + tmin1.getWidth() * 2, 15), this);
		colon.add(new Picture("timerColon.png"));
		min1.add(tmin1);
		min0.add(tmin0);
		sec1.add(tsec1);
		sec0.add(tsec0);
		add(min1);
		add(min0);
		add(sec1);
		add(sec0);
		add(colon);
	}
	private ArrayList<Integer> calculateTime() {
		int minutes;
		int seconds;
		
		minutes = (int)GameState.timer / 60;
		seconds = (int)GameState.timer % 60;
		
		ArrayList<Integer> times = new ArrayList<Integer>();
		times.add(minutes);
		times.add(seconds);
		return times;
	}
	
	private void updateTimer(int minutes, int seconds) {
		tmin1.setTexture(new Texture("timer" + (minutes / 10) % 10 + ".png"));
		tmin0.setTexture(new Texture("timer" + minutes % 10 + ".png"));
		tsec1.setTexture(new Texture("timer" + (seconds / 10) % 10 + ".png"));
		tsec0.setTexture(new Texture("timer" + seconds % 10 + ".png"));
	}
}
