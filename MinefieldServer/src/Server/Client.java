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
    
    //Data to send to client
    private String data = "";
    
    //Client data
    private Player player;
    
    //Map data
    private Map map;
    
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
     * This updates the map for collision detection
     */
    public void updateMap(Map m){
    	this.map = m;
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
                        else                    keys[k] = false;
                    }
                    
                    //Movement
                    if(keys[0] && map.validLocation(player.getX()-1, player.getY())) player.moveLeft(1);
                    if(keys[1] && map.validLocation(player.getX(), player.getY()-1)) player.moveUp(1);
                    if(keys[2] && map.validLocation(player.getX()+1, player.getY())) player.moveRight(1);
                    if(keys[3] && map.validLocation(player.getX(), player.getY()+1)) player.moveDown(1);
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
                output.println(sendMap());
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
       public String sendMap(){
    	   String ret = "MAP ";
    	   for(int x=player.getX()-5; x <= player.getX()+5; x++){
    		   for(int y=player.getY()-5; y <= player.getY()+5; y++){
    			   if(x < 0 || x > map.map.length || y < 0 || y > map.map[0].length)
    				   ret+="0 ";
    			   else
    				   ret+=map.getMap()[x][y]+" ";
        	   }
    	   }
    	   
    	   return ret;
       }
        
    }
}
