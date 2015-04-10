package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

/**
 * The player class that plays the sound clip
 *
 */
public class ClipPlayer extends Thread implements LineListener, Runnable{

	private Clip clip;
	private String filename;
	boolean playCompleted = false;

	/**
	 * Sets up the clipPlayer
	 * @param filename
	 * 			The name of the file
	 */
	public ClipPlayer(String filename) {
		this.filename = filename;
	}

	/**
	 * Plays the audio
	 */
	void play() {
		run();
	}

	/**
	 * Returns if the audio has completed playing
	 * @return
	 * 			if the audio has finished
	 */
	public boolean getPlayCompleted() {
		return playCompleted;
	}

	/**
	 * The thread
	 */
	public void run() {
		try {
			// Get URL of audio resource
			URL url = ClipPlayer.class.getResource("/audio/soundEffects/" + filename);
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

		clip.start();
		try {
			Thread.sleep(100);

			while (clip.isActive()) {
				// Wait for playback to complete
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		clip.close();
	}

	/**
	 * Updates the status of the audio
	 */
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();

		if (type == LineEvent.Type.START) {
			System.out.println("Playback started.");

		} else if (type == LineEvent.Type.STOP) {
			playCompleted = true;
			System.out.println("Playback completed.");
		}

	}



}
