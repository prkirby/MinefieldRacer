
package Server;

import java.io.File;
import java.util.ArrayList;

import GameMechanics.Map;
import GameMechanics.MineCreation;

/**
 * This class sets up all of the appropriate data 
 * and sends it to the clients that are currently 
 * within this thread
 * 
 * @author Joseph Ryan
 */
public class ClientWriter implements Runnable {

    private ArrayList<Client> clients = new ArrayList<Client>(); //The clients contained within this thread
    private Map map = new Map(new File("MAPS/bridges.txt"));
    private Map mineLayer;
    private double minePercentage = 0.10;
    
    private final int raceTime = 5 * 60 * 1000; //5 Minutes
    private final int lobbyTime = 30 * 1000;	//30 Seconds
    private int currentTime = lobbyTime;				//Time counter
    private boolean inRace = false;				//Race = true, lobby = false;
    
    private boolean someoneWon = false;			//Temporary variable
    
    
    /**
     * The default constructor (not in use)
     */
    public ClientWriter(){}
    
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
            sleep(5); //5 ms
            switchModes();
        }
    }
    
    /**
     * This determines if a mode switch is necessary. If it is, it does it.
     */
    private void switchModes(){
    	if(this.clients().size()>1)
    		currentTime-=5;//5 ms
    	
    	if(this.currentTime <= 0 && !inRace){
    		System.out.println("STARTING RACE");
    		this.inRace = true;
    		this.currentTime = raceTime;
    		
    		//System.out.println(map.toString());
    		
    		//Start race
    		//Populate mineLayer with mines and numbers
    		mineLayer = MineCreation.createMineLayer(map.map, minePercentage);
    		
    		//System.out.println(mineLayer.toString());
    		
    		//Place all current clients at the starting line
    		for(int c = 0; c < this.clients.size(); c++){
    			if(this.clients.get(c).canRace()){
    				this.clients.get(c).setSpectatorMode(false);
    				this.clients.get(c).player().setX(1);
    				this.clients.get(c).player().setY((int)((Math.random()*100)%this.map.getHeight()-2)+1);
    				
    			}
    		}
    		
    	}else if(this.currentTime <= 0 && inRace){
    		System.out.println("ENTERING LOBBY.");
    		this.inRace = false;
    		this.currentTime = lobbyTime;
    		this.someoneWon = false;
    		
    		//Start Lobby
    		for(int c = 0; c < this.clients.size(); c++){
				this.clients.get(c).setSpectatorMode(true);
    		}
    	}
    	
    	if(inRace && this.currentTime == 10000){
    		System.out.println("RETURNING TO LOBBY IN 10 SECONDS.");
    	}
    	if(!inRace && this.currentTime == 10000){
    		System.out.println("STARTING RACE IN 10 SECONDS.");
    	}
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
			data += clients.get(clientN).player().getX() + " " + clients.get(clientN).player().getY() + " ";

			for(int d = 0; d < clients.size(); d++){
				if(!clients.get(d).inSpectatorMode() && d !=clientN)
					data += clients.get(d).player().getX() + " " + clients.get(d).player().getY() + " ";
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

	public void mineHit(int ClientNumber) {

		int explosionX = clients.get(ClientNumber).player().getX();
		int explosionY = clients.get(ClientNumber).player().getY();

		// Sets the radius of the explosion.
		int explosionRadius = 2;

		int explosionXmin = explosionX - explosionRadius;
		int explosionYmin = explosionY + explosionRadius;

		int explosionXmax = explosionX + explosionRadius;
		int explosionYmax = explosionY - explosionRadius;


		for (int k = 0; k < clients.size(); k++) {
			// Checks if players are in this radius
			if (clients.get(k).player().getX() >= explosionXmin && clients.get(k).player().getX() <= explosionXmax
					&& clients.get(k).player().getY() >= explosionYmin && clients.get(k).player().getY() <= explosionYmax) {

				// Sets players back to start and resets the mineHit variable in Client.
				// If the player is past the checkpoint, it moves them back to the checkpoint.
				if (clients.get(k).player().getX() > (map.getWidth() / 2)) {
					clients.get(k).player().setX(map.getWidth() / 2);
				}
				else {
					clients.get(k).player().setX(1);
				}
				// The y is randomized.
				clients.get(k).player().setY(((int)((Math.random()*100)%(this.map.getHeight()-2)))+1);
				clients.get(k).setMineHit(false);
			}
		}
	}

	public boolean checkWinCondition(int clientN){
		if(clients.get(clientN).player().getX()>=this.map.getWidth()-3){
			return true;
		}
		return false;
	}
}
