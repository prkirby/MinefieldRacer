package Drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class DrawWinner {
	public static void draw(Graphics g, int width, int height, String name, Color c){
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica",Font.BOLD,40));
		g.setColor(c);
		g.drawString(name, 0, height/3);
	}
}
