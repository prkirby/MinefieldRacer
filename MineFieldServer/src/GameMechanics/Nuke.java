package GameMechanics;

public class Nuke extends Powerup{

	public Nuke(int d) {
		super(d);
		// TODO Auto-generated constructor stub
	}
	
	public void useAbility(Player p){
		p.armNuke();
		Powerup temp = new NoPowerup(0);
		p.setPowerup(temp);//removes old powerup
	}
	
	public String getPowerupName(){
		return "Nuke";
	}

}
