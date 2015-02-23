
package Main;

import Server.ConnectionThread;

/**
 * This class initializes the ConnectionThread so that 
 * the client can connect to the server, holds the main
 * 
 * @author Joseph Ryan
 */
public class Main {

    /**
     * The main of the program
     * @param args 
     *          Holds the arguments for the program (not in use)
     */
    public static void main(String[] args){
        ConnectionThread connect = new ConnectionThread();
        new Thread(connect).start();
    } 
}
