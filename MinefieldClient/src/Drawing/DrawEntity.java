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
    public static void draw(Graphics g, Entity e, Entity p, int width, int height, String name){
    	int startX = (e.getX() * 50 + (width / 2 - 25) - p.getX() * 50)-2;
    	int startY = ((height / 2 - 25) - (p.getY() * 50 - e.getY() * 50))-2;

    	
        //sets up drawing
        Graphics2D g2d = (Graphics2D)g;
        //body
        g.setColor(Color.black);
        g.fillOval(startX+4+2, startY+4+2, p.getSize()*scale-8, p.getSize()*scale-8);
        g.setColor(e.getColor());
        g.fillOval(startX+4, startY+4, p.getSize()*scale-8, p.getSize()*scale-8);
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
        
        //draw name
        g.setFont(new Font("Helvetica",Font.BOLD,15));
		g.setColor(e.getColor());
        g2d.drawString(name, startX + 6, startY - 6);
        //Crown
        if(e.isWinner()){
        	//Crown
//	        int[] x = {startX+5,startX+scale*e.getSize()-3,startX+scale*e.getSize()-3,startX+(2*scale*e.getSize()/3)+2,startX+(scale*e.getSize()/2)+1,startX+(1*scale*e.getSize()/3),startX+5,startX+5};
//	        int[] y = {startY+3*scale/2,startY+3*scale/2,startY-scale/2,startY+scale/2,startY-scale/2,startY+scale/2,startY-scale/2,startY+3*scale/2};
//	        Polygon crown = new Polygon(x,y,x.length);
//	        g.setColor(new Color(255,201,14));
//	        g.fillPolygon(crown);
//	        g.setColor(Color.BLACK);
//	        g.drawPolygon(crown);
//	        
//	        //Gem
//	        int[] gX = {startX+scale*e.getSize()/2-5+1,startX+scale*e.getSize()/2+1,startX+scale*e.getSize()/2+5+1,startX+scale*e.getSize()/2+1,startX+scale*e.getSize()/2-5+1};
//	        int[] gY = {startY+scale/2,startY+scale/2+5,startY+scale/2,startY+scale/2-5,startY+scale/2};
//	        Polygon gem = new Polygon(gX,gY,gX.length);
//	        if(e.getColor().equals(Color.yellow)){
//	        	g.setColor(Color.RED);
//	        }else{
//	        	g.setColor(e.getColor());
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
    }
    
    /**
     * This draws the entity when the player is in spectator mode
     * @param g
     * 			The mainpanel graphics to draw with
     * @param e
     * 			The entity to draw
     */
    public static void draw(Graphics g, Entity e, int width, int height, String mapName, int mW, int mH){

    	
    	int startX=width/2-mW*5/2, startY=height/2-mH*5/2;
    	g.setColor(e.getColor());
    	g.fillRect(startX+e.getX()*5, startY+e.getY()*5, 5, 5);
    }
}

