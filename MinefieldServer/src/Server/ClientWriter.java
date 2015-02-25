
package Server;

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
    private Map map = new Map(40,25);
    
    
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
            sleep(5);
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
}
