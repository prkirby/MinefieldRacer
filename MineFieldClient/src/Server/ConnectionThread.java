package Server;

import GameMechanics.Entity;

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
    private final String ipAddress = "141.219.211.116";
    private Socket socket = null;                                       //The client's socket
    private BufferedReader input = null;                                //Input from server
    private PrintWriter output = null;                                  //Output to server
    
    private InputThread in;                                             //The client input thread
    
    //Timeout Timer Variables
    private Timer t = new Timer();                                      //The timer for connection timeout
    private boolean connected = false;                                  //Determines if a connection was made
    private boolean tryC = true;                                        //Shows an attempt to connect
    
    /**
     * The default constructor: Attempts a connection 
     * to the server. If connection fails, timeout and 
     * close occurs. If connection succeeds threads are 
     * set up
     */
    public ConnectionThread(){
        
        while(true){
            try{
                if(tryC){
                    t.schedule(new RemindTask(), 10000);
                    System.out.println("Attempting to connect...");
                    socket = new Socket(ipAddress,serverPort); //This is the connection to the server
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = new PrintWriter(socket.getOutputStream(), true);
                    
                    connected = true;
                    in = new InputThread(input, output);
                    new Thread(in).start();
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
                    }else
                        this.close();
                }
                
            } catch (IOException ex) {
                System.out.println("Error getting output from server (IOException): "+inn);
                ex.printStackTrace();
                this.close();
            } catch (java.lang.NullPointerException e){
                System.out.println("Error getting output from server (NULL): "+inn);
                //e.printStackTrace(); //pretend this doesn't happen
                this.close();
            } catch (java.util.NoSuchElementException e){
                //e.printStackTrace(); //pretend this doesn't happen
            } catch (Exception e){
                System.out.println("Error getting output from server ("+e.toString()+"): "+inn);
                //e.printStackTrace(); //pretend this doesn't happen
                this.close();
            }
            sleep(4);
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
        int x, y;
        x = scan.nextInt();
        y = scan.nextInt();
        data.add(new Entity(x, y));
        while(scan.hasNext()){
            x = scan.nextInt();
            y = scan.nextInt();
            data.add(new Entity(x, y));
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
