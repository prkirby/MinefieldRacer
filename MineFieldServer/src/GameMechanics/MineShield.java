package GameMechanics;

public class MineShield extends Powerup{

	public MineShield(int d) {
		super(d);
	}
	
	//switches a boolean value so that we can survive a bomb explsion
		public void useAbility(Player p){
			p.switchShield();
			Powerup temp = new NoPowerup(0);
			p.setPowerup(temp);//removes old powerup
		}
		
		//returns the powerup name
		public String getPowerupName(){
			return "MineShield";
		}

}
