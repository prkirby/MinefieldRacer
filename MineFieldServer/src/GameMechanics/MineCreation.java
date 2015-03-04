package GameMechanics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MineCreation {
	
	public static void main(String[] args){
		createMapFile(30,30,200);
	}
	
	private static File mineLayer = new File("MAPS/mineLayer.txt");
	
	public static File createMapFile(int width, int height, int numMines){
		//quick safety check
		if(numMines > width * height){
			System.out.println("Too many mines for the given dimensions");
			return null;
		}
		Random xrand = new Random();
		Random yrand = new Random();
		int xMineCoor =  xrand.nextInt(width );
		int yMineCoor =  yrand.nextInt(height);
		int [][] mineHolder = new int[width][height];

		int placedMines = 0;

		//places mines first, represented by a -1
		while(placedMines < numMines){
			if(mineHolder[(int) xMineCoor][(int) yMineCoor] != -1){
				mineHolder[(int) xMineCoor][(int) yMineCoor] = -1;
				placedMines++;
				xMineCoor =  xrand.nextInt(width);
				yMineCoor =   yrand.nextInt(height);
			}
			else{
				xMineCoor = xrand.nextInt(width);
				yMineCoor =   yrand.nextInt(height );
			}
		}

		//figures out nonzero tile values
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(mineHolder[i][j] == -1){
					mineHolder = incrementValues(width, height, i, j, mineHolder);
				}
			}
		}

		//writes out minefield
		//If file doesn't exist, create it
		if (!mineLayer.exists()) {
			try {
				mineLayer.createNewFile();
			} catch (IOException e) {
				System.out.println("Could not create mineLayer.txt");
				e.printStackTrace();
			}
		}
		//Create FileWriter
		FileWriter mineWriter = null;
		try {
			mineWriter = new FileWriter(mineLayer.getAbsoluteFile());
		} catch (IOException e) {
			System.out.println("Could not create mineWriter");
			e.printStackTrace();
		}
		
		//Write out the mineLayer row by row
		String row = "";
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){//had to reverse in order to print properly
				row = row + mineHolder[i][j] + " ";
			}
			row += "\n";
			try {
				mineWriter.write(row);
			} catch (IOException e) {
				System.out.println("Could not write to mineLayer.txt");
				e.printStackTrace();
			}
			row = "";
		}
		
		//Try to close mineWriter
		try {
			mineWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mineLayer;

	}//end of createMapFile

	//creates the values for tiles to check for surrounding bombs
	public static int[][] incrementValues(int width, int height, int xCoor, int yCoor, int[][]grid){
		/* 1  2  3
		 * 4  x  5
		 * 6  7  8
		 * 
		 * Check each of the surrounding spots in numerical order
		 */

		//1. Upper Left
		if(xCoor != 0){
			if(yCoor != 0){
				if(grid[xCoor - 1][yCoor -1] >= 0){
					grid[xCoor -1][yCoor -1] ++;
				}
			}
		}

		//2. Middle Upper
		if(yCoor != 0){
			if(grid[xCoor][yCoor -1] >= 0){
				grid[xCoor][yCoor -1] ++;
			}
		}

		//3. Upper Right
		if(yCoor != 0){
			if(xCoor != width - 1){
				if(grid[xCoor + 1][yCoor -1 ]>= 0){
					grid[xCoor + 1][yCoor -1]++;
				}	
			}
		}
		
		//4. Middle Left
		if(xCoor != 0){
			if(grid[xCoor -1][yCoor] >= 0){
				grid[xCoor -1][yCoor] ++;
			}
		}
		
		//5. Middle Right
		if(xCoor != width -1){
			if(grid[xCoor + 1][yCoor] >= 0){
				grid[xCoor + 1][yCoor] ++;
			}
		}
		
		//6. Bottom Left
		if(xCoor != 0){
			if(yCoor != height -1){
				if(grid[xCoor -1][yCoor+ 1] >= 0){
					grid[xCoor - 1][yCoor + 1] ++;
				}
			}
		}
		
		//7.  Middle Bottom
		if(yCoor != height -1){
			if(grid[xCoor][yCoor +1] >= 0){
				grid[xCoor][yCoor +1]++;
			}
		}
		
		//8.  Bottom Right
		if(xCoor != width -1){
			if(yCoor != height -1){
				if(grid[xCoor + 1][yCoor +1] >= 0){
					grid[xCoor +1][yCoor +1]++;
				}
			}
		}
		
		//returns the modified grid for the particular mine,
		//overwrites the previous grid
		return grid;
	}
}//end of MineCreation.java
