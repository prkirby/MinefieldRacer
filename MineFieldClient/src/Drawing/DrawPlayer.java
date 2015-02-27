
package Drawing;

import GameMechanics.Entity;
import java.awt.Graphics;

/**
 * This is the general class that does the drawing for all entities
 * @author Vincent Ader
 */
public abstract class DrawPlayer {
    
	static int scale = 10;
	// Recomment later
    /**
     * Draws the given entity
     * @param g
     *          The mainpanel graphics to draw with
     * @param e 
     *          The entity to draw
     */
    public static void draw(Graphics g, Entity p, int width, int height){
    	int startX = width / 2 - p.getSize()*scale / 2;
    	int startY = height / 2 - p.getSize()*scale / 2;;
        g.setColor(p.getColor());
        g.fillRect(startX, startY, p.getSize()*10, p.getSize()*10);
    }
}