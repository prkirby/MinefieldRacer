package GameMechanics;

public class Nuke extends Powerup{

	public Nuke(int d) {
		super(d);
	}
	
	//switches a boolean such that we have "detonated" a nuke
	public void useAbility(Player p){
		p.armNuke();
		Powerup temp = new NoPowerup(0);
		p.setPowerup(temp);//removes old powerup
	}
	
	//returns the powerup name
	public String getPowerupName(){
		return "Nuke";
	}

}
