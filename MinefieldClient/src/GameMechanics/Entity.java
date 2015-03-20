
package GameMechanics;

import java.awt.Color;

/**
 * This is an easy storage for all of the visible player's data.
 * @author Joseph Ryan
 */
public class Entity {
    
    private int x, y;           //Coordinate locations in relation to the grid
    private final int size = 5; //The size of the entity
    private Color color;        //The color of the entity (maybe used later)
    
    /**
     * Default Constructor (Not in use)
     */
    public Entity(){}
    
    /**
     * Sets up the Entity with a default color and at a specific spot
     * @param x
     *          The x coordinate
     * @param y 
     *          The y coordinate
     */
    public Entity(int x, int y){
        this.x = x;
        this.y = y;
        this.color = Color.yellow;
    }
    
    /**
     * Sets up the Entity with a color and at a specific spot
     * @param x
     *          The x coordinate
     * @param y 
     *          The y coordinate
     * @param c 
     *          The color
     */
    public Entity(int x, int y, Color c){
        this.x = x;
        this.y = y;
        this.color = c;
    }
    
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
     * Returns the entity's color
     * @return 
     *          The color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the entity's color
     * @param color 
     *          The color to change to
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the size of the entity
     * @return 
     *          The size of the entity
     */
    public int getSize() {
        return size;
    }
}
