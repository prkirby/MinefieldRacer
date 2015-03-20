package Drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    			g.setColor(new Color(193,193,193));
    			try{
	    			if(map[x][y].equals("W")){
	    				g.setColor(Color.black);
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    			}else if(map[x][y].equals("c")){
	    				g.setColor(new Color(193,193,193));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    				g.setColor(new Color(254,254,254));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, scale/2);
	    				g.fillRect(x*scale*5, y*scale*5, scale/2, 5*scale);
	    				g.setColor(new Color(125,125,125));
	    				g.fillRect(x*scale*5, y*scale*5+scale*5-scale/2, scale*5, scale/2);
	    				g.fillRect(x*scale*5+scale*5-scale/2, y*scale*5, scale/2, scale*5);
	    			}else if(map[x][y].equals("r")){
	    				g.setColor(new Color(255,0,0));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    			}else if(map[x][y].equals("bf")){
	    				g.setColor(new Color(30,30,30));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    			}else if(map[x][y].equals("wf")){
	    				g.setColor(new Color(255,255,255));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    			}else if(map[x][y].equals("y")){
	    				g.setColor(new Color(222,222,0));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    			}else if(map[x][y].equals("m")){
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    				g.setColor(Color.black);
	    				g.fillOval(x*scale*5+10*scale/12, y*scale*5+10*scale/12, 10*scale/3, 10*scale/3);
	    				g.fillRect(x*scale*5+10*scale/12-5, y*scale*5+5*scale/2-2, 5*scale-5, scale/3);
	    				g.fillRect(x*scale*5+5*scale/2-2, y*scale*5+10*scale/12-5, scale/3, 5*scale-5);
	    				Graphics2D g2d = (Graphics2D)g;
	    				g2d.setStroke(new BasicStroke(scale/3));
	    				g.drawLine(x*scale*5+7, y*scale*5+7, x*scale*5+scale*5-7, y*scale*5+scale*5-7);
	    				g.setColor(Color.white);
	    				g.fillOval(x*scale*5+10*scale/6, y*scale*5+10*scale/6, 3*scale/4, 3*scale/4);
	    			}
	    			else if(map[x][y].equals("n")){}
	    			else{
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    			}
    			}catch(NullPointerException e){
    				//System.out.println("POINT: "+x+", "+y);
    			}
    		}
    	}
    }
    
    /**
     * Draws the numbers
     * @param g
     * 			The graphics	
     * @param map
     * 			The map to reference
     */
    public static void drawNumbers(Graphics g, String[][] map){
    	for(int x = 0; x < 11; x++){
    		for(int y = 0; y < 11; y++){
    			g.setColor(new Color(193,193,193));
		    	try{
		    		if(map[x][y].matches(".*\\d.*")){
		    			if(!map[x][y].equals("0")){
		    				g.setFont(new Font("Courier",Font.BOLD,40));
		    				g.setColor(Color.black);
		    				g.drawString(map[x][y],x*scale*5+14, y*scale*5+37);

							if(map[x][y].equals("1")){
								g.setColor(new Color(0,0,255));
							}else if(map[x][y].equals("2")){
								g.setColor(new Color(0,130,0));
							}else if(map[x][y].equals("3")){
								g.setColor(new Color(255,0,0));
							}else if(map[x][y].equals("4")){
								g.setColor(new Color(0,0,132));
							}else if(map[x][y].equals("5")){
								g.setColor(new Color(132,0,0));
							}else if(map[x][y].equals("6")){
								g.setColor(new Color(0,130,132));
							}else if(map[x][y].equals("7")){
								g.setColor(new Color(132,0,132));
							}else if(map[x][y].equals("8")){
								g.setColor(Color.black);
							}
							g.drawString(map[x][y],x*scale*5+12, y*scale*5+35);
		    			}
		    		}
			    }catch(NullPointerException e){
					//System.out.println("POINT: "+x+", "+y);
				}
    		}
    	}
    	
    }
    
    
    /**
     * This is for drawing the map when in spectator mode
     * @param g
     * 			The graphics to draw
     * @param mapName
     * 			The mapName to draw
     */
    public static void draw(Graphics g, String mapName, int width, int height){
    	File m = new File("MAPS/"+mapName+".txt");
    	Scanner s = null;
    	
    	try {
			s = new Scanner(m);
		} catch (FileNotFoundException e) {}
    	
    	int wid = s.nextInt();
    	int hei = s.nextInt();
    	
    	int startX=width/2-wid*5/2, startY=height/2-hei*5/2;
    	
    	for(int y = 0; y < hei; y++){
    		for(int x = 0; x < wid; x++){
    			String block = s.next();
    			if(block.equals("W")){
    				g.setColor(Color.black);
    				g.fillRect(startX+x*5, startY+y*5, 5, 5);
    			}else if(block.equals("y")){
    				g.setColor(new Color(222,222,0));
    				g.fillRect(startX+x*5, startY+y*5, 5, 5);
    			}else if(block.equals("c")){
    				g.setColor(new Color(154,154,154));
    				g.fillRect(startX+x*5, startY+y*5, 5, 5);
    			}else if(block.equals("r")){
    				g.setColor(new Color(255,0,0));
    				g.fillRect(startX+x*5, startY+y*5, 5, 5);
    			}else if(block.equals("bf")){
    				g.setColor(new Color(30,30,30));
    				g.fillRect(startX+x*5, startY+y*5, 5, 5);
    			}else if(block.equals("wf")){
    				g.setColor(new Color(255,255,255));
    				g.fillRect(startX+x*5, startY+y*5, 5, 5);
    			}else if(block.equals("n")){}
    		}
    	}
    	
    	s.close();    	
    }
}
