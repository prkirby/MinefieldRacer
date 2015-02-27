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
    	for(int x = 0; x < map.length; x++){
    		for(int y =0; y < map[0].length; y++){
    			if(map[x][y].equals("w")){
    				g.setColor(Color.black);
    				g.fillRect(x*scale, y*scale, 5*scale, 5*scale);
    			}else if(map[x][y].equals("0")){
    				g.setColor(new Color(154,154,154));
    				g.fillRect(x*scale, y*scale, 5*scale, 5*scale);
    			}
    			else{
    				g.drawString(map[x][y],x*scale+25, y*scale+25);
    			}
    		}
    	}
    }
}
