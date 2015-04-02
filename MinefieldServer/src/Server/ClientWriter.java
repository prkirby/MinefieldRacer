package Server;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import GameMechanics.Map;
import GameMechanics.MineCreation;
import GameMechanics.Player;
import Main.FileReading;

/**
 * This class sets up all of the appropriate data 
 * and sends it to the clients that are currently 
 * within this thread
 * 
 * @author Joseph Ryan
 */
public class ClientWriter implements Runnable {

	private ArrayList<Client> clients = new ArrayList<Client>(); //The clients contained within this thread
	private Map map = new Map(new File("MAPS/funnel.txt"));
	private Map mineLayer;
	private static Map mapArray[];
	private double minePercentage = 0.10;

	private final int raceTime = 5 * 60 * 1000; //5 Minutes
	private final int lobbyTime = 5*1000;//30 * 1000;	//30 Seconds
	private int currentTime = lobbyTime;		//Time counter
	private boolean inRace = false;				//Race = true, lobby = false;

	private boolean someoneWon = false;			//Temporary variable

	private Player previousWinner = null;		//Pointer to previous winner
	
	private Timer powerupTimer = new Timer();


	/**
	 * The default constructor (not in use)
	 */
	public ClientWriter(){
		FileReading temp = new FileReading();
		File f  [] = temp.getMaps();
		mapArray = new Map[f.length];
		for(int i = 0; i < mapArray.length; i++){
			mapArray[i] = new Map(f[i]);
		}

		//choose random map
		map = mapArray[getRandomNumber()];
	}

	/**
	 * Returns the list of clients in this thread
	 * @return 
	 *          The list of clients in this thread
	 */
	public ArrayList<Client> clients(){
		return this.clients;
	}

	/**
	 * The thread: Compiles all the 
	 * necessary data and sends it to 
	 * all current clients.
	 */
	public void run() {
		while(true){

			checkClients();
			ArrayList<Client> temp = clients;

			for(int w = 0; w < temp.size(); w++){
				try{
					if(temp.get(w).canGetInfo())
						temp.get(w).setData(setupData(w));
				}catch(IndexOutOfBoundsException e){}
			}
			checkClients();
			sleep(2); //2 ms
			switchModes();
		}
	}

	/**
	 * This determines if a mode switch is necessary. If it is, it does it.
	 */
	private void switchModes(){
		if(this.clients().size()>1 || inRace)
			currentTime-=2;//2 ms
		if(this.clients().size() == 0){
			this.inRace = false;
			this.currentTime = lobbyTime;
			this.someoneWon = false;
		}

		if(this.currentTime <= 0 && !inRace){
			System.out.println("STARTING RACE");
			this.inRace = true;
			this.someoneWon = false;
			this.currentTime = raceTime;

			//Start race
			//Populate mineLayer with mines and numbers
			mineLayer = MineCreation.createMineLayer(map.map, minePercentage);

			//Place all current clients at the starting line
			for(int c = 0; c < this.clients.size(); c++){
				if(this.clients.get(c).canRace()){
					this.clients.get(c).setSpectatorMode(false);
					this.clients.get(c).music("bg1");
					this.clients.get(c).player().setX(1);
					this.clients.get(c).player().setY(((int)((Math.random()*100)%(this.map.getHeight()-3)))+1);
					this.clients.get(c).player().resetFlags();
					this.clients.get(c).setWinMsg(null);
				}
			}

		}else if(this.currentTime <= 0 && inRace){
			System.out.println("ENTERING LOBBY.");
			//create random number
			int rndm = getRandomNumber();
			//choose random map
			map = mapArray[rndm];
			this.inRace = false;
			this.currentTime = lobbyTime;

			//Start Lobby
			for(int c = 0; c < this.clients.size(); c++){
				this.clients.get(c).setSpectatorMode(true);
				this.clients.get(c).music("lobby");
				this.clients.get(c).setWinMsg(null);
			}
		}

		if(inRace && this.currentTime == 10000){
			System.out.println("RETURNING TO LOBBY IN 10 SECONDS.");
		}
		if(!inRace && this.currentTime == 10000){
			System.out.println("STARTING RACE IN 10 SECONDS.");
		}
	}


	public static int getRandomNumber(){
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((mapArray.length -1 ));

		return randomNum;
	}


	/**
	 * Double checks to see if all clients 
	 * in the client list are still there. 
	 * Removes them as necessary
	 */
	public void checkClients(){
		try{
			for(int cl = 0; cl < clients.size(); cl++){
				if(!clients.get(cl).isActive()){
					clients.remove(cl);
					cl--;
				}else{ //If the client exists
					clients.get(cl).updateMap(map);
					clients.get(cl).updateMineLayer(mineLayer);
					clients.get(cl).setTime(this.formatTime());
					if(!this.inRace)
						clients.get(cl).setCanRace(true);

					//If a client met win Condition
					if(checkWinCondition(cl) && !this.someoneWon){
						this.currentTime = 10*1000; //Ten seconds left
						this.someoneWon = true;

						//Crown the winner
						if(previousWinner!=null)
							previousWinner.setPreviousWinner(false);
						clients.get(cl).player().setPreviousWinner(true);
						previousWinner = clients.get(cl).player();
					}else if(this.someoneWon && !clients.get(cl).player().isPreviousWinner()){
						clients.get(cl).player().setPreviousWinner(false);
					}

					//Setup winner message
					if(this.someoneWon)
						clients.get(cl).setWinMsg(""+previousWinner.getName()+ " "+previousWinner.getColor());

					//Check to see if client has hit a mine
					if(clients.get(cl).mineHit()){
						this.mineHit(cl);
					}
				}
			}
		}catch(NullPointerException e){
			System.out.println("NullPointer in check clients.");
			e.printStackTrace();
		}
	}

	/**
	 * This method takes the current time and converts 
	 * it into a nice string to pass to the client
	 * @return
	 * 			A formatted time string
	 */
	public String formatTime(){   
		String time = "";
		int min = this.currentTime/60000;
		int sec = this.currentTime/1000 - min*60;
		int mil = this.currentTime%1000;

		if(min < 1){
			time+="0:";
		}else{
			time+=""+min+":";
		}

		if(sec==0){
			time+="00:";
		}else if(sec<10){
			time+="0"+sec+":";
		}else{
			time+=""+sec+":";
		}

		if(mil <= 0){
			time+="000";
		}else if(mil < 10){
			time+="00"+mil;
		}else if(mil<100){
			time+="0"+mil;
		}else{
			time+=""+mil;
		}

		return time.substring(0, time.length()-1);
	}

	/**
	 * Sets up the data for a given client
	 * @param clientN
	 *          The given client to setup data for
	 * @return 
	 *          The formated data to send
	 */
	public String setupData(int clientN){
		String data = "DATA ";
		try{
			data += clients.get(clientN).player().getX() + " " + clients.get(clientN).player().getY() + " " +  clients.get(clientN).player().getColor().toString() 
					+ " " +  (clients.get(clientN).player().isPreviousWinner() ? "1" : "0") + " " +clients.get(clientN).player().flagsLeft() + " ";

			for(int d = 0; d < clients.size(); d++){
				if(!clients.get(d).inSpectatorMode() && d !=clientN)
					data += clients.get(d).player().getX() + " " + clients.get(d).player().getY() + " " + clients.get(d).player().getColor().toString() + " "+  (clients.get(d).player().isPreviousWinner() ? "1" : "0") + " " ;
			}
		}catch (java.lang.NullPointerException e){clients.remove(clientN); e.printStackTrace(); System.out.println();}

		return data;
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
			e.printStackTrace();
		}
	}

	/**
	 * The code to run when a mine is hit
	 * @param ClientNumber
	 * 			The player that hit a mine
	 */
	public void mineHit(int ClientNumber) {

		int explosionX = clients.get(ClientNumber).player().getX();
		int explosionY = clients.get(ClientNumber).player().getY();

		// Sets the radius of the explosion.
		int explosionRadius = 2;

		int explosionXmin = explosionX - explosionRadius;
		int explosionYmin = explosionY - explosionRadius;

		int explosionXmax = explosionX + explosionRadius;
		int explosionYmax = explosionY + explosionRadius;


		for (int k = 0; k < clients.size(); k++) {
			// Checks if players are in this radius
			if (clients.get(k).player().getX() >= explosionXmin && clients.get(k).player().getX() <= explosionXmax
					&& clients.get(k).player().getY() >= explosionYmin && clients.get(k).player().getY() <= explosionYmax) {

				// Fire Mine SFX
				clients.get(k).soundEffect("mineHit");

				// Resets points if you get blown up
				clients.get(k).player().resetPoints();

				// Sets players back to start and resets the mineHit variable in Client.
				// If the player is past the checkpoint, it moves them back to the checkpoint.
				if (clients.get(k).player().getX() > (map.getWidth() / 2)) {
					clients.get(k).player().setY(((int)((Math.random()*100)%(this.map.getHeight()/3)))+this.map.getHeight()/3);
					clients.get(k).player().setX(map.getWidth() / 2);
				}
				else {
					clients.get(k).player().setY(((int)((Math.random()*100)%(this.map.getHeight()-2)))+1);
					clients.get(k).player().setX(1);
				}
				// The y is randomized.
				clients.get(k).setMineHit(false);

			}
		}
	}

	/**
	 * The win condition of the game (reach the finish)
	 * @param clientN
	 * 			The current client to check
	 * @return
	 * 			If the condition was met
	 */
	public boolean checkWinCondition(int clientN){
		if(clients.get(clientN).player().getX()>=this.map.getWidth()-3){
			return true;
		}
		return false;
	}
	
	public class PowerupRemindTask extends TimerTask{

		public void run() {
			if(inRace){
				
			}
			
		}
		
	}
}