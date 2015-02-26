package audio;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public enum SFX {
	MOVE("move.wav"),
	SHOOT("gun.wav"),
	MP3("mp3Test.mp3");

	private Clip clip;

	private SFX(String filename) {
		try {
			// Get URL of audio resource
			URL url = SFX.class.getResource("/audio/soundEffects/" + filename);
			// Set up an audio input stream piped from the sound file.
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			// Get a clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	// Play or Re-play the sound effect from the beginning, by rewinding.
	public void play() {
		if (clip.isRunning())
			clip.stop();   // Stop the player if it is still running
		clip.setFramePosition(0); // rewind to the beginning
		clip.start();     // Start playing
	}
	
	// Static initializer method for the enum
	public static void init() {
		values();
	}
}
