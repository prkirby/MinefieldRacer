
package GameMechanics;

/**
 * The
 * @author Joseph Ryan
 */
public class Player extends Entity{
    
	// Default viewPortSize
	private int viewPortSize = 1;
	
    /**
     * Default Constructor (Not in use)
     */
    public Player(){}
    
    /**
     * Sets up the player with initial x, y coordinates
     * @param x
     *          The x coordinate
     * @param y 
     *          The y coordinate
     */
    public Player(int x, int y){
        super.setX(x);
        super.setY(y);
    }
    
    /**
     * Getter for viewPortSize
     * @return
     * 		Player's Viewport size
     */
    public int getViewPort() {
    	return viewPortSize;
    }
    
    /**
     * Setter for viewPortSize
     * @param newViewPort
     * 		Size of new view port (in spaces away from player)
     */
    public void setViewPort(int newViewPort) {
    	viewPortSize = newViewPort;
    }
    
    /**
     * Moves the player up
     * @param amt 
     *          The amount to move the player
     */
    public void moveUp(int amt){
        super.setY(super.getY()-amt);
    }
    
    /**
     * Moves the player down
     * @param amt 
     *          The amount to move the player
     */
    public void moveDown(int amt){
        super.setY(super.getY()+amt);
    }
    
    /**
     * Moves the player left
     * @param amt 
     *          The amount to move the player
     */
    public void moveLeft(int amt){
        super.setX(super.getX()-amt);
    }
    
    /**
     * Moves the player right
     * @param amt 
     *          The amount to move the player
     */
    public void moveRight(int amt){
        super.setX(super.getX()+amt);
    }
}
