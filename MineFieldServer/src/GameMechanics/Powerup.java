package GameMechanics;

//All powerups inherit this structure
public class Powerup {
	int duration;
	public Powerup(int d){
		duration = d;//mostly for invisibility, could have applications elsewhere
	}
	
	//does whatever the powerup needs to do
	public void useAbility(Player p){
		
	}
	
	//return the name of the powerup
	public String getPowerupName(){
		return "";	
	}

}
