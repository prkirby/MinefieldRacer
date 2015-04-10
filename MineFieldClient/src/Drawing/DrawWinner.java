package Drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * Draws the winner message of the winner
 * @author Joseph Ryan
 *
 */
public abstract class DrawWinner {
	
	private static final int scale = 10;
	
	/**
	 * The method that draws the winner message
	 * @param g
	 * 			The graphics to draw with
	 * @param width
	 * 			The width of the screen
	 * @param height
	 * 			The height of the screen
	 * @param name
	 * 			The name of the winner
	 * @param c
	 * 			The winner's color
	 */
	public static void draw(Graphics g, int width, int height, String name, Color c){
		int len = 12;
		g.setFont(new Font("Courier",Font.BOLD,40));
		g.setColor(Color.black);
		g.drawString(name, width/2-name.length()*len-4, height/5);
		g.setColor(c);
		g.drawString(name, width/2-name.length()*len, height/5);
		
		//Draw player
		int startX = width/2-name.length()*len + name.length()*len*2;
    	int startY = height/5 - 40;
        
        Graphics2D g2d = (Graphics2D)g;
        //body
        g.setColor(Color.black);
        g.fillOval(startX+4+2, startY+4+2, 5*scale-8, 5*scale-8);
        g.setColor(c);
        g.fillOval(startX+4, startY+4, 5*scale-8, 5*scale-8);
        //eyes
        g.setColor(Color.black);
        g.fillOval(startX+scale+2, startY+scale, scale, scale);
        g.fillOval(startX+scale*3-2, startY+scale, scale, scale);
        //Pupils
//        g.setColor(Color.white);
//        g.fillOval(startX+scale+6, startY+scale, 4*scale/9, 4*scale/9);
//        g.fillOval(startX+scale*3+3, startY+scale, 4*scale/9, 4*scale/9);
        //eyebrows
        g2d.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        g.drawLine(startX+scale*2-4, startY+scale-1, startX+scale*2+3, startY+scale+3);
        g.drawLine(startX+scale*3+5, startY+scale-1, startX+scale*3-2, startY+scale+3);
        g2d.setStroke(new BasicStroke(1));
        //Mouth
        g2d.setStroke(new BasicStroke(2));
        g.setColor(Color.black);
        g.drawArc(startX+3*scale/2+1, startY+scale*3, scale*2, scale, 30, 120);
        g2d.setStroke(new BasicStroke(1));
        
        //Sunglasses
	    g.setColor(Color.black);
	    int[] sX = {startX+(scale)+2,startX+(scale*4)-2,startX+(scale*4)-2,startX+(11*scale/3)-1,startX+(10*scale/3)-1,startX+(5*scale/2)+1,startX+(5*scale/2)+1,startX+(5*scale/2)-1,startX+(5*scale/2)-1,startX+(5*scale/3)+1,startX+(4*scale/3)+1,startX+(scale)+2,startX+(scale)+2};
	    int[] sY = {startY+scale+1,startY+scale+1,startY+scale*2+1,startY+scale*2+2+1,startY+scale*2+2+1,startY+scale*2+1,startY+scale+2+1,startY+scale+2+1,startY+scale*2+1,startY+scale*2+2+1,startY+scale*2+2+1,startY+scale*2+1,startY+scale+1};
	    Polygon sunglasses = new Polygon(sX,sY,sX.length);
	    g.fillPolygon(sunglasses);
	    g2d.setStroke(new BasicStroke(3));
	    g.drawLine(startX+6, startY+scale*2, startX+scale+2, startY+scale+4);
	    g.drawLine(startX+scale*5-4, startY+scale*2, startX+scale*4-4, startY+scale+3);
	    g2d.setStroke(new BasicStroke(1));
	}
}
