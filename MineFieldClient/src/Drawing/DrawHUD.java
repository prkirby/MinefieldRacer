package Drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class DrawHUD {
	
	public static void draw(Graphics g, int width, int height, String time){
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica",Font.BOLD,24));
		g.drawString(time, width/2-(7*time.length())-2, 35);
		g.setColor(Color.white);
		g.drawString(time, width/2-(7*time.length()), 33);
		
	}
}
