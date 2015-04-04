package GameMechanics;

public class GodMode extends Powerup {

	public GodMode(int d) {
		super(d);
		// TODO Auto-generated constructor stub
	}

	public void useAbility(Player p){
		System.out.println("activating godMode");
		p.setGodStatus();
		Powerup temp = new NoPowerup(0);
		p.setPowerup(temp);//removes old powerup
	}
	
	public String getPowerupName(){
		return "GodMode";
	}
}
