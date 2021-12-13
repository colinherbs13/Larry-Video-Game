package game.scenes;

import java.util.ArrayList;

import game.Entity;
import game.InteractiveButton;
import game.audio.BClip;
import game.graphics.Picture;
import game.graphics.Texture;
import game.util.Interfaces;
import game.util.Vector2;

public final class Tutorial extends Scene {

		/// add entity declarations and additional things here
		private static Entity screenContainer = new Entity();
		private static ArrayList<Picture> screens = new ArrayList<Picture>();
		private static int curScreenNum = 0;
		private InteractiveButton forward = new InteractiveButton(new Vector2(555, 300), this, new Texture("forward.png"), new Texture("forward.png"), Interfaces.nextTutorial);
		private InteractiveButton backward = new InteractiveButton(new Vector2(50, 300), this, new Texture("back.png"), new Texture("back.png"), Interfaces.prevTutorial);
		
	
		public Tutorial() {
			super();
			// do not touch!
		}
		
		@Override
		public void begin() {
			super.begin();
			forward.setSound(new BClip("mouseClick.wav"));
			backward.setSound(new BClip("mouseClick.wav"));
			// add entity initiations, setup, etc.
			// add ALL entities to scene using this.add(entity)
			for (int i = 1; i < 6; i++) {
				Picture background = new Picture("tutorial" + i + ".png");
				screens.add(background);
			}
			screenContainer.add(screens.get(0));
			add(screenContainer);
			add(forward);
			add(backward);
			
			this.renderer.camera.setScale(3);
		}
		
		@Override
		public void update() {
			super.update();
			// super.update() already updates entities. add any additional update here.
			screenContainer.add(screens.get(curScreenNum));
			forward.bringToFront();
			backward.bringToFront();
		}
		
		@Override
		public void render() {
			super.render();
			// don't touch unless you specifically need to add something.
		}
		
		public static void nextScreen() {
			if (curScreenNum == screens.size() - 1) {
				Interfaces.NewGame.function();
			}
			else {
				curScreenNum += 1;
				screenContainer.remove(screens.get(curScreenNum - 1));
			}
		}
		
		public static void prevScreen() {
			if (curScreenNum == 0) {
				return;
			}
			else {
				screenContainer.remove(screens.get(curScreenNum));
				curScreenNum -= 1;
			}
		}
}
