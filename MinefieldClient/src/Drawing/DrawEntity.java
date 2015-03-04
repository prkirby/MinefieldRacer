package Drawing;

import GameMechanics.Entity;
import java.awt.Graphics;

/**
 * This is the general class that does the drawing for all entities
 * @author Joseph Ryan
 */
public abstract class DrawEntity {
    
	static int scale = 10;
    /**
     * Draws the given entity
     * @param g
     *          The mainpanel graphics to draw with
     * @param e 
     *          The entity to draw
     */
    public static void draw(Graphics g, Entity e, Entity p, int width, int height){
    	int startX = e.getX() * 50 + (width / 2 - 25) - p.getX() * 50;
    	int startY = (height / 2 - 25) - (p.getY() * 50 - e.getY() * 50);
        g.setColor(e.getColor());
        g.fillRect(startX, startY, e.getSize()*10, e.getSize()*10);
    }
    
    /**
     * This draws the entity when the player is in spectator mode
     * @param g
     * 			The mainpanel graphics to draw with
     * @param e
     * 			The entity to draw
     */
    public static void draw(Graphics g, Entity e){
    	g.setColor(e.getColor());
    	g.fillRect(e.getX()*5, e.getY()*5, 5, 5);
    }
}

