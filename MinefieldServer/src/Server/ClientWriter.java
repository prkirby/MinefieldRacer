
package Server;

import java.io.File;
import java.util.ArrayList;

import GameMechanics.Map;

/**
 * This class sets up all of the appropriate data 
 * and sends it to the clients that are currently 
 * within this thread
 * 
 * @author Joseph Ryan
 */
public class ClientWriter implements Runnable{

    private ArrayList<Client> clients = new ArrayList<Client>(); //The clients contained within this thread
    private Map map = new Map(new File("MAPS\\test1.txt"));
    
    private final int raceTime = 5 * 60 * 1000; //5 Minutes
    private final int lobbyTime = 60 * 1000;	//1 Minute
    private int currentTime = 0;				//Time counter
    private boolean inRace = false;				//Race = true, lobby = false;
    
    private boolean haveWinner = false;			//Temporary variable
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
        currentTime+=5;//5 ms
    	if(this.currentTime >= this.lobbyTime && !inRace){
    		this.inRace = true;
    		this.currentTime = 0;
    		
    		//Start race
    		//Place all current clients at the starting line
    		for(int c = 0; c < this.clients.size(); c++){
    			if(this.clients.get(c).canRace()){
    				this.clients.get(c).player().setX(1);
    				this.clients.get(c).player().setY((int)((Math.random()*100)%this.map.getHeight()));
    			}
    		}
    		
    	}else if(this.currentTime >= this.raceTime && inRace){
    		this.inRace = false;
    		this.currentTime = 0;
    		this.haveWinner = false;
    		this.someoneWon = false;
    		
    		//Start Lobby
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
                	if(!this.inRace)
                		clients.get(cl).setCanRace(true);
                	
                	//If a client met win Condition
                	if(!this.haveWinner && this.someoneWon){
                		this.currentTime = this.raceTime-10*1000; //Ten seconds left
                		this.haveWinner = true;
                	}
                }
            }
        }catch(NullPointerException e){
            System.out.println("NullPointer in check clients.");
        }
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
                data += clients.get(d).player().getX() + " " + clients.get(d).player().getY() + " ";
            }
        }catch (java.lang.NullPointerException e){clients.remove(clientN); System.out.println();}
        
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
        }
    }
    
    public void mineHit() {
    	// Does mine things
    }
}
