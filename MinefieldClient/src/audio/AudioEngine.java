package audio;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


import javax.sound.sampled.*;

public class AudioEngine {

	public static Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
	public static Mixer mixer = AudioSystem.getMixer(mixerInfo[0]);
	public static HashMap<String, SoundEffect> sfxMap = new HashMap<String, SoundEffect>();
	
	/**
	 * Override Default constructor to initial audio maps
	 */
	public AudioEngine() {
		soundEffectsInit();
	}
	
	/**
	 * Instantiates Sound Effects
	 */
	public void soundEffectsInit() {
		SoundEffect move = new SoundEffect("move.wav");
		sfxMap.put("move", move);

	}
	/**
	 * Prints the info of the users AudioSystem, i.e. inputs and outputs
	 * Used for debugging purposes
	 * 
	 */
	public void printInfo() {
		for (Mixer.Info info : mixerInfo) {
			System.out.println(info.getName() + " --- " + info.getDescription());
		}
	}

	/**
	 * A private class for creating Sound Effect objects with 
	 * appropriate lines in the mixer and audio file reference
	 */
	private class SoundEffect {

		private Clip clip;
		DataLine.Info info = new DataLine.Info(Clip.class, null);


		public SoundEffect(String fileName) {
			
			try {
				clip = (Clip) mixer.getLine(info);
			} catch (LineUnavailableException e) { e.printStackTrace(); }
			
			try {
				URL audioURL = AudioEngine.class.getResource("soundEffects/" + fileName);
				System.out.println(audioURL);
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioURL);
				clip.open(audioStream);
			} catch (LineUnavailableException e) { 
				e.printStackTrace(); 
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
			}
				
		}
		
		public void play(){
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			clip.start();
		}
//		public void stop() {
//			clip.stop();
//		}

	}
	
	/**
	 * Plays a SoundEffect object
	 * @param filename
	 */
	public void playSFX(String filename) {
		System.out.println(filename);
		System.out.println(sfxMap);
		sfxMap.get(filename).play();
		
	}

}
