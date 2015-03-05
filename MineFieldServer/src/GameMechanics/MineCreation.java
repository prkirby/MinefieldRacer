package GameMechanics;

import java.util.Random;

public class MineCreation {
	
	private static Map mineLayer = null;
	
	public static Map createMineLayer(String[][] curMap, double minePercent){
		//quick safety check
		if(minePercent > 1.0){
			System.out.println("Greater than 100% mines");
			return null;
		}
		
		// Get number of mines to be placed based on percentage, 
		// Get width and height of current map
		int numMines = (int)(minePercent * (curMap.length * curMap[0].length));
		int width = curMap.length;
		int height = curMap[0].length;
		
		Random xrand = new Random();
		Random yrand = new Random();
		int xMineCoor =  xrand.nextInt(width - 4) + 4; // no mines on first four columns
		int yMineCoor =  yrand.nextInt(height);
		int [][] mineHolder = new int[width][height];

		int placedMines = 0;

		//places mines first, represented by a -1
		while(placedMines < numMines){
			if(mineHolder[xMineCoor][yMineCoor] != -1 && 
					curMap[xMineCoor][yMineCoor].compareTo("c") == 0){
				mineHolder[(int) xMineCoor][(int) yMineCoor] = -1;
				placedMines++;
				xMineCoor =  xrand.nextInt(width - 4) + 4;
				yMineCoor =   yrand.nextInt(height);
			}
			else{
				xMineCoor = xrand.nextInt(width - 4) + 4;
				yMineCoor =   yrand.nextInt(height );
			}
		}

		//figures out nonzero tile values
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(mineHolder[i][j] == -1){
					mineHolder = incrementValues(width, height, i, j, mineHolder, curMap);
				}
			}
		}

		// Convert int[][] to string[][]
		String[][] tempMap = new String[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tempMap[i][j] = Integer.toString(mineHolder[i][j]);
			}
		}

		mineLayer = new Map(tempMap);
		
		return mineLayer;

	}//end of createMapFile

	//creates the values for tiles to check for surrounding bombs
	public static int[][] incrementValues(int width, int height, int xCoor, int yCoor, int[][]grid, String[][] curMap){
		/* 1  2  3
		 * 4  x  5
		 * 6  7  8
		 * 
		 * Check each of the surrounding spots in numerical order
		 */

		//1. Upper Left
		if(xCoor != 0){
			if(yCoor != 0){
				if(grid[xCoor - 1][yCoor -1] >= 0 &&
						curMap[xCoor - 1][yCoor - 1].compareTo("c") == 0){
					grid[xCoor -1][yCoor -1] ++;
				}
			}
		}

		//2. Middle Upper
		if(yCoor != 0){
			if(grid[xCoor][yCoor -1] >= 0 && 
					curMap[xCoor][yCoor - 1].compareTo("c") == 0){
				grid[xCoor][yCoor -1] ++;
			}
		}

		//3. Upper Right
		if(yCoor != 0){
			if(xCoor != width - 1){
				if(grid[xCoor + 1][yCoor -1 ]>= 0 &&
						curMap[xCoor + 1][yCoor - 1].compareTo("c") == 0){
					grid[xCoor + 1][yCoor -1]++;
				}	
			}
		}
		
		//4. Middle Left
		if(xCoor != 0){
			if(grid[xCoor -1][yCoor] >= 0 && 
					curMap[xCoor - 1][yCoor].compareTo("c") == 0){
				grid[xCoor -1][yCoor] ++;
			}
		}
		
		//5. Middle Right
		if(xCoor != width -1){
			if(grid[xCoor + 1][yCoor] >= 0 && 
					curMap[xCoor + 1][yCoor].compareTo("c") == 0){
				grid[xCoor + 1][yCoor] ++;
			}
		}
		
		//6. Bottom Left
		if(xCoor != 0){
			if(yCoor != height -1){
				if(grid[xCoor -1][yCoor+ 1] >= 0 && 
						curMap[xCoor - 1][yCoor + 1].compareTo("c") == 0){
					grid[xCoor - 1][yCoor + 1] ++;
				}
			}
		}
		
		//7.  Middle Bottom
		if(yCoor != height -1){
			if(grid[xCoor][yCoor +1] >= 0 &&
					curMap[xCoor][yCoor + 1].compareTo("c") == 0){
				grid[xCoor][yCoor +1]++;
			}
		}
		
		//8.  Bottom Right
		if(xCoor != width -1){
			if(yCoor != height -1){
				if(grid[xCoor + 1][yCoor +1] >= 0 &&
						curMap[xCoor + 1][yCoor + 1].compareTo("c") == 0){
					grid[xCoor +1][yCoor +1]++;
				}
			}
		}
		
		//returns the modified grid for the particular mine,
		//overwrites the previous grid
		return grid;
	}
}//end of MineCreation.java
