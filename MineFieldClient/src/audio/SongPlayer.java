package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public class SongPlayer implements Runnable{

	private SourceDataLine song;
	private String filename = "";
	boolean playCompleted = false;
	boolean running = true;
	//Buffer size for sourceLine
	int BUFFER_SIZE = 64*1024;

	public SongPlayer(String filename) {
		this.filename = filename;
	}

	public void run() {

		while (running) {
			try {
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
				while (nBytesRead != -1 && running) {
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
			} finally {
				song.drain();
				song.close();
			}
		}


	}
	
	public void stop() {
		running = false;
	}
	
	public void start() {
		running = true;
		run();
	}


}
