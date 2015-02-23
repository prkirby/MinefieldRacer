package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class accepts clients
 * @author Xazaviar
 */
public class ClientAcceptor implements Runnable{

    private int serverPort = 1111;              //The port of the server
    private ServerSocket serverSocket = null;   //The server's socket
    private boolean isStopped = false;          //Determines if the server is done
    private InetAddress hostAddress = null;     //The IP of the computer running the server
    private Thread runningThread = null;        //The main running thread
    private ClientWriter cl;                    //The Client writer thread
    
    /**
     * The default constructor (not in use)
     */
    public ClientAcceptor(){
        //Attempt to get the host address
        try{
            hostAddress = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
            System.out.println("Could not get the host address.");
            return;
        }
        
        //Initialize the clientWriter
        cl = new ClientWriter();
        new Thread(cl).start();
        
        //Print the host address
        System.out.println("Server host address is: "+hostAddress);
    }   

    /**
     * The Thread: Opens the server and accepts clients. When 
     * clients join they get placed into a clientWriter thread.
     */
    public void run() {
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        
        while(!this.isStopped){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            
            cl.clients().add(new Client(clientSocket));
            new Thread(cl.clients().get(cl.clients().size()-1)).start();
            
            //OUTPUT (when someone connets)
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            System.out.println(""+clientSocket+" has connected at: "+dateFormat.format(date));
            
            sleep(5);
        }
    }
    
    /**
     * Returns if the server has been stopped
     * @return 
     *          If the server has been stopped
     */
    private synchronized boolean isStopped() {
        return this.isStopped;
    }
    
    /**
     * Stops the server, and closes it nicely
     */
    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
            System.out.println("Server has been stopped");
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
    
    /**
     * Opens up the server
     */
    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort,0,hostAddress);
            System.out.println("Server has been started: "+this.serverSocket);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port "+serverPort, e);
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
}
