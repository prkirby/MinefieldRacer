package GameMechanics;

import java.util.Timer;
import java.util.TimerTask;



public class ViewportExtender extends Powerup {

	Player pl; //stores the player, so that we can change their visibility
	public ViewportExtender(int d) {
		super(d);
	}
	//makes it so that the user can see farther for duration seconds
	public void useAbility(Player p){
		p.setViewPort(4);
		pl = p;
		Timer t = new Timer();
		t.schedule(new makeVisible(), duration * 1000);//after duration seconds
		Powerup delete = new NoPowerup(0);
		pl.setPowerup(delete); //uses up the powerup
	}

	//returns the powerup's name
	public String getPowerupName(){
		return "ViewportExtender";	
	}

	//returns the vision of the player back to normal
	class makeVisible extends TimerTask{
		public void run(){
			pl.setViewPort(1);;
		}
	}

}
