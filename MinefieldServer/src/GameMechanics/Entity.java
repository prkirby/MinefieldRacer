package GameMechanics;


/**
 * This class represents the basic entity
 * @author Joseph Ryan
 */
public class Entity {
   
	//variables for all players
    private int x, y;           		//Entity Coordinates
    private final int size = 5; 		//The size of the entity
    private String name = "undefined";
    private boolean godMode = false;

	/**
     * Default Constructor (Not in use)
     */
    public Entity(){}
    
    /**
     * Returns the entity's x coordinate
     * @return 
     *          The x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the entity's x coordinate
     * @param x 
     *          The coordinate to change x to
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the entity's y coordinate
     * @return 
     *          The y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the entity's y coordinate
     * @param y 
     *          The coordinate to change y to
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Returns the size of the entity
     * @return 
     *          The size of the entity
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Returns the name of the entity
     * @return 
     *          The name of the entity
     */
    public String getName(){
    	return name;
    }
    

    /**
     * Sets the entity's name
     * @param y 
     *          The name we want to set
     */
    public void setName(String s){
    	name = s;
    }
    

    /**
     * Sets the entity's god mode
     * @param y 
     *          The value of godmode
     */
    public void setGodMode(Boolean b){
    	godMode = b;
    }
    
    /**
     * Returns if the entity is in godmode
     * @return 
     *          godmode
     */
    public Boolean getGodMode(){
    	return godMode;
    }
}
