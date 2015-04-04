package GameMechanics;

import java.util.Timer;
import java.util.TimerTask;

public class Invisibility extends Powerup{

	Player pl; //stores the player, so that we can change their visibility
	public Invisibility(int d) {
		super(d);
		// TODO Auto-generated constructor stub
	}
	public void useAbility(Player p){
		p.switchVisibility();
		pl = p;
		System.out.println("I am invisible");
		Timer t = new Timer();
		t.schedule(new makeVisible(), duration * 1000);//after duration seconds
		Powerup deleteInvisibility = new NoPowerup(0);
		pl.setPowerup(deleteInvisibility); //uses up the powerup, not sure how garbage collection on this works though
	}

	public String getPowerupName(){
		return "Invisibility";	
	}

	class makeVisible extends TimerTask{
		public void run(){
			pl.switchVisibility();
		}
	}
}


