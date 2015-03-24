package Drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

public abstract class DrawTips {
	
	private static final int scale = 10;
										 //---------------------------------------|---------------------------------------|---------------------------------------|
	private static final String[] tips = {"Have fun.",
										  "Being a kamikaze is not only a viable   strategy; it's also fun.",
										  "Your K/D ratio doesn't matter. Just get to the finish line. What is this? Call  of Duty?",
										  "Remember: Mine dismemberment is only    temporary.",
										  "If nothing else, get to the finish for  the sick shades.",
										  "The player with the shades is the       previous races's winner.",
										  "Don't feel bad about hitting mines.     Death happens.",
										  "Don't die.",
										  "Don't do drugs kids.",
										  "1/10 opponents are Nazis. Don't let a   Nazi take your victory.",
										  "Notice how your opponents are frowning? Don't let a debbie downer beat your     cheerful smile.",
										  "Powerups are a good thing. If they      weren't they would be called Jim.",
										  "You have 5 minutes to get to the finish line. Although it shouldn't take that   long. Unless your slow.",
										  "If you get lost, go to the right.",
										  "Play smart. Follow the path you have    already made after death. It speeds up  your time.",
										  "Remember: The guy with the glasses is a dick. Unless that guy is you; you're    cool.",
										  "Follow your dreams.",
										  "Not being a racist, but if their color  is not your color, then they're a bad   person and probably steal shit.",
										  "Don't trust the reds. They're commies.",
										  "Avoid other people: Your life depends onit.",
										  "Shit happens.",
										  "Don't get mad; get even.",
										  "Even if someone completes the race,     you have some time to maybe see the     finish line.",
										  "The yellow line is not the finish line.",
										  "#YOLO",
										  "If you see a bug, it's Paul's fault.    Blame him for your struggles.",
										  "Remember: You are always right, everyoneelse is to blame for your problems.",
										  "Aww yiss. Mutha. Fuckin. Bread crumbs.",
										  "The meme was dank.",
										  "It's the final countdown...",
										  "The clock at the top of the screen is   not just for show. It also tells you howmuch time you have left.",
										  "Don't worry. We disabled permadeath.",
										  "Remember: mines blow up in a radius. Usethis knowledge wisely.",
										  "Joey: Write \"Pro Tips\" here.",
										  "Hello World!",
										  "James Walker is awesome.",
										  "Always get consent before you descent."};
	private static int index = ((int)(Math.random()*100)%tips.length);
	private static Timer timer;
	private static final int chars = 40;
	private static final int sec = 7;
	
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
    		timer.schedule(new RemindTask(), sec*1000);
    	}
    	
    	g.setColor(Color.red);
    	g.setFont(new Font("Courier",Font.BOLD,28));
    	g.drawString("Pro", width/2-190, 3*height/4);
    	g.drawString("Tip", width/2-190, 3*height/4+20);
    	g.setColor(Color.white);
    	g.setFont(new Font("Courier",Font.PLAIN,15));
    	for(int i = 0; i < (tips[index].length()/chars)+1; i++){
    		if(i*chars+chars>tips[index].length()){
            	g.drawString(tips[index].substring(i*chars, tips[index].length()), width/2-135, 3*height/4-7+i*14);
    		}else{
    			g.drawString(tips[index].substring(i*chars, i*chars+chars), width/2-135, 3*height/4-7+i*14);
    		}
    	}
    }
    
    private static class RemindTask extends TimerTask{

		public void run() {
			int prev = index;
			while(prev == index)
				index = ((int)(Math.random()*100)%tips.length);
			timer.schedule(new RemindTask(), sec*1000);//Every 5 seconds switch
		}
    	
    }

}
