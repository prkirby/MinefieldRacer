package Server;

import Drawing.StartupGUI;
import GameMechanics.Entity;
import audio.SFX;
import audio.SongPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This thread sets up a connection then serves 
 * to receive data from the server
 * 
 * @author Joseph Ryan
 */
public class ConnectionThread implements Runnable{

	private final int serverPort = 1111;


	private final String ipAddress = "192.168.1.76";

	private Socket socket = null;                                       //The client's socket
	private BufferedReader input = null;                                //Input from server
	private PrintWriter output = null;                                  //Output to server

	private InputThread in;                                             //The client input thread
	private SongPlayer songPlayer = new SongPlayer("lobby");			//The clients music player
	private Thread musicThread = new Thread(songPlayer);				//The music thread

	//Timeout Timer Variables
	private Timer t = new Timer();                                      //The timer for connection timeout
	private boolean connected = false;                                  //Determines if a connection was made
	private boolean tryC = true;                                        //Shows an attempt to connect

	/**
	 * The default constructor: Attempts a connection 
	 * to the server. If connection fails, timeout and 
	 * close occurs. If connection succeeds threads are 
	 * set up
	 * @throws InterruptedException 
	 */
	public ConnectionThread(){

		//Get data from user
		StartupGUI s = new StartupGUI();
		int c = 0;
		while(true){
			if(s.dataReady()){ break;}
			System.out.checkError(); //Used to slow down loop
		};
		String name = s.getName();
		String color = s.getColor();
		s = null;

		while(true){
			try{
				if(tryC){
					t.schedule(new RemindTask(), 10000);
					System.out.println("Attempting to connect...");
					socket = new Socket(ipAddress,serverPort); //This is the connection to the server
					input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					output = new PrintWriter(socket.getOutputStream(), true);

					connected = true;
					in = new InputThread(input, output, name, color);
					new Thread(in).start();
					// Start lobby music
					musicThread.start();
					break;
				}
			}catch(Exception e){
				System.out.println("Could not connect");
			}
		}

		System.out.println("Connected."); //For debugging
	}   

	/**
	 * This is the thread. It continuously receives 
	 * data from the server and places it appropriately
	 */
	public void run() { 
		String checkDupe = ""; //Checks for duplicate chat messages (may be used later)
		while(true){
			String inn = ""; //Input from server
			try {

				inn = input.readLine();
				if((!checkDupe.equals(inn) && checkDupe.startsWith("TEXT")) || !checkDupe.startsWith("TEXT")){
					checkDupe = inn;
					Scanner scan = new Scanner(inn);
					String flag = scan.next();
					
					if(flag.equals("DATA")){
						this.readData(scan);
					}else if(flag.equals("MAP")){
						this.readMap(scan);
					}else if(flag.equals("MODE")){
						this.readMode(scan);
					}else if(flag.equals("AUDIO")){
						this.readAudio(scan);
					}else if (flag.equals("POWERUP")){
						this.readPowerup(scan);
					}
					else if(flag.equals("WINNER")){
						this.readWinner(scan);
					}else
						this.close();
				}

			} catch (IOException ex) {
				System.out.println("Error getting output from server (IOException): "+inn);
				//ex.printStackTrace(); //pretend this doesn't happen
				this.close();
			} catch (java.lang.NullPointerException e){
				System.out.println("Error getting output from server (NULL): "+inn);
				e.printStackTrace(); //pretend this doesn't happen
				this.close();
			} catch (java.util.NoSuchElementException e){
				//e.printStackTrace(); //pretend this doesn't happen
			} catch (Exception e){
				System.out.println("Error getting output from server ("+e.toString()+"): "+inn);
				//e.printStackTrace(); //pretend this doesn't happen
				this.close();
			}
			sleep(3);
		}
	}

	/**
	 * Reads the data type "DATA" from the server 
	 * and sends it to the GUI
	 * @param scan 
	 *          The scanner to use
	 */
	public void readData(Scanner scan){
		ArrayList<Entity> data = new ArrayList<Entity>();
		int x, y, crown, flags;
		String color;
		x = scan.nextInt();
		y = scan.nextInt();
		color = scan.next();
		crown = scan.nextInt();
		in.mainGUI().setFlags(scan.nextInt());
		data.add(new Entity(x, y, color, crown == 1));
		while(scan.hasNext()){
			x = scan.nextInt();
			y = scan.nextInt();
			color = scan.next();
			crown = scan.nextInt();
			data.add(new Entity(x, y, color, crown == 1));
		}        
		in.mainGUI().coords(data);
		in.mainGUI().display();
	}

	/**
	 * Scans the map data for drawing the map
	 * @param scan
	 */
	public void readMap(Scanner scan){
		String[][] ret = new String[11][11];
		for(int x = 0; x < 11; x++){
			for(int y = 0; y < 11; y++){
				ret[x][y] = scan.next();
			}
		}

		in.mainGUI().map(ret);
	}

	/**
	 * Scans the mode and performs the actions accordingly
	 * @param scan
	 * 			The scanner being used
	 */
	public void readMode(Scanner scan){
		in.mainGUI().setMode(scan.next());
		in.mainGUI().setTime(scan.next());
		in.mainGUI().setMapName(scan.next());
	}

	public void readWinner(Scanner scan){
		boolean winner = scan.nextInt()==1;
		if(winner)
			in.mainGUI().setWinner(winner, scan.next(), scan.next());
		else
			in.mainGUI().setWinner(winner, "", "");
	}
	public void readPowerup(Scanner scan){
		String currentPowerup = scan.next();
		System.out.println(currentPowerup);
		if(currentPowerup.equals("noPowerup")){
			in.mainGUI().setPowerup("noPowerup");
		}
		else if(currentPowerup.equals( "Invisibility")){
			in.mainGUI().setPowerup("Invisibility");
		}
	}
	public void readAudio(Scanner scan) throws InterruptedException {
		String tag = scan.next();

		if (tag.equals("SFX")){
			String sfxName = scan.next();
			if (sfxName.compareTo("mineHit") == 0) {
				SFX.EXPLODE.play();
			} else if (sfxName.compareTo("flag") == 0) {
				SFX.FLAG.play();
			}
		} else if (tag.equals("MUSIC")) {
			String songName = scan.next();
			if (songName.compareTo("lobby") == 0) {
				songPlayer.stop();
				musicThread.join();
				songPlayer = new SongPlayer("lobby");
				musicThread = new Thread(songPlayer);
				musicThread.start();
			}
			if (songName.compareTo("bg1") == 0) {
				songPlayer.stop();
				musicThread.join();
				songPlayer = new SongPlayer("bg1");
				musicThread = new Thread(songPlayer);
				musicThread.start();
			}
		}
	}

	/**
	 * Closes the thread when needed
	 */
	public void close(){
		try{
			this.input.close();
		}catch(IOException e){
			throw new RuntimeException("Error closing.", e);
		}catch(NullPointerException e){System.exit(0);}
		this.output.close();
		System.exit(0);
	}

	/**
	 * Tells the thread to sleep for a set amount of time
	 * @param dur 
	 *          The duration the thread sleeps
	 */
	public void sleep(int dur){
		try{
			Thread.sleep(dur);
		}catch(InterruptedException e){
			System.out.println("Room has been interrupted");
		}
	}

	/**
	 * The timeout timer task. When timeout is reached, the client closes.
	 */
	public class RemindTask extends TimerTask{

		/**
		 * This is called when timeout time is reached.
		 */
		public void run(){  
			if(!connected){tryC = false; close();}
		}
	}
}
