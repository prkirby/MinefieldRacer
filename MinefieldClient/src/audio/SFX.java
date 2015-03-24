package audio;

public enum SFX {
	MOVE("move.wav"),
	SHOOT("gun.wav"),
	EXPLODE("explosion.wav");

	private String filename;

	private SFX(String filename) {
		this.filename = filename;
	}

	// Play or Re-play the sound effect from the beginning, by rewinding.
	public void play() {
		Thread t = new Thread(new ClipPlayer(filename));
		t.start();
	}
	
	// Static initializer method for the enum
	public static void init() {
		values();
	}
}
