package gameLogic;

import game.Entity;
import game.audio.BClip;
import game.enums.Buttons;
import game.graphics.Picture;
import game.graphics.Texture;
import game.scenes.Scene;
import game.util.Input;
import game.util.Logger;
import game.util.Vector2;
import game.util.Interfaces.Action;

public class NonogramCell extends Entity {
	/* This is the class that contains the visual information
	 * for a specific cell of the nonogram puzzle.
	 * It contains three textures (one for each state) and
	 * has information about where it is located on the screen.
	 */
	
	//Fields
		public Picture picture;
		private Texture unchecked;
		private Texture checked;
		private Texture marked;
		private int state;
		
		//Constructors
		public NonogramCell(Vector2 pos, Scene scene, Texture unchecked, Texture checked, Texture marked) {
			super(pos, scene);
			position = pos;
			this.state = 0;
			this.unchecked = unchecked;
			this.checked = checked;
			this.marked = marked;
			this.setPicture(new Picture(this, unchecked));
			add(picture);
		}
		
		public NonogramCell(Scene scene) {
			this(new Vector2(0,0), scene, null, null, null);
		}
		
		//Methods
		@Override
		public void update() {
			//update called on each frame
			super.update();
			
			if (this.collide(Input.getMousePos()) && Input.released(Buttons.LeftButton)) {
				this.swapTexture(1);
				BClip temp = new BClip("mouseClick.wav");
				temp.setVolume(0.2f);
				temp.start();
			}
			
			else if (this.collide(Input.getMousePos()) && Input.released(Buttons.RightButton)) {
				this.swapTexture(2);
				BClip temp = new BClip("mouseClick.wav");
				temp.setVolume(0.2f);
				temp.start();
				
			}
		}
		
		public void setPicture(Picture picture) {
			//changes the cell's picture
			this.remove(picture);
			this.picture = picture;
			add(picture);
			this.setHitbox(picture.getWidth(), picture.getHeight());
		}
		
		public void swapTexture(int state) {
			//swaps the cell's texture based on the specified state of the cell
			if (state == 1 && this.picture.getTexture() != checked) {
				this.state = 1;
				this.picture.setTexture(checked);
			}
			else if (state == 2 && this.picture.getTexture() != marked) {
				if (this.picture.getTexture() != checked) {
					this.state = 2;
					this.picture.setTexture(marked);
				}
			}
			else {
				this.picture.setTexture(unchecked);
				this.state = 0;
			}
			setPicture(this.picture);
			Logger.Log(this.state);
		}
		
		public int getState() {
			//gets current state of the cell
			return this.state;
		}
		
		public void setState(int state) {
			//sets state of the cell to coordinate with the nonogram puzzle
			if (state < 0 || state > 2) {
				return;
			}
			else {
				this.state = state;
				if (state == 0) {
					this.picture.setTexture(unchecked);
				}
				else if (state == 1) {
					this.picture.setTexture(checked);
				}
				else {
					this.picture.setTexture(marked);
				}
				setPicture(this.picture);
			}
		}
}
