package game.audio;

import java.util.ArrayList;
import java.util.Iterator;

import game.util.Logger;

public class AudioPlayer {
	
	private static ArrayList<BClip> playingClips = new ArrayList<BClip>();
	private static ArrayList<BClip> finishedClips = new ArrayList<BClip>();
	
	private static boolean inPlayingClips(BClip clip) {
		boolean isPlaying = false;
		for (var c : playingClips) {
			if (clipsAreEqual(clip, c)) {
				isPlaying = true;
				break;
			}
		}
		return isPlaying;
	}
	
	private static boolean inFinishedClips(BClip clip) {
		boolean isFinished = false;
		for (var c : finishedClips) {
			if (clipsAreEqual(clip, c)) {
				isFinished = true;
				break;
			}
		}
		return isFinished;
	}
	
	
	private static void add(BClip clip) {
		if (inPlayingClips(clip) == false) {
			playingClips.add(clip);
		}
	}
	
	private static void remove(BClip clip) {
		if (inPlayingClips(clip) == true) {
			removeClip(clip.getFilename());
		}
		if (inFinishedClips(clip) == false) {
			finishedClips.add(clip);
		}
	}
	
	private static void removeClip(String filename) {
		Iterator<BClip> itr = playingClips.iterator();
		 
        while (itr.hasNext()) {
            BClip c = (BClip)itr.next();
            if (c.getFilename().equals(filename)) {
            	itr.remove();
            	break;
            }
        }
	}

	private static boolean clipsAreEqual(BClip clip1, BClip clip2) {
		return clip1.getFilename().equals(clip2.getFilename());
	}
	
	public static void start(BClip clip) {
		
		if (inPlayingClips(clip) == false) {
			clip.start();
			playingClips.add(clip);
			
		} else {
			Logger.Log("Clip is already playing AudioPlayer_start");
		}
	}
	
	public static BClip get(BClip clip) {
		for (var c : playingClips) {
			if (clipsAreEqual(c, clip)) {
				return c;
			}
		}
		return null;
	}
	
	
	
	public static void startOnce(BClip clip) {
		
		if (inPlayingClips(clip) == true) {
			Logger.Log("Clip is already playing AudioPlayer_startOnce");
		} 
		else if (inFinishedClips(clip) == true) {
			Logger.Log("Clip has already finished playing AudioPlayer_startOnce");
		}
		else {
			clip.start();
			playingClips.add(clip);
		}
	}
	
	public static void stop(BClip clip) {
		clip.stop();
		remove(clip);
	}
	
	public static void stopAll() {
		
		for (var c : playingClips) {
			c.stop();
			if (!inFinishedClips(c)) {
				finishedClips.add(c);
			}
		}
		playingClips.clear();
	}
	
	public static int clipsPlaying() {
		return playingClips.size();
	}
	
	public static int clipsFinished() {
		return finishedClips.size();
	}
	
	private static void trim() {
        Iterator<BClip> itr = playingClips.iterator();

        while (itr.hasNext()) {
 
            BClip c = (BClip)itr.next();
            if (c.getIsPlaying() == false) {
                itr.remove();
                if (inFinishedClips(c) == false) {
                    finishedClips.add(c);
                }
            }

        }
    }

	public static void reset() {
		stopAll();
		finishedClips.clear();
	}
	
	public static void update() {
		trim();
	}
	
	
}
