package GameMechanics;

import java.util.Timer;
import java.util.TimerTask;

public class Invisibility extends Powerup{

	Player pl; //stores the player, so that we can change their visibility
	public Invisibility(int d) {
		super(d);
	}
	//overloads powerup, switches a boolean so we don't draw
	//this player for other players
	public void useAbility(Player p){
		p.switchVisibility();
		pl = p;
		Timer t = new Timer();
		t.schedule(new makeVisible(), duration * 1000);//after duration seconds
		Powerup deleteInvisibility = new NoPowerup(0);
		pl.setPowerup(deleteInvisibility);
	}

	//returns the powerup's name
	public String getPowerupName(){
		return "Invisibility";	
	}

	//makes us visible again after duration amount of seconds
	class makeVisible extends TimerTask{
		public void run(){
			pl.switchVisibility();
		}
	}
}


