package Drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import GameMechanics.Entity;

/**
 * This class draws the mineshield powerup
 * @author 
 *
 */
public class DrawMineShield {
	static final int scale = 10;
	
	/**
	 * The drawing method that draws the mineShield
	 * @param g
	 * 			The graphics to draw with
	 * @param p
	 * 			The entity to draw the shield on
	 * @param width
	 * 			The width of the screen
	 * @param height
	 * 			The height of the screen
	 */
	public static void draw(Graphics g, Entity p, int width, int height){
		int startX = (width / 2 - p.getSize()*scale / 2)-2;
    	int startY = (height / 2 - p.getSize()*scale / 2)-2;
    	
    	 Graphics2D g2d = (Graphics2D)g;
     	//body
         g.setColor(Color.magenta);
         g.drawOval(startX+4, startY+4,  p.getSize()*scale-7,  p.getSize()*scale-7);
	}
}
