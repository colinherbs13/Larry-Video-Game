package game.scenes;

// A template used for making scenes.
// do NOT inherit from this class. Instead copy the contents and add on to it.
public final class SCENE_TEMPLATE extends Scene {

	/// add entity declarations and additional things here
	
	public SCENE_TEMPLATE() {
		super();
		// do not touch!
	}
	
	@Override
	public void begin() {
		super.begin();
		// add entity initiations, setup, etc.
		// add ALL entities to scene using this.add(entity)
	}
	
	@Override
	public void update() {
		super.update();
		// super.update() already updates entities. add any additional update here.
	}
	
	@Override
	public void render() {
		super.render();
		// don't touch unless you specifically need to add something.
	}
	
}
