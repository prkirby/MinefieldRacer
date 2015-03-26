package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public class ClipPlayer extends Thread implements LineListener, Runnable{

	private Clip clip;
	private String filename;
	boolean playCompleted = false;
	

	public ClipPlayer(String filename) {
		this.filename = filename;
	}

	void play() {
		run();
	}
	
	public boolean getPlayCompleted() {
		return playCompleted;
	}
	
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

		while (clip.isActive()) {
			// Wait for playback to complete
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		clip.close();
	}

	@Override
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
