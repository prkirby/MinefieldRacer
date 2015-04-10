package GameMechanics;

public class GodMode extends Powerup {

	//Inherits parent's constructor
	public GodMode(int d) {
		super(d);
	}

	//overloads powerup, sets a boolean value in the player
	public void useAbility(Player p){
		p.setGodStatus();
		Powerup temp = new NoPowerup(0); //resets powerup
		p.setPowerup(temp);//removes old powerup
	}
	
	//Grabs powerup's name
	public String getPowerupName(){
		return "GodMode";
	}
}
