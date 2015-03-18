
package Drawing;

import GameMechanics.Entity;

import java.awt.Color;
import java.awt.Font;
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
    public static void draw(Graphics g, Entity p, int width, int height, String[][] map){
    	int startX = width / 2 - p.getSize()*scale / 2;
    	int startY = height / 2 - p.getSize()*scale / 2;;
        g.setColor(p.getColor());
        g.fillRect(startX, startY, p.getSize()*10, p.getSize()*10);
        try {
        	if (Integer.parseInt(map[5][5]) > 0) {
        	g.setColor(Color.WHITE);
        	g.setFont(new Font("Arial",Font.BOLD,20));
			g.drawString(map[5][5], startX + 20, startY + 30);
        	}
        }
        catch (NumberFormatException e) {
        	//This is an excepted error, no need to handle
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
}