package Drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public abstract class DrawMap {
	
	private static final int scale = 10;
	
	/**
     * Draws the given map
     * @param g
     *          The mainpanel graphics to draw with
     * @param e 
     *          The entity to draw
     */
    public static void draw(Graphics g, String[][] map, Color pColor){
    	for(int x = 0; x < 11; x++){
    		for(int y =0; y < 11; y++){
    			g.setColor(new Color(193,193,193));
    			try{
	    			if(map[x][y].equals("W")){
	    				g.setColor(Color.black);
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    				g.setColor(new Color(150,150,150));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, scale/4);
	    				g.fillRect(x*scale*5, y*scale*5, scale/4, 5*scale);
	    				g.setColor(new Color(40,40,40));
	    				g.fillRect(x*scale*5, y*scale*5+scale*5-scale/4, scale*5, scale/4);
	    				g.fillRect(x*scale*5+scale*5-scale/4, y*scale*5, scale/4, scale*5);
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
	    				g.setColor(new Color(200,0,0));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    			}else if(map[x][y].equals("bf")){
	    				g.setColor(new Color(30,30,30));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    			}else if(map[x][y].equals("wf")){
	    				g.setColor(new Color(255,255,255));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    			}else if(map[x][y].equals("y")){
	    				g.setColor(new Color(216,220,0));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    			}else if(map[x][y].equals("m")){
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    				g.setColor(Color.black);
	    				g.fillOval(x*scale*5+10*scale/12, y*scale*5+10*scale/12, 10*scale/3, 10*scale/3);
	    				g.fillRect(x*scale*5+10*scale/12-5, y*scale*5+5*scale/2-2, 5*scale-7, scale/3);
	    				g.fillRect(x*scale*5+5*scale/2-2, y*scale*5+10*scale/12-5, scale/3, 5*scale-7);
	    				Graphics2D g2d = (Graphics2D)g;
	    				g2d.setStroke(new BasicStroke(4*scale/9));
	    				g.drawLine(x*scale*5+10, y*scale*5+10, x*scale*5+scale*5-10, y*scale*5+scale*5-10);
	    				g.drawLine(x*scale*5+10, y*scale*5+scale*5-10, x*scale*5+scale*5-10, y*scale*5+10);
	    				g2d.setStroke(new BasicStroke(1));
	    				g.setColor(Color.white);
	    				g.fillOval(x*scale*5+10*scale/6, y*scale*5+10*scale/6, 3*scale/4, 3*scale/4);
	    				g.setColor(Color.BLACK);
	        			g.drawRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	        			
	        			//The red x
	        			g.setColor(new Color(199,14,20));
	    				g2d.setStroke(new BasicStroke(2));
	    				g.drawLine(x*scale*5+5, y*scale*5+5, x*scale*5+scale*5-5, y*scale*5+scale*5-5);
	    				g.drawLine(x*scale*5+5, y*scale*5+scale*5-5, x*scale*5+scale*5-5, y*scale*5+5);
	    				g2d.setStroke(new BasicStroke(1));
	    			}
	    			else if(map[x][y].equals("f")) {
	    				//Tile
	    				g.setColor(new Color(193,193,193));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    				g.setColor(new Color(254,254,254));
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, scale/2);
	    				g.fillRect(x*scale*5, y*scale*5, scale/2, 5*scale);
	    				g.setColor(new Color(125,125,125));
	    				g.fillRect(x*scale*5, y*scale*5+scale*5-scale/2, scale*5, scale/2);
	    				g.fillRect(x*scale*5+scale*5-scale/2, y*scale*5, scale/2, scale*5);
	    				
	    				//Flag
	    				g.setColor(Color.BLACK);
	    				g.fillRect(x*scale*5+10, y*scale*5+35, 25, 7);
	    				g.fillRect(x*scale*5+14, y*scale*5+31, 18, 7);
	    				g.fillRect(x*scale*5+5*scale/2-4, y*scale*5+10, 4, 30);
	    				if(pColor.equals(Color.yellow))
	    					g.setColor(Color.red);
	    				else
	    					g.setColor(pColor);
	    				int[] xx = {x*scale*5+10,x*scale*5+5*scale/2,x*scale*5+5*scale/2,x*scale*5+10};
	    				int[] yy = {y*scale*5+15,y*scale*5+7,y*scale*5+23,y*scale*5+15};
	    				Polygon flag = new Polygon(xx,yy,xx.length);
	    				g.fillPolygon(flag);
	    			}
	    			else if(map[x][y].equals("n")){}
	    			else{
	    				g.fillRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
	    				g.setColor(Color.BLACK);
	        			g.drawRect(x*scale*5, y*scale*5, 5*scale, 5*scale);
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
		    				g.drawString(map[x][y],x*scale*5+12, y*scale*5+37);

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
							g.drawString(map[x][y],x*scale*5+10, y*scale*5+35);
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
   
    	InputStream m = DrawMap.class.getResourceAsStream("/MAPS/"+mapName+".txt");
    	Scanner s = null;
    	
    	s = new Scanner(m);
    	
    	int wid = s.nextInt();
    	int hei = s.nextInt();
    	
    	int startX=width/2-wid*5/2, startY=height/2-hei*5/2;
    	
    	//Map name
    	g.setColor(Color.white);
    	g.setFont(new Font("Helvetica",Font.BOLD,28));
    	g.drawString(mapName, width/2-mapName.length()*7, startY-8);
    	
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
    				g.setColor(new Color(193,193,193));
    				g.fillRect(startX+x*5, startY+y*5, 5, 5);
//    				g.setColor(new Color(254,254,254));
//    				g.fillRect(startX+x*5, startY+y*5, 5, 1);
//    				g.fillRect(startX+x*5, startY+y*5, 1, 5);
//    				g.setColor(new Color(125,125,125));
//    				g.fillRect(startX+x*5, startY+y*5+5-1, 5, 1);
//    				g.fillRect(startX+x*5+5-1, startY+y*5, 1, 5);
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
