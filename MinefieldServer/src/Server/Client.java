package Server;

import GameMechanics.Map;
import GameMechanics.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Xazaviar
 */
public class Client implements Runnable{

	private Socket clientSocket = null;     //The connection 
	private PrintWriter output = null;
	private BufferedReader input = null;
	private boolean isActive = true;
	private boolean canGetInfo = false;
	public String name = "";
	private boolean win = false;
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
	
	public void setSpectatorMode(boolean b){
		this.spectatorMode = b;
	}

	public String getName(){
		return name;
	}
	
	public void setName(String s){
		name = s;
	}
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
				if (mineLayer.getMap()[x][y].compareTo("0") == 0 ||
						mineLayer.getMap()[i][j].compareTo("0") == 0 ||
						(x == i && y == j)){
					hasBeen[i][j] = true;
				}
			}
		}
	}

	/**
	 * Currently just moves player back to [1,y] if they share
	 * a space with a mine. Gets called whenever a player moves.
	 * 
	 */
	public void mineCollision() {
		if(mineLayer.checkForMine(player.getX(), player.getY())) {
			// Can do other things here
			
			player.setX(1);
			// Remove this after debugging
			//System.out.println("You fucked up!!");
		}
	}
	
	/**
	 * The Thread: Reads in data from the client 
	 * and uses it appropriately
	 */
	public void run() {
		this.canGetInfo = true;
		while(true){
			try{
				String prints = input.readLine();

				//Setup data
				Scanner scan = new Scanner(prints);
				String flag = scan.next();
				if(flag.equals("KEYS")){
					boolean keys[] = new boolean[4];
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
					//Update hasBeen array
					updateHasBeen(player);
					//Check if mine has been hit

					//update uncover

					mineCollision();
					
					} else{
						this.player.setX(0);
						this.player.setY(0);
						// Initialize the hasBeen array
						hasBeenInit();
						hasBeen[0][0] = true;
					}
				}

				sleep(25);
			} catch (Exception e){
				System.out.println(""+clientSocket+" disconnnected.");
				this.close();
				break;
			}
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
				}catch(NullPointerException e){}
				this.sleep(20);
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
		public String sendMap() throws NullPointerException{
			String ret = "MAP ";
			for(int x=player.getX()-5; x <= player.getX()+5; x++){
				for(int y=player.getY()-5; y <= player.getY()+5; y++){
					if(x < 0 || x > map.map.length-1 || y < 0 || y > map.map[0].length-1)
						ret += "n ";
					else if (hasBeen[x][y] && 
							map.getMap()[x][y].compareTo("c") == 0 &&
							mineLayer.getMap()[x][y].compareTo("-1") != 0)
						ret += mineLayer.getMap()[x][y]+" ";
					else
						ret += map.getMap()[x][y] + " ";
				}
			}

			return ret;
		}
	}
}
