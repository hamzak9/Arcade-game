import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


/*
 * Author: Hamza Khan,Richard Persaud
 * Date: January, 2016
 * Description: This class is the game itself. It has a Bird and BirdXL object in it 
 * and takes inputs of character and mode from the Bird GUI
 * Method List:
 *         public BainsyBird(ImageIcon Bird,double x2) -- default constructor,takes in bird image and game mode
 *                 protected void paintComponent (Graphics g) -- paint method,paints pipes and bird
 *                         public static void main(String[] args) //self testing main
 *                                 public void actionPerformed(ActionEvent e)  -- does actions when buttons are pressed
 *                                         public void keyReleased(KeyEvent e) -- does actions when key is released 
 *                                                 public void newGame()   -- creates new game 
 *                                                         public void checkCollision() -- checks for collisions on pipes / out of screen limits


 *                                 
 *                                 

 */
public class BainsyBird extends JPanel implements ActionListener, KeyListener
{

	private Timer timer; //declaring variables 
	private int highscore; 
	private Pipe pipe, pipe2; 
	public Point box; 
	int counter = 0, afterCounter = 150; 
	private ImageIcon background, bird=new ImageIcon("properbird.png"); 
	private ImageIcon tp,bp,grass;
	private boolean dead;
	private int score=0;
	private JFrame frame;
	private int tries;
	private JButton play;
	Bird Bird3;
	private JButton exit;
	private boolean pressed=false;
	private ImageIcon hsMenu;
	private double x2;
	private static ImageIcon birdTest; 

	public BainsyBird(ImageIcon Bird,double x2) //Author : Hamza 
	{
		frame = new JFrame("Bainsy Bird"); // creating new JFrame Bainsy Bird
		//For getting the screen dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // getting size of user's screen
		int width = (int)screenSize.getWidth(); 
		int height = (int) screenSize.getHeight();

		//Procedure for embedding images for JAR file
		try 
		{ 
			tp = new ImageIcon (ImageIO.read(getClass().getResource("tp.png")));
			bp =new ImageIcon (ImageIO.read(getClass().getResource("bp.png")));
			background = new ImageIcon (ImageIO.read(getClass().getResource("background.png")));
			hsMenu = new ImageIcon (ImageIO.read(getClass().getResource("hsMenu.png")));
			grass = new ImageIcon (ImageIO.read(getClass().getResource("grass.png")));

		}
		catch (Exception e)
		{ 
		}

		if(x2==1) // if 
		{
			Bird3 = new Bird(); // creates standard bird object for standard mode

		}
		else if(x2==2){
			Bird3 = new BirdXL(); // creates birdxl for non-gravity mode
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // create default exit option


		this.bird=Bird; //input from gui is set to the bird on bainsybird class



		play = new JButton("Play"); // creating jbuttons exit/play
		exit = new JButton("Exit");

		play.setBounds(230,350,125,78); // setting bounds and declaring them to be invisible
		play.setBackground(Color.WHITE);
		play.setVisible(true);
		play.setFocusable(false);

		exit.setBounds(460,350,125,78); //setting bounds for exit button,making it invisible,setting its focus to false,setting background to white
		exit.setVisible(false);
		exit.setFocusable(false);
		exit.setBackground(Color.WHITE);

		frame.setSize(800,720); // set size of frame
		frame.setLocation (width/2 - 400, height/2 - 390);  // location of frame
		frame.add(play); // adding listeners to buttons and adding buttons to frame
		frame.add(exit);
		exit.addActionListener(this);
		play.addActionListener(this);
		frame.addKeyListener(this) ;
		frame.add(this);

		box = new Point (3,100);  // creating box to detect collisions
		pipe = new Pipe();  // creating 2 sets of pipes
		pipe2 = new Pipe(); 
		this.tries=0;
		this.x2=x2;
		frame.setResizable(false); // doesnt allow user to increase/decrease size of frame
		frame.setVisible(true); 

	}

	protected void paintComponent (Graphics g) // Author : Richard
	{       
		super.paintComponent(g);


		this.background.paintIcon(this,g,0,0); // painting background image

		bird.paintIcon(this, g, Bird3.getX()*30, Bird3.getY()); // painting bird icon 

		tp.paintIcon(this, g, pipe.getX(),-1*tp.getIconHeight() + pipe.getLength()); // painting top pipe #1

		bp.paintIcon(this,g,pipe.getX(),500 - pipe.getLength2()); // painting bot pipe #1

		tp.paintIcon(this, g, pipe2.getX(),-1*tp.getIconHeight() + pipe2.getLength()); // painting top pipe #2

		bp.paintIcon(this, g, pipe2.getX(), 500 - pipe2.getLength2()); // painting bot pipe #2

		this.grass.paintIcon(this, g, 0, 500);


		g.setColor(Color.BLACK); // sets color to black

		g.setFont(new Font("Aharoni",Font.PLAIN,20)); // new font


		g.drawString("Score:"+this.score/22, 700, 15); // displays score as user is playing

		g.setFont(new Font("Aharoni", Font.PLAIN, 120));          // set new font   
		g.setColor(Color.WHITE); //set color to white 

		if(this.dead==true){ // if bird is dead

			this.hsMenu.paintIcon(this,g,230,150); // paints highscore box
			g.drawString(""+this.score/22, 360, 300); // displays score when bird is dead 


		}
		if(this.pressed==false && this.tries<=1){ // if tries is equal or less than 1 

			g.setFont(new Font("Aharoni", Font.PLAIN, 40));    
			if(this.x2==1){ // gets x2 value, if its 1 will tell user to use up arrow, otherwise use down arrow
				g.drawString("Use UP arrow to Jump", 200, 200);
			}
			else if(this.x2==2){
				g.drawString("Use DOWN arrow to Fall", 200, 200); 

			}
		}

	}

	public static void main(String[] args) // Self - Testing main : Author : Hamza
	{
		String mode = JOptionPane.showInputDialog(null,"S-Standard Mode\nN-No gravity Mode"); // ask user for what mode
		int userbird = Integer.parseInt(JOptionPane.showInputDialog(null,"1-Flappy Bird\n2-Icy bird\n3-Angry Bird\n4-Happy Bird\n5-Green Bird\n6-Red Bird")); // asking user for what bird they want
		ImageIcon birdie = null; // setting birdie to null and changes it to users input later

		String birdFile = "";  
		if(userbird==1){
			birdFile = ("properbird.png");  // sets to flappy bird if user enters 1

		}
		else if(userbird==2){
			birdFile = ("icybird.png");  // sets to icybird if user enters 2

		}
		else if(userbird==3){
			birdFile = ("angrybird.png");  // sets to angrybird if user enters 3

		}
		else if(userbird == 4){
			birdFile = ("happybird.png");  // sets to happybird if user enters 4

		}
		else if (userbird==5){
			birdFile =("greenbird.png");  // sets to greenbird if user enters 5

		}
		else if(userbird==6){
			birdFile = ("redyellowbird.png");  // sets to red bird if user enters 6

		}
		else{
			JOptionPane.showMessageDialog(null, "Invalid input"); // displays error if user invalid is not correct
		} 

		//Procedure for embedding images for JAR file
		try 
		{ 
			birdie = new ImageIcon (ImageIO.read(BainsyBird.class.getResource(birdFile)));	      			
		}
		catch (Exception e2)
		{ 
		}

		if(mode.equalsIgnoreCase("s")){ // if user input is s, starts standard mode
			new BainsyBird(birdie,1);
		}
		else{ // else starts no gravity mode
			new BainsyBird(birdie,2);
		}

	} 

	public void actionPerformed(ActionEvent e)  // Action performed -- Author : Richard
	{

		if(e.getSource()==play){ // when play button is pressed, starts a new game and hides buttons
			this.pressed=true;
			newGame();
			play.setVisible(false);
			exit.setVisible(false);

		}
		else if(e.getSource()==exit)
		{
			frame.dispose();  // disposes frame when exit button is pressed
		}

		Bird3.update(); 

		int x = pipe.getX();  // getting x-coordinates for pipes
		int x2 = pipe2.getX();

		if (x >-50)  // if the pipes location passes -50, sets to another location to bring them back on screen
		{ 
			pipe.setX(x-4);
			if (x < 450)
			{ 
				pipe2.setX(x2-4);
			}
		}
		else
		{ 
			pipe = pipe2; //sets pipe 1 to pipe 2 location
			counter++;  // adds 1 to counter 
			pipe2 = new Pipe();   // creating new pipe 
		}

		Bird3.fall(); // makes bird fall
		repaint(); // repaint


		checkCollision(); //checks for collision, stops timer if collided
	}

	public void keyReleased(KeyEvent e)  // Key released -- Author : Richard
	{
		int i = e.getKeyCode(); // get keypressed


		if (i == KeyEvent.VK_UP&& this.dead==false && this.x2==1)  // if up arrow is pressed and bird is alive   
		{ 
			Bird3.jump(); 
		}
		else if(i==KeyEvent.VK_DOWN && this.dead==false && this.x2==2){ // uses bottom key if its in nongravity mode
			Bird3.jump();

		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	} 

	public void newGame()  // New Game method - Author : Hamza
	{
		tries++;
		play.setVisible(true); // setting buttons visible
		exit.setVisible(true);
		play.setFocusable(false);
		exit.setFocusable(false);

		this.dead = false; //bird dead,resets score
		this.score=0;

		pipe = new Pipe();
		pipe2 = new Pipe(); // creating pipes


		if(x2==1) // checks for what mode and makes birdXL for non-gravity mode and standard Bird object for standard mode
		{
			Bird3 = new Bird();

		}
		else if(x2==2){
			Bird3 = new BirdXL();
		}


		timer = new Timer(10, this); // creating new timer

		if(this.pressed==true){ // starts timer when play is pressed


			timer.start(); 
		}

		Bird3.update(); // gets new time 

	}
	public void checkCollision() // Collision detector method -- Author : Richard & Hamza
	{ 
		int x = pipe.getX();  // gets pipe's X coordinate

		if (x < 125 && x > 38) // adds to score while bird is alive

		{ 
			this.score++; // add to score 

			if (Bird3.getY() < pipe.getLength() || Bird3.getY() + 30 > pipe.getLength() + 100) // if bird hits pipes, timer is stopped,buttons are visible 
			{ 
				timer.stop(); 
				this.dead=true;
				play.setVisible(true);
				exit.setVisible(true);
				this.tries++;

			}
		}

		if(Bird3.getY()>=500 || Bird3.getY()<-30) // if bird has exceeded frame limits, stops timer,buttons are visible
		{
			timer.stop(); 
			this.dead=true;
			play.setVisible(true);
			exit.setVisible(true);
		}

		if (this.dead == true) // if bird is dead and high score is greater than the score achieved, new highscore is set
		{ 
			if (this.highscore < this.score/22)
			{
				this.highscore = this.score/22; 
			}
		}


	}


	public int getScore()  // getter method for score
	{
		return this.highscore;  // returns highest score on flappy bird
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
