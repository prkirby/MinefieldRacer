package GameMechanics;

public class NoPowerup extends Powerup{

	public NoPowerup(int d) {
		super(d);
		// TODO Auto-generated constructor stub
	}

	public void useAbility(){
		System.out.println("I have no powerup");
	}
	
	public String getPowerupName(){
		return "NULL";
	}

}
