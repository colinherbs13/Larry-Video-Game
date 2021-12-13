package game.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.Game;
import game.util.Calc;
import game.util.Logger;
import gameLogic.GameState;

// BertButEt Clip. Wrapper class for Clip.
public class BClip {

	/// Fields
	private Clip clip = null;
	private String filename;
	private FloatControl gControlPan;
	private FloatControl gControlVol;
	public Boolean isLooping = false;
	private float pan = 0f;
	private float trueVolume = 1f;
	private float volume = 1f;
	private int startFrame = 0;
	private int endFrame = -1;
	
	/// Constructors
	public BClip(String filename) {
		this.filename = filename;
		String soundName = Game.contentPath + filename;    
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.Log(filename);
		gControlPan = (FloatControl)clip.getControl(FloatControl.Type.PAN);
		gControlVol = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	/// Methods
	// starts the clip at the startFrame, and ends at the endFrame.
	public void start(int startFrame, int endFrame) {
		this.clip.setFramePosition(startFrame);
		this.clip.setLoopPoints(startFrame, endFrame);
		this.setVolume(this.trueVolume);
		this.clip.start();
		if (isLooping) {
			clip.loop(-1);
		}
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	// starts and ends at the BClip's start and end frames.
	public void start() {
		start(startFrame, endFrame);
	}
	
	public void setPan(float pan) {
        this.pan = Calc.snap(pan, -1f, 1f);
        gControlPan.setValue(pan);
    }
	
	public float getPan() {
		return this.pan;
	}
	
	public void setVolume(float volume) {	
		this.volume = Calc.snap(volume * GameState.getVolumeModifier(), 0f, 1f);
		gControlVol.setValue(20f * (float) Math.log10(this.volume));
	}
	
	public float getVolume() {
		return this.volume;
	}
	
	public int getStartFrame() {
		return this.startFrame;
	}
	
	public void setStartFrame(int frame) {
		this.startFrame = frame;
	}
	
	public int getEndFrame() {
		return this.endFrame;
	}
	
	public void setEndFrame(int frame) {
		this.endFrame = frame;
	}
	
	public int getTotalFrame() {
		return this.clip.getFrameLength();
	}
	
	// returns true if the clip is playing
	public Boolean getIsPlaying() {
		return clip.isActive();
	}
	
	// stops the clip.
	public void stop() {
		this.clip.stop();
	}
	
}
