package Server;

import Drawing.MainGUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This is the client input to server thread. It 
 * takes whatever key presses are being pressed 
 * and sends them in a binary string to the server
 * 
 * @author Joseph Ryan
 */
public class InputThread implements Runnable{

    private BufferedReader input = null; //Input from server
    private PrintWriter output = null;   //Output to server
    private MainGUI mainGUI;             //The Client visuals
    
    /**
     * The primary constructor. This sets up the read and write
     * @param input
     *          The input reader from the server
     * @param output 
     *          The output writer to the server
     */
    public InputThread(BufferedReader input, PrintWriter output){
        this.input = input;
        this.output = output;        
        mainGUI = new MainGUI();
    }

    /**
     * Returns the MainGUI, used to setup data
     * @return 
     *          The drawing GUI
     */
    public MainGUI mainGUI(){
        return this.mainGUI;
    }
    
    /**
     * The running thread: sends client key presses to the server
     */
    public void run() {
        while(true){
            try {
                output.println(setupData(mainGUI.keyPresses())); //To server
                mainGUI.resetKeyPresses();
            } catch (Exception ex) {
                System.out.println("Error getting output to the server (InputThread).");
                break;
            }
            sleep(30);
        }
        
        close(); //If an error occurs, close the connection
    }
    
    /**
     * Sets up the data to send to the server in a convenient string
     * @param keys
     *          The key press array
     * @return 
     *          A formatted string to send to the server. 
     *          If a key is being pressed set to 1 else 0.
     */
    public String setupData(boolean[] keys){
        String ret = "KEYS ";
        for(int i = 0; i< keys.length; i++){
            if(keys[i]) ret += "1 ";
            else        ret += "0 ";
        }
        return ret;
    }
    
    /**
     * Closes the thread when needed
     */
    public void close(){
        try{
            this.input.close();
        }catch(IOException e){
            throw new RuntimeException("Error closing.", e);
        }
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
}
