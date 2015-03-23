package Drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

public abstract class DrawTips {
	
	private static final int scale = 10;
	
	private static final String[] tips = {"Have fun.",
										  "Being a kamikaze is not only a viable strategy; it's also fun.",
										  "Your K/D ratio doesn't matter. Just get to the finish line. What is this? Call of Duty?",
										  "Remember: Mine dismemberment is only temperary.",
										  "If nothing else, get to the finish for the sick shades.",
										  "The player with the shades is the previous races's winner.",
										  "Don't feel bad about hitting mines. Death happens.",
										  "Don't die."};
	private static int index = 0;
	private static Timer timer;
	
	/**
	 * Draws the "pro" tips
	 * @param g
	 * 			The mainpanel's graphics
	 * @param width
	 * 			The width of the screen
	 * @param height
	 * 			The height of the screen
	 */
    public static void draw(Graphics g, int width, int height){
    	if(timer == null){
    		timer = new Timer();
    		timer.schedule(new RemindTask(), 1000);
    	}
    	
    	g.setColor(Color.red);
    	g.setFont(new Font("Courier",Font.BOLD,28));
    	g.drawString("Pro", width/2-170, 3*height/4);
    	g.drawString("Tip", width/2-170, 3*height/4+20);
    	g.setColor(Color.white);
    	g.setFont(new Font("Courier",Font.PLAIN,15));
    	for(int i = 0; i < tips.length%40; i++){
    		if(i*40+40>tips[i].length())
            	g.drawString(tips[index].substring(i*40, tips[index].length()-1), width/2-115, 3*height/4-3+i*14);
    		else
    			g.drawString(tips[index].substring(i*40, i*40+40), width/2-115, 3*height/4-3+i*14);
    	}
    }
    
    private static class RemindTask extends TimerTask{

		public void run() {
			index = ((int)(Math.random()*100)%tips.length);
			System.out.println("hit "+index);
			timer.schedule(new RemindTask(), 1000);//Every 5 seconds switch
		}
    	
    }

}
