package GameMechanics;

public class MineShield extends Powerup{

	public MineShield(int d) {
		super(d);
		// TODO Auto-generated constructor stub
	}
	
	//does whatever the powerup needs to do
		public void useAbility(Player p){
			System.out.println("using the shield");
			p.switchShield();
			Powerup temp = new NoPowerup(0);
			p.setPowerup(temp);//removes old powerup
		}
		
		public String getPowerupName(){
			return "MineShield";
		}

}
