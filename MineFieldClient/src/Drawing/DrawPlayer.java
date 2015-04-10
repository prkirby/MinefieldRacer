
package Drawing;

import GameMechanics.Entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the general class that does the drawing for all entities
 * @author Vincent Ader
 * @author Joseph Ryan
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

    public static void draw(Graphics g, Entity p, int width, int height, String name){
    	int startX = (width / 2 - p.getSize()*scale / 2)-2;
    	int startY = (height / 2 - p.getSize()*scale / 2)-2;

        Graphics2D g2d = (Graphics2D)g;
    	//body
        g.setColor(Color.black);
        g.fillOval(startX+4+2, startY+4+2, p.getSize()*scale-8, p.getSize()*scale-8);
        g.setColor(p.getColor());
        g.fillOval(startX+4, startY+4, p.getSize()*scale-8, p.getSize()*scale-8);
        //eyes
        g.setColor(Color.black);
        g.fillOval(startX+scale+2, startY+scale, scale, scale);
        g.fillOval(startX+scale*3-2, startY+scale, scale, scale);
        //Pupils
//        g.setColor(Color.white);
//        g.fillOval(startX+scale+6, startY+scale, 4*scale/9, 4*scale/9);
//        g.fillOval(startX+scale*3+3, startY+scale, 4*scale/9, 4*scale/9);
        //Mouth
        g2d.setStroke(new BasicStroke(2));
        g.setColor(Color.black);
        //g.drawArc(startX+scale+4, startY+scale*2+4, scale*2, scale, -90, 90); //Smirk
        g.drawArc(startX+scale+6, startY+scale*2+5, scale*2, scale, 200, 140); //Smile
        g2d.setStroke(new BasicStroke(1));
        
        //draw name
        g.setFont(new Font("Helvetica",Font.BOLD,15));
		g.setColor(p.getColor());
        g2d.drawString(name, startX + 6, startY - 6);
        
        
      //Crown
        if(p.isWinner()){
        	//Crown
//	        int[] x = {startX+5,startX+scale*p.getSize()-3,startX+scale*p.getSize()-3,startX+(2*scale*p.getSize()/3)+2,startX+(scale*p.getSize()/2)+1,startX+(1*scale*p.getSize()/3),startX+5,startX+5};
//	        int[] y = {startY+3*scale/2,startY+3*scale/2,startY-scale/2,startY+scale/2,startY-scale/2,startY+scale/2,startY-scale/2,startY+3*scale/2};
//	        Polygon crown = new Polygon(x,y,x.length);
//	        g.setColor(new Color(255,201,14));
//	        g.fillPolygon(crown);
//	        g.setColor(Color.BLACK);
//	        g.drawPolygon(crown);
//	        
//	        //Gem
//	        int[] gX = {startX+scale*p.getSize()/2-5+1,startX+scale*p.getSize()/2+1,startX+scale*p.getSize()/2+5+1,startX+scale*p.getSize()/2+1,startX+scale*p.getSize()/2-5+1};
//	        int[] gY = {startY+scale/2,startY+scale/2+5,startY+scale/2,startY+scale/2-5,startY+scale/2};
//	        Polygon gem = new Polygon(gX,gY,gX.length);
//	        if(p.getColor().equals(Color.yellow)){
//	        	g.setColor(Color.RED);
//	        }else{
//	        	g.setColor(p.getColor());
//	        }
//	        g.fillPolygon(gem);
	        
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
        
        //draw name
//        String name = "";
//        try {
//			Scanner fileScanner = new Scanner(new File("Username"));
//			while(fileScanner.hasNext()){
//				name = name + fileScanner.next() + " ";
//			}
//		} catch (FileNotFoundException e) {
//			System.out.println("failed");
//			name = "couldn'tread";
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        g.setFont(new Font("Helvetica",Font.BOLD,15));
//		g.setColor(p.getColor());
//        g2d.drawString(name, startX, startY);
    }
}