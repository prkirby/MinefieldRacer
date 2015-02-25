
package Drawing;

import GameMechanics.Entity;
import java.awt.Graphics;

/**
 * This is the general class that does the drawing for all entities
 * @author Joseph Ryan
 */
public abstract class DrawEntity {
    
    /**
     * Draws the given entity
     * @param g
     *          The mainpanel graphics to draw with
     * @param e 
     *          The entity to draw
     */
    public static void draw(Graphics g, Entity e){
    	int scale = 10;
        g.setColor(e.getColor());
        g.fillRect(e.getX()*e.getSize()*scale, e.getY()*e.getSize()*scale, e.getSize()*scale, e.getSize()*scale);
    }
}
