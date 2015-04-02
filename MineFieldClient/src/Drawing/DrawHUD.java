package Drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class DrawHUD {
	
	/**
	 * Spectator draw mode
	 * @param g
	 * 			The graphics
	 * @param width
	 * 			The width of the screen
	 * @param height
	 * 			The height of the screen
	 * @param time
	 * 			The current time
	 */
	public static void draw(Graphics g, int width, int height, String time){
		//Time
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica",Font.BOLD,24));
		g.drawString(time, width/2-(7*time.length())-2, 35);
		g.setColor(Color.white);
		g.drawString(time, width/2-(7*time.length()), 33);
		
	}
	
	/**
	 * In-race draw mode
	 * @param g
	 * 			The graphics
	 * @param width
	 * 			The width of the screen
	 * @param height
	 * 			The height of the screen
	 * @param time
	 * 			The current time
	 * @param flags
	 * 			The number of flags the client has
	 * @param pColor
	 * 			The color of the client
	 */
	public static void draw(Graphics g, int width, int height, String time, int flags, Color pColor, String[][] map, int px, int py){
		//Time
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica",Font.BOLD,24));
		g.drawString(time, width/2-(7*time.length())-2, 35);
		g.setColor(Color.white);
		g.drawString(time, width/2-(7*time.length()), 33);
		
		//Flag count Outline
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica",Font.BOLD,20));
		String f = ""+flags;
		if(flags<10)
			f = "0"+flags;
		g.drawString(""+f+"/10", width-74, height-19);
		
		int x = width-24, y = height-34;
		//Flag
		g.fillRect(x+3, y+13, 14, 4);
		g.fillRect(x+5, y+12, 10, 4);
		g.fillRect(x+9, y+5, 2, 10);
		int[] xx = {x+3, x+10, x+10, x+3};
		int[] yy = {y+3,y-2,y+8,y+3};
		Polygon flag = new Polygon(xx,yy,xx.length);
		g.fillPolygon(flag);
		
		//Flag count
		g.setColor(pColor);
		g.setFont(new Font("Helvetica",Font.BOLD,20));
		f = ""+flags;
		if(flags<10)
			f = "0"+flags;
		g.drawString(""+f+"/10", width-75, height-20);
		
		x = width-25; y = height-35;
		
		//Flag
		g.fillRect(x+3, y+13, 14, 4);
		g.fillRect(x+5, y+12, 10, 4);
		g.fillRect(x+9, y+5, 2, 10);
		int[] xx2 = {x+3, x+10, x+10, x+3};
		int[] yy2 = {y+3,y-2,y+8,y+3};
		Polygon flag2 = new Polygon(xx2,yy2,xx2.length);
		g.fillPolygon(flag);
		
		//Mini map
    	int wid = map.length;
    	int hei = map[0].length;
    	
    	g.setColor(Color.black);
		int scale = 4;
		
		int dx = 0, dy = 0;
		
		for(int h = py-7; h <= py+7; h++){
			dx = 0;
			for(int w = px-10; w <= px+10; w++){
				if(h < 0 || h >= hei || w < 0 || w >= wid){
					g.setColor(new Color(80,80,80));
				}else{
					String block = map[w][h];
					if(block.equals("W")){
	    				g.setColor(Color.black);
	    			}else if(block.equals("y")){
	    				g.setColor(new Color(222,222,0));
	    			}else if(block.equals("c")){
	    				g.setColor(new Color(193,193,193));
	    			}else if(block.equals("r")){
	    				g.setColor(new Color(255,0,0));
	    			}else if(block.equals("bf")){
	    				g.setColor(new Color(30,30,30));
	    			}else if(block.equals("wf")){
	    				g.setColor(new Color(255,255,255));
	    			}
				}
				g.fillRect(10+dx*scale, height-70+dy*scale, scale, scale);
				dx++;
			}
			dy++;
		}
		g.setColor(Color.black);
		g.drawRect(10,height-70, 21*scale, 15*scale);
		g.setColor(pColor);
		g.fillRect(10+10*scale, height-70+7*scale, scale, scale);
	}
}
