package Drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class StartupGUI {
	
	//Hud setup vars
	private int Width = 190;
	private int Height = 170;
	private JFrame frame = new JFrame();
	private myJPanel mainpanel = new myJPanel();
	
	//Important vars
	private String name = "NO_NAME";
	private Color color = Color.yellow;
	
	//Setup vars
	private Color[] colorWheel = {Color.red,new Color(255,128,64),Color.yellow,
								  new Color(0,255,0),new Color(0,128,0),new Color(0,255,255),
								  new Color(0,0,255),new Color(64,0,64),new Color(255,0,255),
								  new Color(255,255,255)};
	private myJButton[] colorChoice = new myJButton[colorWheel.length];
	private JButton start = new JButton();
	private JTextArea namer = new JTextArea();
	private boolean dataReady = false;
	
	/**
	 * This is the GUI that gets the 
	 * player's name and color before connection
	 */
	public StartupGUI(){
		frame = new JFrame("START UP");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(Width, Height);
		frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-Width/2, 
				Toolkit.getDefaultToolkit().getScreenSize().height/2-Height/2);

		mainpanel.setPreferredSize(new Dimension(Width, Height));
		mainpanel.setLayout(null);
		mainpanel.setFocusable(true);
		
		this.setupComponents();

		//Start up the magic
		frame.getContentPane().add(mainpanel);
		frame.setVisible(true);
		frame.pack();

		this.display();
	}
	
	private void setupComponents(){
		
		//Setup Color Buttons
		for(int i = 0; i < colorWheel.length; i++){
			colorChoice[i] = new myJButton(colorWheel[i]);
			colorChoice[i].setFocusable(false);
			colorChoice[i].setVisible(true);
			colorChoice[i].addActionListener(colorChoice[i]);
			colorChoice[i].setBounds((i%5)*35+10,(i/5)*35+65,30,30);
//			colorChoice[i].setBorderPainted(false);
			mainpanel.add(colorChoice[i]);
		}
		
		start = new JButton("START");
		start.setFocusable(false);
		start.setVisible(true);
		start.addActionListener(new Start());
		start.setBounds(Width/2-40,140,80,20);
		mainpanel.add(start);
		
		//Text Area
		namer.setText("Enter Name Here");
		namer.setBounds(Width/2-70, 15, 140, 20);
		namer.setVisible(true);
		mainpanel.add(namer);
	}
	
	/**
	 * Denotes if the data is ready to be sent
	 * @return
	 * 			IF the data is ready
	 */
	public boolean dataReady(){
		return this.dataReady;
	}
	
	/**
	 * Returns the selected name when data is ready
	 * @return
	 * 			The name
	 */
	public String getName(){
		if(name==null || name.equals("") || name.equals("Enter Name Here")){
			return "LAME";
		}
		return this.name;
	}
	
	/**
	 * Returns the selected color
	 * @return
	 * 			The color (in string form)
	 */
	public String getColor(){
		if(this.color.equals(Color.red)){
			return "red";
		}else if(this.color.equals(new Color(255,128,64))){
			return "orange";
		}else if(this.color.equals(new Color(0,255,0))){
			return "lime";
		}else if(this.color.equals(new Color(0,128,0))){
			return "green";
		}else if(this.color.equals(new Color(0,255,255))){
			return "cyan";
		}else if(this.color.equals(new Color(0,0,255))){
			return "blue";
		}else if(this.color.equals(new Color(64,0,64))){
			return "purple";
		}else if(this.color.equals(new Color(255,0,255))){
			return "pink";
		}else if(this.color.equals(new Color(255,255,255))){
			return "white";
		}
		return "yellow";
	}
	
	/**
	 * This is called whenever it is time to update the GUI
	 */
	public void display(){
		mainpanel.repaint();
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
			setBackground(Color.GRAY);
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("Courier",Font.BOLD,16));
			g.drawString("Select Color:", Width/2-62, 55);
		}
	}
	
	private class myJButton extends JButton implements ActionListener{
		
		private Color buttonColor;
		
		public myJButton(Color c){
			buttonColor = c;
			this.setBackground(c);
		}

		public void actionPerformed(ActionEvent arg0) {
			color = this.buttonColor;
			
		}
	}
	
	private class Start implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			dataReady = true;
			name = namer.getText();
			frame.dispose();
		}
	}

}
