package game.audio;

import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.Clip;

// Represents a collection of BClips.
public class BClipSet implements Iterable<BClip> {
	
	/// Fields
	private ArrayList<BClip> clips;
	
	/// Constructors
	public BClipSet() {
		clips = new ArrayList<BClip>();
	}
	
	/// Methods
	// starts the clip at the specified index
	public void start(int index) {
		AudioPlayer.start(clips.get(index));	
	}
	
	public void startOnce(int index) {
		AudioPlayer.startOnce(clips.get(index));
	}
	
	
	// stops the clip at the specified index
	public void stop(int index) {
		AudioPlayer.stop(clips.get(index));
	}
	
	public void stopAll() {
		for (int i = 0; i < clips.size(); i++) {
			AudioPlayer.stop(clips.get(i));
		}
	}
	
	// returns the clip at the specified index
	public BClip get(int index) {
		return clips.get(index);
	}
	
	public void add(BClip clip) {
		clips.add(clip);
	}

	@Override
	public Iterator<BClip> iterator() {
		return clips.iterator();
	}
	
	
}
