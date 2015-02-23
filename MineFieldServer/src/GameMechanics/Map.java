package GameMechanics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class represents the overall map that
 * clients will race on
 * 
 * @author Joseph Ryan
 */
public class Map {

	public String[][] map;	//The actual map
	
	/**
	 * Default constructor (not in use)
	 */
	public Map(){}
	
	/**
	 * Constructor: Builds an empty map with given dimensions
	 * 
	 * @param width
	 * 			The width of the map
	 * @param height
	 * 			The height of the map
	 */
	public Map(int width, int height){
		
		//Build the map
		map = new String[width][height];
		
		for(int w = 0; w < width; w++){
			for(int h = 0; h < height ; h++){
				map[w][h] = "0";
			}
		}
		
	}
	
	/**
	 * Constructor:  Builds a map based on a given text file <br />
	 * <b>Format:</b><br />
	 * width height <br />
	 * map data...<br /><br />
	 * <b>Example:</b><br />
	 * 4 5<br />
	 * w w w w<br />
	 * 0 0 0 0<br />
	 * 0 0 0 0<br />
	 * w w 0 w
	 * @param f
	 * 			The file to read from
	 */
	public Map(File f){
		
		try {
			Scanner s = new Scanner(f);
			
			int width = s.nextInt();
			int height = s.nextInt();
			map = new String[width][height];
			
			for(int w = 0; w < width; w++){
				for(int h = 0; h < height ; h++){
					map[w][h] = s.next();
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("The given file ("+f.getName()+") is not found.");
		}
	}
	
	/**
	 * Returns the game's map
	 * @return
	 * 			The game's map
	 */
	public String[][] getMap(){
		return this.map;
	}
}
