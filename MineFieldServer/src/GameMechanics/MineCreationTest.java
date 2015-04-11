package GameMechanics;

import static org.junit.Assert.*;

import org.junit.Test;

public class MineCreationTest {

	/**
	 * createMineLayerTest
	 * 
	 * Tests a number of things in createMineLayer method.
	 * 
	 */
	@Test
	public void createMineLayerTest() {
		MineCreation mines = new MineCreation();
		Map newMap = new Map();
		Map mineMap;
		double minePercent;

		// Quick check first. Not currently working either.
		minePercent = 2;
		if (mines.createMineLayer(newMap.map, minePercent) != null) {
			fail("Greater than 100% mines");
		}
		
		minePercent = 0.5;
		mineMap = mines.createMineLayer(newMap.map, minePercent);
		
		int mineCount = 0;
		for (int i = 0; i < mineMap.map.length; i++) {
			for (int j = 0; j < mineMap.map[0].length; j++) {
				if ((mineMap.map[i][j].compareTo("-1")) == 0) {
					mineCount++;
				}
			}
		}
		
		if (mineCount <= 0) {
			fail("Mines are not being created accurately with a mine percentage of 0.5.");
		}
	}

}
