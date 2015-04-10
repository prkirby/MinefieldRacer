
package Drawing;

import GameMechanics.Entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import audio.SFX;

/**
 * This class represents the primary visuals for the client.
 * @author Joseph Ryan
 */
public class MainGUI {

	//Hud setup vars
	private int Width = 550;
	private int Height = 550;
	private JFrame frame = new JFrame();
	private myJPanel mainpanel = new myJPanel();

	
	//Player interaction vars
	private boolean[] keyPresses = {false,false,false,false,false,false,false,false,false};

	private boolean canPress = true;

	//Drawing data
	private ArrayList<Entity> coords = new ArrayList<Entity>();
	private String[][] map = new String[11][11];
	private String[][] fullMap;
	private String mapName = "test2";
	private String time;
	private int flags;
	private String mode;
	private String name ="";
	private Color winColor = Color.yellow;
	private boolean winner = false; 
	private int[] movementKeys = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
	private int[] flagKeys = {KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_W};
	private String powerupName = "";
	private boolean shield = false;
	private boolean god = false;
	private boolean nuke = false;
	private String nukeColor = "";
	private String nukeName = "";
	
	/**
	 * Default Constructor: Sets up all of the drawing and audio
	 */
	public MainGUI(){
		frame = new JFrame("SURVIVE!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(Width, Height);
		frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-Width/2, 
				Toolkit.getDefaultToolkit().getScreenSize().height/2-Height/2);

		mainpanel.setPreferredSize(new Dimension(Width, Height));
		mainpanel.setLayout(null);
		mainpanel.addKeyListener(new Push());
		mainpanel.setFocusable(true);

		//Start up the magic
		frame.getContentPane().add(mainpanel);
		frame.setVisible(true);
		frame.pack();

		this.display();
	}

	/**
	 * This is called whenever it is time to update the GUI
	 */
	public void display(){
		mainpanel.repaint();
	}

	/**
	 * Returns the key presses made by the Client
	 * @return 
	 *          The key presses 
	 */
	public boolean[] keyPresses(){
		return this.keyPresses;
	}

	/**
	 * This resets the key presses
	 */
	public void resetKeyPresses(){
		for(int k = 0; k < this.keyPresses.length; k++ )
			this.keyPresses[k] = false;
	}
	/**
	 * Sets up the data of all of the visible entities
	 * @param c 
	 *          The data of the entities
	 */
	public void coords(ArrayList<Entity> c){
		this.coords = c;
	}

	/**
	 * Sets up the data
	 * @param m
	 * 			
	 */
	public void map(String[][] m){
		this.map = m;
	}

	/**
	 * The time of the race
	 * @return
	 * 			The time of the race
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * Sets the powerup to the client
	 * @param s
	 * 			The powerup to change to
	 */
	public void setPowerup(String s){
		powerupName = s;
	}
	
	/**
	 * Sets if the client has the shield powerup
	 * @param b
	 * 			if the client has the shield powerup
	 */
	public void doWeHaveAShield(boolean b){
		shield = b;
	}
	
	/**
	 * Sets if the client has the godmode powerup
	 * @param b
	 * 			if the client has the godmode powerup
	 */
	public void areWeAGod(boolean b){
		god = b;
	}
	
	/**
	 * Sets the nuke powerup conditions
	 * @param b
	 * 			if we have a nuke
	 * @param c
	 * 			the color of the nuker
	 * @param s
	 * 			The name of the nuker
	 */
	public void setNuke(boolean b, String c, String s){
		nuke = b;
		nukeColor = c;
		nukeName = s;
	}
	
	/**
	 * Sets the winner if we have a winner
	 * @param win
	 * 			if we have a winner
	 * @param name
	 * 			The name of the person that won
	 * @param color
	 * 			The color of the person that won
	 */
	public void setWinner(boolean win, String name, String color){
		this.winner = win;
		if(win){
			if(name.length() < 12)
				this.name = name;
			else
				this.name = name.substring(0, 12);
			if(color.equals("red")){
	    		this.winColor = Color.red;
	    	}else if(color.equals("orange")){
	    		this.winColor = new Color(255,128,64);
	    	}else if(color.equals("lime")){
	    		this.winColor = new Color(0,255,0);
	    	}else if(color.equals("green")){
	    		this.winColor = new Color(0,128,0);
	    	}else if(color.equals("cyan")){
	    		this.winColor = new Color(0,255,255);
	    	}else if(color.equals("blue")){
	    		this.winColor = new Color(0,0,255);
	    	}else if(color.equals("purple")){
	    		this.winColor = new Color(64,0,64);
	    	}else if(color.equals("pink")){
	    		this.winColor = new Color(255,0,255);
	    	}else if(color.equals("white")){
	    		this.winColor = new Color(255,255,255);
	    	}else{
	    		this.winColor = Color.yellow;
	    	}
		}
	}

	/**
	 * Sets the time
	 * @param time
	 * 			The new time
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	/**
	 * Sets the number of flags the client has
	 * @param flags
	 * 			The new number
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	/**
	 * Returns the current mode the client is in
	 * @return
	 * 			The new mode the client is in
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Sets the mode the client is about to be in
	 * @param mode
	 * 			The mode of the client
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Changes the name of the map. If a new map, it loads the map
	 * @param name
	 * 			The name of the map
	 */
	public void setMapName(String name){
		String temp = mapName;
		this.mapName = name;
		
		if(!temp.equals(mapName)){
			System.out.println("CHANGE MAP: "+mapName);
			
			//Get input stream from src located maps directory
			InputStream m = MainGUI.class.getResourceAsStream("/MAPS/"+mapName+".txt");
	    	Scanner s = null;
	    	
	    	s = new Scanner(m);
	    	
	    	int wid = s.nextInt();
	    	int hei = s.nextInt();
	    	
	    	
	    	
	    	fullMap = new String[wid][hei];
	    	for(int y = 0; y < hei; y++){
	    		for(int x = 0; x < wid; x++){
	    			String t = s.next();
	    			if(t == null)
	    				System.out.println("ERROR IN MAP MAKE");
	    			fullMap[x][y] = t;
	    		}
	    	}
	    	s.close(); 
		}
	}

	/**
	 * This class represents the panel that the player sees
	 */
	private class myJPanel extends JPanel{

		/**
		 * Paints the panel
		 * @param g 
		 *          The graphics being used to draw
		 */
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			super.setBackground(new Color(80,80,80));

			ArrayList<Entity> temp = coords; //So data is not affected when coords is changed

//			//check for winner inefficiently
//			boolean thereIsAWinner = false;
//			int winnerLocation = 0;
//			for(int i = 0; i < temp.size(); i++){
//				if(temp.get(i).isWinner() == true){
//					if(temp.get(i).getX() >= 97){//bad fix due to map size and lack of passing info
//						thereIsAWinner = true;
//						winnerLocation = i;
//						break;
//					}
//
//				}
//			}

			//System.out.println(temp.size());

				if(mode == null) mode = "SPEC";
				
				if(mode.equals("SPEC")){
					if(fullMap!=null)
						DrawMap.draw(g, mapName, fullMap, mainpanel.getWidth(), mainpanel.getHeight());
					for(int e = 1; e < temp.size(); e++){
						DrawEntity.draw(g, temp.get(e),mainpanel.getWidth(), mainpanel.getHeight(),mapName);
	
					}
					DrawHUD.draw(g, mainpanel.getWidth(), mainpanel.getHeight(), "NEXT RACE IN: "+time);
					DrawTips.draw(g, mainpanel.getWidth(), mainpanel.getHeight());
				}else if(mode.equals("RACE")){
					if(map!=null && temp.size()>0){
						DrawMap.draw(g, map,temp.get(0).getColor());
					}
					if(temp.size()>0){
						if(god){
							DrawGodMode.draw(g, temp.get(0), mainpanel.getWidth(), mainpanel.getHeight(), temp.get(0).getName());
						}
						else{
							DrawPlayer.draw(g, temp.get(0), mainpanel.getWidth(), mainpanel.getHeight(),  temp.get(0).getName());
						}
					}
					//draws other players
					for(int e = 1; e < temp.size(); e++){
						if(temp.get(e).getGodMode()){
							DrawGodModeEntity.draw(g, temp.get(e),temp.get(0),mainpanel.getWidth(),mainpanel.getHeight(), temp.get(e).getName());
						
						}
						else{
							DrawEntity.draw(g, temp.get(e),temp.get(0),mainpanel.getWidth(),mainpanel.getHeight(), temp.get(e).getName());
						}
						
						DrawHUD.drawSpotOnBar(g, temp.get(e).getColor(), mainpanel.getWidth(), fullMap.length, temp.get(e).getX());
					}
					
					
					//draw powerup
					DrawPowerup.draw(g, 0, mainpanel.getHeight(), powerupName);
					
					//draw MineShield
					if(shield)
						DrawMineShield.draw(g, temp.get(0), mainpanel.getWidth(), mainpanel.getHeight());
					
					DrawHUD.drawProgressBar(g, mainpanel.getWidth(), fullMap.length);
					if(temp.size() >0)
						DrawHUD.drawSpotOnBar(g, temp.get(0).getColor(), mainpanel.getWidth(), fullMap.length, temp.get(0).getX());
					for(int e = 1; e < temp.size(); e++){
						DrawHUD.drawSpotOnBar(g, temp.get(e).getColor(), mainpanel.getWidth(), fullMap.length, temp.get(e).getX());
					}
					if(map!=null)
						DrawMap.drawNumbers(g, map);
					if(temp.size() > 0)
						DrawHUD.draw(g, mainpanel.getWidth(), mainpanel.getHeight(), time, flags,temp.get(0).getColor(), fullMap, temp.get(0).getX(), temp.get(0).getY());
					
				}
				
				if(nuke){
					DrawNukeExplosion.draw(g, mainpanel.getWidth(), mainpanel.getHeight(), nukeName, nukeColor);
				}
				//draw the winner
				if(winner){
					DrawWinner.draw(g, mainpanel.getWidth(), mainpanel.getHeight(), name + " WINS!", winColor);
				}
		}
	}

	/**
	 * This class represents the button pushes by the client
	 */
	private class Push extends myJPanel implements KeyListener{

		/**
		 * The default constructor (not in use)
		 */
		public Push(){}

		/**
		 * The event that occurs when a key is pressed
		 * @param e 
		 *          The event
		 */
		public void keyPressed(KeyEvent e) {

			if(canPress) {
				switch (e.getKeyCode()){
				//Movement Keys
				case KeyEvent.VK_LEFT:
					keyPresses[0] = true;  
					canPress = false;
					break;
				case KeyEvent.VK_UP:
					keyPresses[1] = true; 
					canPress = false;
					break;
				case KeyEvent.VK_RIGHT:
					keyPresses[2] = true;  
					canPress = false;
					break;
				case KeyEvent.VK_DOWN:
					keyPresses[3] = true; 
					canPress = false;
					break;
					
				//Flag laying keys
				case KeyEvent.VK_A:
					keyPresses[4] = true;  
					canPress = false;
					break;
				case KeyEvent.VK_W:
					keyPresses[5] = true;  
					canPress = false;
					break;
				case KeyEvent.VK_D:
					keyPresses[6] = true; 
					canPress = false;
					break;
				case KeyEvent.VK_S:
					keyPresses[7] = true;
					canPress = false;
					break;
					
				//PowerupKey
				case KeyEvent.VK_M:
					keyPresses[8] = true;
					canPress = false;
					break;
				}
			}
		}

		/**
		 * The event that occurs when a key is typed (not in use)
		 * @param e 
		 *          The event
		 */
		public void keyTyped(KeyEvent e) {}

		/**
		 * The event that occurs when a key is released (temporarily in use)
		 * @param e 
		 *          The event
		 */
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()){
			//Movement Keys
			case KeyEvent.VK_LEFT:
				keyPresses[0] = false;
				canPress = true;    
				break;
			case KeyEvent.VK_UP:
				keyPresses[1] = false;
				canPress = true;    
				break;
			case KeyEvent.VK_RIGHT:
				keyPresses[2] = false;
				canPress = true;    
				break;
			case KeyEvent.VK_DOWN:
				keyPresses[3] = false;
				canPress = true;    
				break;
			
			//Flag laying keys
			case KeyEvent.VK_A:
				keyPresses[4] = false;
				canPress = true;
				break;
			case KeyEvent.VK_W:
				keyPresses[5] = false;
				canPress = true;    
				break;
			case KeyEvent.VK_D:
				keyPresses[6] = false;
				canPress = true;    
				break;
			case KeyEvent.VK_S:
				keyPresses[7] = false;
				canPress = true;    
				break;
				
			//Powerup key
			case KeyEvent.VK_M:
				keyPresses[8] = false;
				canPress = true;
				break;
			}
			
			


		}
	}
}
