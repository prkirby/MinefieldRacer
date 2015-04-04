package GameMechanics;

import java.util.Timer;
import java.util.TimerTask;



public class ViewportExtender extends Powerup {

	Player pl; //stores the player, so that we can change their visibility
	public ViewportExtender(int d) {
		super(d);
		// TODO Auto-generated constructor stub
	}
	public void useAbility(Player p){
		p.setViewPort(4);
		pl = p;
		System.out.println("I can see farther");
		Timer t = new Timer();
		t.schedule(new makeVisible(), duration * 1000);//after duration seconds
		Powerup delete = new NoPowerup(0);
		pl.setPowerup(delete); //uses up the powerup, not sure how garbage collection on this works though
	}

	public String getPowerupName(){
		return "ViewportExtender";	
	}

	class makeVisible extends TimerTask{
		public void run(){
			pl.setViewPort(1);;
		}
	}

}
