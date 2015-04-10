package GameMechanics;

public class NoPowerup extends Powerup{

	public NoPowerup(int d) {
		super(d);
	}

	//does nothing
	public void useAbility(){
		
	}
	//returns null
	public String getPowerupName(){
		return "NULL";
	}

}
