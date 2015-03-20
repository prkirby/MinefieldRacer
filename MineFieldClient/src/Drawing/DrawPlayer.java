
package Drawing;

import GameMechanics.Entity;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This is the general class that does the drawing for all entities
 * @author Vincent Ader
 */
public abstract class DrawPlayer {
    
	static final int scale = 10;
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
    	int startY = height / 2 - p.getSize()*scale / 2;
    	
    	//body
        g.setColor(p.getColor());
        g.fillOval(startX, startY, p.getSize()*scale, p.getSize()*scale);
        g.setColor(Color.black);
        g.drawOval(startX, startY, p.getSize()*scale, p.getSize()*scale);
        //eyes
        g.fillOval(startX+scale, startY+scale, scale, scale);
        g.fillOval(startX+scale*3, startY+scale, scale, scale);
        //Mouth
        g.drawArc(startX+scale, startY+scale*3, scale*3, scale, -90, 90);
    }
}