
package GameMechanics;

/**
 * The
 * @author Joseph Ryan
 */
public class Player extends Entity{
    
	// Default viewPortSize
	private int viewPortSize = 1;			//The radius of view
	private String name = "NO_NAME";		//The name of the player
    private String color = "yellow"; 		//The color of the entity
    private int points = 0;
    private int highestStreak = 0;
    
    //Crowning a player
    private boolean previousWinner = false;	//Denotes if this player won the last race

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
     * Sets up the player with initial x, y coordinates, 
     * their name and selected color
     * @param name
     * 			The name of the player
     * @param color
     * 			The color of the player
     * @param x
     *          The x coordinate
     * @param y 
     *          The y coordinate
     */
    public Player(String name, String color, int x, int y){
    	this.name = name;
    	this.color = color;
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
     * Returns the given name of the player
     * @return
     * 			The player's name
     */
    public String getName() {
		return name;
	}

    /**
     * Sets the name of the player
     * @param name
     * 			The name to set to
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Returns the given color of the player
     * @return
     * 			The player's color
     */
    public String getColor() {
		return color;
	}

    /**
     * Sets the color of the player
     * @param color
     * 			The color to set to
     */
	public void setColor(String color) {
		this.color = color;
	}
	
	/**
	 * Returns if this player was the last winner
	 * @return
	 * 			If the player was the last winner
	 */
    public boolean isPreviousWinner() {
		return previousWinner;
	}

    /**
     * Sets the player's winner Status
     * @param previousWinner
     * 			If the player is a winner
     */
	public void setPreviousWinner(boolean previousWinner) {
		this.previousWinner = previousWinner;
	}
    
    //*******************************************************
    //Actions
    //*******************************************************
    
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
    
    public int getPoints() {
    	return points;
    }
    
    public void setPoints(int newPoints) {
    	points = newPoints;
    }
    
    // Resets points and updates highestStreak
    public void resetPoints() {
    	if (points > highestStreak) {
    		highestStreak = points;
    	}
    	points = 0;
    }
    
    public void addAPoint() {
    	points += 1;
    }
}
