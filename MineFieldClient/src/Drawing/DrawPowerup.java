package Drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import GameMechanics.Entity;

public class DrawPowerup {
	private static final int scale = 10;

	public static void draw(Graphics g, int xcoor, int ycoor, String name){
		Graphics2D g2d = (Graphics2D)g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("Serif", Font.PLAIN, 36);//size is arbitrary
		g2d.setFont(font);
		
		
		//int height = 100; //size of drawing
		//int width = 100;

		if(name.equals("NULL")){
			//draw no powerup
			//g.setColor(Color.red);
			g2d.setColor(Color.red);
			g2d.drawString("NO POWERUP", xcoor, ycoor);
			
			//g.fillOval(0, ycoor - height, width, height);
		}
		else if(name.equals("Invisibility")){
			//draw invisibility
			g2d.setColor(Color.blue);
			g2d.drawString("INVISIBILITY", xcoor, ycoor);
		}

		else if(name.equals("MineShield")){
			g.setColor(Color.magenta);
			g2d.drawString("MINESHIELD", xcoor, ycoor);
		}
		else if(name.equals("GodMode")){
			g.setColor(Color.white);
			g2d.drawString("GODMODE", xcoor, ycoor);
		}
		else if(name.equals("ViewportExtender")){
			g.setColor(Color.green);
			g2d.drawString("VISION", xcoor, ycoor);
		}
		else if(name.equals("Nuke")){
			g.setColor(Color.orange);
			g2d.drawString("NUKE", xcoor, ycoor);
		}
	}
}
