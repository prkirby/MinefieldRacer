package GameMechanics;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import Main.FileReading;

public class MapTest {

	/**
	 * mapTest
	 * 
	 * Checks to see that empty maps are being created with the
	 * parametized dimensions.
	 */
	@Test
	public void mapTest() {
		Map newMap = new Map(40,40);
		if (newMap.map.length != 40 || newMap.map[0].length != 40) {
			fail("Map incorrectly sizing with given parameters (40, 40).");
		}
	}
	
	/**
	 * mapReadTest
	 * 
	 * Reads the given file and lets you know if it read the map correctly
	 * by checking points (5,5) and (10,10).
	 * 
	 * Not currently working?
	 */
	@Test
	public void mapReadTest() {
		
		Map mapArray[];
		Map newMap = new Map();
		
		FileReading temp = new FileReading();
		File f [] = temp.getMaps();
		mapArray = new Map[f.length];
		for(int i = 0; i < mapArray.length; i++){
			mapArray[i] = new Map(f[i]);
		}

		//choose random map
		newMap = mapArray[1];
		if (newMap.map[5][5] == "w") {
			fail("Not correctly reading map labeled: test1.txt at point [5][5].");
		}
		if (newMap.map[10][10] == "w") {
			fail("Not correctly reading map labeled: test1.txt at point [10][10].");
		}
	}
	
	/**
	 * validLocationTest
	 * 
	 * Checks if the given input is a valid location for the unit to enter.
	 */
	@Test
	public void validLocationTest() {
		Map newMap = new Map(0,0);
		if (newMap.validLocation(0, 0)) {
			fail("Not correctly reading location (0,0) which is a wall in: test1.txt.");
		}
		if (newMap.validLocation(5, 5)) {
			fail("Not correctly reading location (5,5) which is an open space in: test1.txt.");
		}
	}

}