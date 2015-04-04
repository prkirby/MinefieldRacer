package Drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import GameMechanics.Entity;

public class DrawGodMode {
	static final int scale = 10;
	public static void draw(Graphics g, Entity p, int width, int height){
		int startX = (width / 2 - p.getSize()*scale / 2)-2;
		int startY = (height / 2 - p.getSize()*scale / 2)-2;

		Graphics2D g2d = (Graphics2D)g;
		//body
		g.setColor(Color.black);
		g.fillOval(startX+4+2, startY+4+2, p.getSize()*scale-8, p.getSize()*scale-8);
		//eyes
		g.setColor(Color.yellow);
		g.fillOval(startX+scale+2, startY+scale, scale, scale);
		g.fillOval(startX+scale*3-2, startY+scale, scale, scale);
		//Mouth
		g2d.setStroke(new BasicStroke(2));
		g.setColor(Color.yellow);
		g.drawArc(startX+scale+6, startY+scale*2+5, scale*2, scale, 200, 140); //Smile
		g2d.setStroke(new BasicStroke(1));
	}
}
