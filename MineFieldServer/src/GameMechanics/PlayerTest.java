package GameMechanics;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void pointTest() {
		Player you = new Player();
		for (int i = 0; i < 10; i++ ) {
			you.addAPoint();
		}
		if (you.getPoints() != 10) {
			fail("Points not incrementing correctly.");
		}
		
		you.resetPoints();
		
		if (you.getPoints() != 0) {
			fail("Points not resetting correctly.");
		}
		
		you.incrementFlagStreak();
		you.incrementFlagStreak();
		
		if(you.getFlagStreak() != 2) {
			fail("FlagStreak not incrememnting correctly.");
		}
	}

}
