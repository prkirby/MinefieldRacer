package Server;

import GameMechanics.Map;
import GameMechanics.Player;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * This represents the client thread
 * @author Joseph Ryan
 */
public class Client implements Runnable{

	private Socket clientSocket = null;     //The connection 
	private PrintWriter output = null;
	private BufferedReader input = null;
	private boolean isActive = true;
	private boolean canGetInfo = false;
	public String name = "";
	private boolean win = false;
	private boolean mineHit = false;

	//Data to send to client
	private String data = "";

	//Client data
	private Player player;

	//Map data
	private Map map;
	private Map mineLayer;
	private boolean[][] hasBeen;


	//Modal variables
	private boolean canRace = false;
	private boolean spectatorMode = true;

	//Other info to send to client
	private String time = "n/a";
	private String winMsg = null;
	
	//Passing audio correctly
	private String newSFX = "";
	private String newMusic = "";

	/**
	 * This constructor sets up the client 
	 * with the client's connection socket
	 * 
	 * @param clientSocket 
	 *          The client's socket connection
	 */
	public Client(Socket clientSocket){
		this.clientSocket = clientSocket;
		try{
			output = new PrintWriter(clientSocket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error setting up I/O.");
			this.close();
		}

		player = new Player(4,4); //Temp use
		//Start up data Thread
		DataThread temp = new DataThread();
		new Thread(temp).start();
	}

	/**
	 * Returns the input reader from the client
	 * @return 
	 *          The input reader from the client
	 */
	public BufferedReader input(){
		return this.input;
	}

	/**
	 * Returns the output writer from the client
	 * @return 
	 *          The output writer from the client
	 */
	public PrintWriter output(){
		return this.output;
	}

	/**
	 * Returns the player that relates to this client
	 * @return 
	 *          The Player
	 */
	public Player player(){
		return this.player;
	}

	/**
	 * Returns if the client is allowed to race
	 * @return
	 * 			If the client can race
	 */
	public boolean canRace(){
		return this.canRace;
	}

	/**
	 * Sets the client's race allowance
	 * @param b
	 * 			If the client can or cannot race
	 */
	public void setCanRace(boolean b){
		this.canRace = b;
	}

	/**
	 * Sets up the data that will be sent to the client
	 * @param data 
	 *          The data to send
	 */
	public void setData(String data){
		this.data = data;
	}

	/**
	 * Sets up the winner message to send to client
	 * @param msg 
	 *          The winner message
	 */
	public void setWinMsg(String msg){
		this.winMsg = msg;	
	}

	/**
	 * Returns if the client is active
	 * @return 
	 *          If the client is active
	 */
	public boolean isActive(){
		return this.isActive;
	}

	/**
	 * Returns if the client is able to receive data
	 * @return 
	 *          If the client is able to receive data
	 */
	public boolean canGetInfo(){
		return this.canGetInfo;
	}

	/**
	 * 
	 * @return
	 */
	public boolean inSpectatorMode(){
		return this.spectatorMode;
	}

	/**
	 * Sets if the client is in spectator mode
	 * @param b
	 * 			if in spectator mode
	 */
	public void setSpectatorMode(boolean b){
		this.spectatorMode = b;
	}

	/**
	 * Returns the name of the client
	 * @return
	 * 			The name of the client
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Sets the name of the client
	 * @param s
	 * 			The new name
	 */
	public void setName(String s){
		//System.out.println("we read their name");
		name = s;
	}
	
	/**
	 * Sets if the game has winner
	 * @param winner
	 * 			The client has a winner
	 */
	public void setWinner(boolean winner){
		win = winner;
	}

	/**
	 * This updates the map for collision detection
	 */
	public void updateMap(Map m){
		this.map = m;
	}

	/**
	 * This updates the mineLayer for collision detection
	 */
	public void updateMineLayer(Map mineLayer) {
		this.mineLayer = mineLayer;
	}

	/**
	 * This initializes the hasBeen[][]
	 */
	public void hasBeenInit() {
		hasBeen = new boolean[map.getWidth()][map.getHeight()];
		for(int x = 0; x < map.getWidth(); x++){
			for(int y = 0; y < map.getHeight(); y++){
				hasBeen[x][y] = false;
			}
		}
	}

	/**
	 * This updates hasBeen based on a view port size
	 * 
	 * @param player
	 * 
	 * The player
	 */
	public void updateHasBeen(Player player) {
		int x = player.getX();
		int y = player.getY();
		int port = player.getViewPort();

		//Find view boundaries
		int leftX = (x - port > 0) ? x - port : 0;
		int rightX = (x + port < map.getWidth() - 1) ? x + port : map.getWidth() - 1;
		int topY = (y - port > 0) ? y - port : 0;
		int botY = (y + port < map.getHeight() - 1) ? y + port : map.getHeight() - 1;

		//Update hasBeen
		for (int i = leftX; i <= rightX; i++) {
			for (int j = topY; j <= botY; j++) {
				if (((mineLayer.getMap()[i][j].compareTo("m") == 0 ||
						mineLayer.getMap()[i][j].compareTo("0") == 0) && 
						surroundingRevealed(i,j)>0) ||
						(surroundingRevealed(i,j)>0 && (mineLayer.getMap()[x][y].compareTo("m") == 0 ||
								mineLayer.getMap()[x][y].compareTo("0") == 0) && !mineLayer.getMap()[i][j].equals("-1"))
						
						||(x == i && y == j)){
					if (changeHasBeen(hasBeen[i][j])) {
						player.addAPoint();
					}
					hasBeen[i][j] = true;
				}
				//Reveal four corners code 
				else if(mineLayer.getMap()[x][y].compareTo("0") != 0 && 
						mineLayer.getMap()[x][y].compareTo("m") != 0 &&
						surroundingRevealed(x,y)<2){
					
					if(!mineLayer.getMap()[leftX][topY].equals("-1")){
						if (changeHasBeen(hasBeen[leftX][topY])) {
							player.addAPoint();
						}
						hasBeen[leftX][topY] = true;
					}
					
					if(!mineLayer.getMap()[rightX][topY].equals("-1")){
						if (changeHasBeen(hasBeen[rightX][topY])) {
							player.addAPoint();
						}
						hasBeen[rightX][topY] = true;
					}
					
					if(!mineLayer.getMap()[leftX][botY].equals("-1")){
						if (changeHasBeen(hasBeen[leftX][botY])) {
							player.addAPoint();
						}
						hasBeen[leftX][botY] = true;
					}
					
					if(!mineLayer.getMap()[rightX][botY].equals("-1")){
						if (changeHasBeen(hasBeen[rightX][botY])) {
							player.addAPoint();
						}
						hasBeen[rightX][botY] = true;
					}
				}
			}
		}
	}


	/**
	 * changeHasBeen
	 * 
	 * @param currVal - the a boolean value of the hasBeen array
	 * @return true if hasBeen is currently false, this is used to
	 * 		increment points
	 */
	private boolean changeHasBeen(boolean currVal) {
		if (currVal == false) {
			//System.out.println("Player's points: " + player.getPoints());
			return true;
		}
		return false;
	}

	/**
	 * Check to see how many surrounding tiles have been revealed
	 * @param x
	 * 			The x coord
	 * @param y
	 * 			The y coord
	 * @return
	 * 			The number of surronding open blocks
	 */
	private int surroundingRevealed(int x, int y){
		int count = 0;

		try{
			if(x>0)
				if(hasBeen[x-1][y]) count++;
			if(y>0)
				if(hasBeen[x][y-1]) count++;
			if(x<map.getWidth()-1)
				if(hasBeen[x+1][y]) count++;
			if(y<map.getHeight()-1)
				if(hasBeen[x][y+1]) count++;
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("ERROR: "+x+", "+y);
		}catch(NullPointerException e){
			System.out.println("ERROR: null");
		}

		return count;
	}

	/**
	 * Sets mineHit to true, reset in ClientWriter.
	 * 
	 */
	public void mineCollision() {
		if(!player.checkForFlag(player.getX(), player.getY())
				&& mineLayer.checkForMine(player.getX(), player.getY())) {
			mineHit = true;
		}
	}

	/**
	 * Denotes if the client has hit a mine
	 * @return
	 * 			If the client hit a mine
	 */
	public boolean mineHit() {
		return mineHit;
	}

	/**
	 * Sets if the client hit a mine
	 * @param newHit
	 * 			The client hit a mine maybe?
	 */
	public void setMineHit(boolean newHit) {
		mineHit = newHit;
	}

	/**
	 * Sends the sound effects.
	 * @param sfx
	 * 			The sfx to player
	 */
	public void soundEffect(String sfx) {
		this.newSFX = "AUDIO SFX " +sfx;
		output.println(newSFX);
	}

	/**
	 * 
	 * @param song
	 */
	public void music(String song) {
		newMusic = "AUDIO MUSIC " + song;
		output.println(newMusic);
	}
	
	//powerups
	public String power(){
		String str = "POWERUP ";
		return str + player.getPowerup().getPowerupName() + " ";
	}

	public String sendShield(){
		String str = "MINESHIELD";
		return str + " " + player.getAmIShielded();
	}
	
	public String sendGodMode(){
		String str = "GODMODE";
		return str + " " + player.godStatus();
	}
	/**
	 * The Thread: Reads in data from the client 
	 * and uses it appropriately
	 */
	public void run() {

		//Get initial info (name and color)
		String prints = "";
		try {
			prints = input.readLine();
		} catch (IOException e1) {
		}
		Scanner scan = new Scanner(prints);
		player.setName(scan.next());
		player.setColor(scan.next());
		this.canGetInfo = true;
		while(true){
			try{
				prints = input.readLine();

				//Setup data
				scan = new Scanner(prints);
				String flag = scan.next();
				if(flag.equals("KEYS")){
					boolean keys[] = new boolean[9];
					for(int k = 0; k < keys.length; k++){
						if(scan.nextInt() == 1) keys[k] = true;
						else 					keys[k] = false;
					}

					if(!this.spectatorMode){

						//Movement
						if(keys[0] && map.validLocation(player.getX()-1, player.getY())) player.moveLeft(1);
						if(keys[1] && map.validLocation(player.getX(), player.getY()-1)) player.moveUp(1);
						if(keys[2] && map.validLocation(player.getX()+1, player.getY())) player.moveRight(1);
						if(keys[3] && map.validLocation(player.getX(), player.getY()+1)) player.moveDown(1);

						boolean setFlag = false;

						//Flag Setting
						if(keys[4] && map.validLocation(player.getX()-1, player.getY()) && 
								!hasBeen[player.getX()-1][player.getY()]) {
							setFlag = player.setFlag(player.getX()-1, player.getY());
							correctFlag(player.getX()-1, player.getY());
						}
						if(keys[5] && map.validLocation(player.getX(), player.getY()-1) &&
								!hasBeen[player.getX()][player.getY()-1]) {
							setFlag = player.setFlag(player.getX(), player.getY()-1);
							correctFlag(player.getX(), player.getY()-1);
						}
						if(keys[6] && map.validLocation(player.getX()+1, player.getY()) &&
								!hasBeen[player.getX()+1][player.getY()]) {
							setFlag = player.setFlag(player.getX()+1, player.getY());
							correctFlag(player.getX()+1, player.getY());
						}
						if(keys[7] && map.validLocation(player.getX(), player.getY()+1) &&
								!hasBeen[player.getX()][player.getY()+1]){
							setFlag =player.setFlag(player.getX(), player.getY()+1);
							correctFlag(player.getX(), player.getY()+1);
						}

						if(setFlag) {
							soundEffect("flag");
						}

						//usedPoweurp
						if(keys[8]){
							String powerUpName = player.getPowerup().getPowerupName();
							if (powerUpName.compareTo("GodMode") == 0) {
								music("God");
							}
							else if (powerUpName.compareTo("Nuke") != 0) {
								soundEffect(powerUpName);
							} 
							player.usePowerup();
						}
						
						
						//Update hasBeen array
						updateHasBeen(player);

						//Check if mine has been hit
						mineCollision();

					} else{
						this.player.setX(0);
						this.player.setY(0);
						// Initialize the hasBeen array
						hasBeenInit();
						hasBeen[0][0] = true;
					}
				}

				sleep(15);//15ms
			} catch (Exception e){
				System.out.println(""+clientSocket+" disconnnected.");
				this.close();
				break;
			}
		}
	}
	
	public void correctFlag(int x, int y) {
		if(mineLayer.checkForMine(x, y)) {
			this.player().incrementFlagStreak();
		}
	}

	/**
	 * This closes the client
	 */
	public void close(){
		try{
			this.clientSocket.close();
			this.input.close();
		}catch(IOException e){
			throw new RuntimeException("Error closing client: "+clientSocket, e);
		}
		this.output.close();
		isActive = false;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * This thread endless sends the prepared data to the client
	 */
	public class DataThread implements Runnable{

		/**
		 * The thread: sends the data to the client
		 */
		public void run() {
			while(true){
				output.println(data);
				
				try{
					//Send the mode
					if(spectatorMode){
						output.println("MODE SPEC "+time+" "+map.getName());
					}else{
						output.println("MODE RACE "+time);
					}
					

					//Send the map
					output.println(sendMap());
					
					//Send if winner applicable
					if(winMsg==null){
						output.println("WINNER 0");
					}else{
						output.println("WINNER 1 "+winMsg);
					}
					
					//Send audio/music
//					if(!newSFX.equals("")){
//						output.println(newSFX);
//						newSFX = "";
//					}
//					if(!newMusic.equals("")){
//						output.println(newMusic);
//						newMusic = "";
//					}
					
				}catch(NullPointerException e){
				}catch(java.lang.ArrayIndexOutOfBoundsException e){
				}catch(Exception e){break;}	
				
				this.sleep(15);
			}
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
		 * 
		 * @return
		 */
		public String sendMap() throws NullPointerException, ArrayIndexOutOfBoundsException{
			String ret = "MAP ";
			for(int x=player.getX()-5; x <= player.getX()+5; x++){
				for(int y=player.getY()-5; y <= player.getY()+5; y++){
					if(x < 0 || x > map.map.length-1 || y < 0 || y > map.map[0].length-1)
						ret += "n ";
					else if (player.checkForFlag(x, y))
						ret += "f ";
					else if (hasBeen[x][y] && 
							map.getMap()[x][y].compareTo("c") == 0 
							//&& mineLayer.getMap()[x][y].compareTo("-1") != 0
							)
						ret += mineLayer.getMap()[x][y]+" ";
					else
						ret += map.getMap()[x][y] + " ";
				}
			}

			return ret;
		}
	}
}
