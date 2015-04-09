package Drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class DrawNukeExplosion {
private static final int scale = 10;
	
	public static void draw(Graphics g, int width, int height, String name, String color){
		Color c;
		if(color.equals("red")){
    		c = Color.red;
    	}else if(color.equals("orange")){
    		c = new Color(255,128,64);
    	}else if(color.equals("lime")){
    		c = new Color(0,255,0);
    	}else if(color.equals("green")){
    		c = new Color(0,128,0);
    	}else if(color.equals("cyan")){
    		c = new Color(0,255,255);
    	}else if(color.equals("blue")){
    		c = new Color(0,0,255);
    	}else if(color.equals("purple")){
    		c = new Color(64,0,64);
    	}else if(color.equals("pink")){
    		c = new Color(255,0,255);
    	}else if(color.equals("white")){
    		c = new Color(255,255,255);
    	}else{
    		c = Color.yellow;
    	}
		int len = 12;
		g.setFont(new Font("Courier",Font.BOLD,28));
		g.setColor(Color.black);
		name = name + " just nuked the map!";
		g.drawString(name, 0, height/5);
		g.setColor(c);
		g.drawString(name,0, height/5);
		
		//Draw player
		int startX = width - ((name.length() + 5) * 2);
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
	}
}
