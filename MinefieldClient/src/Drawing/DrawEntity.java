package Drawing;

import GameMechanics.Entity;

import java.awt.Graphics;
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
    public static void draw(Graphics g, Entity e, int width, int height, String mapName){
    	File m = new File("MAPS/"+mapName+".txt");
    	System.out.println(mapName);
    	Scanner s = null;
    	
    	try {
			s = new Scanner(m);
		} catch (FileNotFoundException ee) {}
    	
    	int wid = s.nextInt();
    	int hei = s.nextInt();
    	
    	int startX=width/2-wid*5/2, startY=height/2-hei*5/2;
    	g.setColor(e.getColor());
    	g.fillRect(startX+e.getX()*5, startY+e.getY()*5, 5, 5);
    	
    	s.close(); 
    }
}

