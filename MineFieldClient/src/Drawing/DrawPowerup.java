package Drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import GameMechanics.Entity;

public class DrawPowerup {
private static final int scale = 10;
	
	public static void draw(Graphics g, int width, int height, String name){
		Graphics2D g2d = (Graphics2D)g;
		
		if(name.equals("noPowerup")){
			//draw no powerup
			 g.setColor(Color.red);
		     g.fillOval(0, height, 100, 100);
		}
		else if(name.equals("Invisibility")){
			//draw invisibility
			 g.setColor(Color.blue);
		     g.fillOval(0, height, 100, 100);
		}
		
		else if(name.equals("MineShield")){
			g.setColor(Color.magenta);
			g.fillOval(0, height, 100, 100);
		}
		else if(name.equals("GodMode")){
			g.setColor(Color.white);
			g.fillOval(0, height, 100, 100);
		}
	}
}
