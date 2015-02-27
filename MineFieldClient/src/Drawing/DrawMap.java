package Drawing;

import java.awt.Color;
import java.awt.Graphics;

public class DrawMap {
	
	private static final int scale = 10;
	
	/**
     * Draws the given map
     * @param g
     *          The mainpanel graphics to draw with
     * @param e 
     *          The entity to draw
     */
    public static void draw(Graphics g, String[][] map){
    	for(int x = 0; x < 11; x++){
    		for(int y =0; y < 11; y++){
    			try{
    			if(map[x][y].equals("W")){
    				g.setColor(Color.black);
    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
    			}else if(map[x][y].equals("0")){
    				g.setColor(new Color(154,154,154));
    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
    			}
    			else if(map[x][y].equals("n")){}
    			else{
    				g.drawString(map[x][y],x*scale*5+25, y*scale*5+25);
    			}
    			}catch(NullPointerException e){
    				//System.out.println("POINT: "+x+", "+y);
    			}
    		}
    	}
    }
}
