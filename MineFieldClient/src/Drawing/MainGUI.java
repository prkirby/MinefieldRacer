
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
	private boolean[] keyPresses = {false,false,false,false,false,false,false,false};

	private boolean canPress = true;

	//Drawing data
	private ArrayList<Entity> coords = new ArrayList<Entity>();
	private String[][] map = new String[11][11];
	private String mapName = "test2";
	private String time;
	private int flags;
	private String mode;
	private String name ="";
	private Color winColor = Color.yellow;
	private boolean winner = false; 
	private int[] movementKeys = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
	private int[] flagKeys = {KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_W};

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

	public String getTime() {
		return time;
	}
	
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

	public void setTime(String time) {
		this.time = time;
	}
	
	public void setFlags(int flags) {
		this.flags = flags;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setMapName(String name){
		this.mapName = name;
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

			if(mode == null) mode = "SPEC";

			if(mode.equals("SPEC")){
				if(map!=null)
					DrawMap.draw(g, mapName, mainpanel.getWidth(), mainpanel.getHeight());
				for(int e = 1; e < temp.size(); e++){
					DrawEntity.draw(g, temp.get(e),mainpanel.getWidth(), mainpanel.getHeight(),mapName);

				}
				DrawHUD.draw(g, mainpanel.getWidth(), mainpanel.getHeight(), "NEXT RACE IN: "+time);
				DrawTips.draw(g, mainpanel.getWidth(), mainpanel.getHeight());
			}else if(mode.equals("RACE")){
				if(map!=null)
					DrawMap.draw(g, map,temp.get(0).getColor());
				if(temp.size()>0)
					DrawPlayer.draw(g, temp.get(0), mainpanel.getWidth(), mainpanel.getHeight());
				for(int e = 1; e < temp.size(); e++){
					DrawEntity.draw(g, temp.get(e),temp.get(0),mainpanel.getWidth(),mainpanel.getHeight());
				}
				if(map!=null)
					DrawMap.drawNumbers(g, map);
				DrawHUD.draw(g, mainpanel.getWidth(), mainpanel.getHeight(), time, flags,temp.get(0).getColor());
				
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
				}

				for (int i : movementKeys) {
					if (e.getKeyCode() == i) {
						SFX.MOVE.play();
					}
				}
				
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					SFX.SHOOT.play();
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
			}


		}
	}
}
