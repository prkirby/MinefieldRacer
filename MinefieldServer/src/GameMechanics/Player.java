
package GameMechanics;

import java.util.Timer;
import java.util.TimerTask;

import GameMechanics.Invisibility.makeVisible;

/**
 * The
 * @author Joseph Ryan
 */
public class Player extends Entity{

	// Default viewPortSize
	private int viewPortSize = 1;				//The radius of view
	private String name = "NO_NAME";			//The name of the player
	private String color = "yellow"; 			//The color of the entity
	private final int numFlags = 10;			//The number of flags
	private Flag[] flags = new Flag[numFlags]; 	//Flags that the player has
	private int flagIndex = 0;					//Index of the flag they are on
	private int flagStreak = 0;
	
	private Powerup p = new NoPowerup(10);		//Should default to nopowerup, change for testing purposes
	private int points = 0;
	private int highestStreak = 0;
	private boolean amIVisible = true;
	private boolean amIShielded = false;
	private boolean amIAGod = false;
	private boolean nukeArmed = false;
	private boolean drawNuke = false;


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
	 * Returns the flag array for the player
	 * @return
	 */
	public Flag[] getFlags() {
		return flags;
	}

	/**
	 * Initializes the flags at the beginning of a race
	 */
	public void resetFlags(){
		for (int i = 0; i < flags.length; i++){
			flags[i] = new Flag();
		}
		flagIndex = 0;
	}

	/**
	 * This method places a flag at a given x,y location
	 * 
	 * @param x  Locations for the flag
	 * @param y
	 * 
	 * @return Whether or not a flag was placed
	 */
	public boolean setFlag(int x, int y) {

		//See if there are flags left
		if (flagIndex > numFlags-1) {
			return false;
		}

		//See if a flag already exists at this location
		for (int i = 0; i < flagIndex; i++) {
			if (flags[i].getX() == x && 
					flags[i].getY() == y &&
					flags[i].getIsPlaced()) {
				return false;
			}
		}

		//Otherwise, place a flag at the location
		flags[flagIndex].setX(x);
		flags[flagIndex].setY(y);
		flags[flagIndex].setIsPlaced(true);
		flagIndex++;
		return true;
	}

	/**
	 * Check flag array to see if one exists there
	 * 
	 * @param x
	 * @param y
	 * @return Whether or not a flag is at this location
	 */
	public boolean checkForFlag(int x, int y) {
		//See if a flag exists at this location
		for (int i = 0; i < flagIndex; i++) {
			if (flags[i].getX() == x && 
					flags[i].getY() == y &&
					flags[i].getIsPlaced()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the remaining flags that the player has
	 * @return
	 * 			The number of flags left
	 */
	public int flagsLeft(){
		return flags.length-flagIndex;
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


	/**
	 *Gets player's points
	 * @return
	 * 		points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Sets the player's winner points
	 * @param newPoints
	 * 			sets points
	 */
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

	//adds points for the player
	public void addAPoint() {
		points += 1;
	}
	/**
	 * Sets the player's powerup
	 * @param power
	 * 		power is the new powerup
	 */
	public void setPowerup(Powerup power){
		p = power;
	}
	
	//uses the pouwerup's ability
	public void usePowerup(){
		p.useAbility(this);
	}
	
	/**
	 * Gets the player's powerup
	 * @return
	 *  p, the player's powerup
	 */
	public Powerup getPowerup(){
		return p;
	}
	
	/**
	 * Gets the player's visibility
	 * @return
	 * 	the visibility of the player to others
	 */
	public boolean getVisibility(){
		return amIVisible;
	}
	//swaps visibility
	public void switchVisibility(){
		if(amIVisible){
			amIVisible = false;
		}
		else{
			amIVisible = true;
		}
			
	}
	
	/**
	 * Gets if the player is shielded
	 * @return
	 * 	amIshielded
	 */
	public boolean getAmIShielded(){
		return amIShielded;
	}
	//switches the player's  shielded variable
	public void switchShield(){
		if(amIShielded){
			amIShielded = false;
		}
		else{
			amIShielded = true;
		}
	}
	/**
	 * Gets the player'sgod Status
	 * @return
	 * If the player is a god or not
	 */
	public boolean godStatus(){
		return amIAGod;
	}
	//sets the Player to being a god
	public void setGodStatus(){
		if(!amIAGod){
			amIAGod = true;//one time transformation, can't go back
		}
	}
	
	//Gets rid of the player being a god
	public void makeMortal(){
		amIAGod = false; //reversed at end of match
	}
	
	//makes the nuke true so that we can use it
	public void armNuke(){
		nukeArmed = true;
	}
	
	//returns the value of if we have a used nuke or not
	public boolean getNukeStatus(){
		return nukeArmed;
	}
	
	//Probably could merge with arming nuke, too lazy
	public void defuseNuke(){
		nukeArmed = false;
	}
	
	//method used for drawing the nuke message
	public boolean getDrawNuke(){
		return drawNuke;
	}
	
	//draws the nuke message for 4 seconds
	public void drawNuke(){
		Timer t = new Timer();
		drawNuke = true;
		t.schedule(new drawNukeNotification(), 4000);//after duration seconds
	}
	

	//gets rid of the message after 4 seconds
	class drawNukeNotification extends TimerTask{
		@Override
		public void run() {
			drawNuke = false;
		}
		
	}
	
	//returns the flag streak
	public int getFlagStreak() {
		return flagStreak;
	}
	
	//increments the flag streak
	public void incrementFlagStreak() {
		flagStreak += 1;
	}
	
	//resets the flag streak
	public void resetFlagStreak() {
		flagStreak = 0;
	}
}
