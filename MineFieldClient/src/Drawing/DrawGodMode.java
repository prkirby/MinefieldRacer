package Drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import GameMechanics.Entity;

/**
 * This class draws the godmode buff
 * @author 
 *
 */
public class DrawGodMode {
	static final int scale = 10;
	
	/**
	 * Draws the god mode buff on the player
	 * @param g
	 * 			The graphics to draw with
	 * @param p
	 * 			The entity to draw with
	 * @param width
	 * 			The width of the screen
	 * @param height
	 * 			The height of the screen
	 */
	public static void draw(Graphics g, Entity p, int width, int height, String name){
		int startX = (width / 2 - p.getSize()*scale / 2)-2;
		int startY = (height / 2 - p.getSize()*scale / 2)-2;

		Graphics2D g2d = (Graphics2D)g;
		//body
		g.setColor(Color.black);
		g.fillOval(startX+4+2, startY+4+2, p.getSize()*scale-8, p.getSize()*scale-8);
		//eyes
		g.setColor(Color.yellow);
		g.fillOval(startX+scale+2, startY+scale, scale, scale);
		g.fillOval(startX+scale*3-2, startY+scale, scale, scale);
		//Mouth
		g2d.setStroke(new BasicStroke(2));
		g.setColor(Color.yellow);
		g.drawArc(startX+scale+6, startY+scale*2+5, scale*2, scale, 200, 140); //Smile
		g2d.setStroke(new BasicStroke(1));
		
	     //draw name
        g.setFont(new Font("Helvetica",Font.BOLD,15));
		g.setColor(p.getColor());
        g2d.drawString(name, startX + 6, startY - 6);
	}
}
