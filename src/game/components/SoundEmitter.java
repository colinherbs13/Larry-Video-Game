package game.components;

import game.Entity;
import game.audio.AudioPlayer;
import game.audio.BClip;
import game.audio.BClipSet;
import game.util.Calc;
import game.util.Logger;
import game.util.Vector2;

// Emits sound from an entity.
// Can change pan and volume of sounds based on distance from target listener.
public class SoundEmitter extends Component {

	/// Fields
	private BClipSet clips;
	private Vector2 offset;
	private Entity target;
	private static final float PAN_FACTOR = 50f;
	private static final float VOL_FACTOR = 500f;
	
	/// Constructors
	public SoundEmitter(Vector2 offset) {
		clips = new BClipSet();
		this.offset = offset;
		target = null;
	}
	
	public SoundEmitter() {
		this(new Vector2(0,0));
	}
	
	/// Methods
	public void start(int index) {
		clips.start(index);
		if (target != null) {
			clips.get(index).setPan(panCalc(getXDistance(target.position)));
			clips.get(index).setVolume(volCalc(getMagnitude(target.position)));
		}
	}
	
	public void startOnce(int index) {
		clips.startOnce(index);
		if (target != null) {
			clips.get(index).setPan(panCalc(getXDistance(target.position)));
			clips.get(index).setVolume(volCalc(getMagnitude(target.position)));
		}
	}
	
	public void stop(int index) {
		clips.stop(index);
	}
	
	public void stopAll() {
		clips.stopAll();
	}
	
	public void add(BClip clip) {
		if (AudioPlayer.get(clip) != null) {
			clips.add(AudioPlayer.get(clip));
		} else {
			clips.add(clip);
		}
		
	}
	
	public Entity getTarget() {
		return this.target;
	}
	
	public void setTarget(Entity entity) {
		this.target = entity;
	}
	
	@Override
	public void update() {
		for (var c : clips) {
			if (c.getIsPlaying()) {
				//Logger.Log(c.getVolume());
				if (target != null) {
					c.setPan(panCalc(getXDistance(target.position)));
					c.setVolume(volCalc(getMagnitude(target.position)));
				}
			}
		}
	}

	@Override
	public void render() {
		// None	
	}
	
	private Vector2 getPosition() {
		if (this.entity != null) {
			return entity.position.subtract(offset);
		}
		else {
			return offset.inverse();
		}
	}
	
	private float getMagnitude(Vector2 point) {
		return this.getPosition().subtract(point).magnitude();
	}
	
	private float getXDistance(Vector2 point) {
		return this.getPosition().subtract(point).x;
	}
	
	private float panCalc(float x) {
		double scaleFactor = 1 / PAN_FACTOR;
		return Calc.snap((float)((2 * Math.atan(x * scaleFactor)) / Math.PI), -1f, 1f);
	}
	
	private float volCalc(float mag) {
		float scaleFactor = 1 / VOL_FACTOR;
		return Calc.snap((float)Math.exp(scaleFactor * -mag), 0f, 1f);
	}

}
