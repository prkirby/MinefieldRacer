
package Main;

import Server.ClientAcceptor;

/**
 * This class initializes the server
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
        ClientAcceptor acceptor = new ClientAcceptor();
        new Thread(acceptor).start();
    } 
}
