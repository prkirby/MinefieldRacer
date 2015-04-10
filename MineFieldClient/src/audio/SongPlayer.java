package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

/**
 * This class is a thread that plays music
 * @author Paul Kirby
 *
 */
public class SongPlayer implements Runnable{

	private SourceDataLine song;
	private String filename = "";
	boolean playCompleted = false;
	boolean running = true;
	boolean newSong = false;
	//Buffer size for sourceLine
	int BUFFER_SIZE = 64*1024;

	/**
	 * Starts the music thread with the given file name.
	 * @param filename
	 * 			The name of the audio
	 */
	public SongPlayer(String filename) {
		this.filename = filename;
	}

	/**
	 * The thread
	 */
	public void run() {

		while (running) {
			try {
				String tempFile = filename;
				// Get URL of audio resource
				URL url = SongPlayer.class.getResource("/audio/music/" + filename + ".wav");
				// Set up an audio input stream piped from the sound file.
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
				AudioFormat audioFormat = audioInputStream.getFormat();
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
				song = (SourceDataLine) AudioSystem.getLine(info);
				song.open(audioFormat);
				song.start();
				int nBytesRead = 0;
				byte[] sampledData = new byte[BUFFER_SIZE];
				while (nBytesRead != -1 && tempFile == filename) {
					nBytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
					if (nBytesRead >= 0) {
						// Writes audio data to the mixer via this source data line.
						song.write(sampledData, 0, nBytesRead);
					} 
				}
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch(java.lang.IllegalArgumentException e){
				
			}finally {
				song.drain();
				song.close();
			}
		}


	}
	
	/**
	 * Stops the music
	 */
	public void stop() {
		running = false;
	}
	
	/**
	 * Starts the music
	 */
	public void start() {
		running = true;
		run();
	}
	
	/**
	 * 
	 * @param filename
	 * 			The name
	 * @throws InterruptedException
	 * 			throws a bad bad error
	 */
	public void newSong(String filename) throws InterruptedException {
		this.filename = filename;
	}


}
