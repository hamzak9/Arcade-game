import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;

/**
 * Author : Hamza Khan
 * Date : January 2017
 * BirdGUI class
 * Description : This class is the Graphical User Interface for BainsyBird . It allows the user to select with what bird they want to play
 * and what game mode. It then throws them into a game allowing them to control the bird and back to the main menu when they are satisfied.
 * Method List : public BirdGUI() -- creates all jbuttons,jlist required 
 * 				public void actionPerformed(ActionEvent e)  -- triggers an action when buttons are pressed
        		public int getFlappyScore() // getter method for score
        		public void valueChanged(ListSelectionEvent e) -- sets the value to the variables that bainsybird takes in according to the user's input.
        		        public boolean JListChecker()  -- checks if user is able to click the start button or not 

 *
 */
public class BirdGUI extends JFrame implements ActionListener, ListSelectionListener
{
	private BainsyBird flappyGame;  // declaring variables
	private String birdImage = ""; 
	private DefaultListModel character,modes;
	private JButton btnStart, btnMainMenu;  
	private JList charlist,modeList ; 
	private ButtonGroup group; 
	private ImageIcon Bird;
	private JLabel title;
	private JLabel bg, pickChar, mode; 
	private double x2=0;

	public static void main(String[] args) 
	{
		new BirdGUI();  // creating new birdgui
	}

	public BirdGUI() 
	{
		super("Bainsy Bird"); // name of window

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int) screenSize.getHeight(); 

		//Procedure for embedding images for JAR file
		try 
		{ 
			title = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("title.png"))));
			pickChar = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("pickChar.png"))));
			bg = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("guiBG.jpg"))));
			mode = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("mode.png"))));

		}
		catch (Exception e)
		{ 
		}

		getContentPane().setLayout(null); // set layout to null


		group = new ButtonGroup(); // group buttons 


		bg.setBounds(0,0,800,720);

		btnStart = new JButton("Start"); // creating main menu/start buttons
		btnStart.setBackground(Color.WHITE);
		btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBackground(Color.WHITE);



		pickChar.setBounds(0, 110, 400, 200);


		mode.setBounds(0,400, 200, 200);



		character = new DefaultListModel();  // adding elements to list so user can select bird
		charlist = new JList(character); 
		character.addElement("Flappy Bird");
		character.addElement("Frozen Bird");
		character.addElement("Angry Bird");
		character.addElement("Happy Bird");
		character.addElement("Green Bird");
		character.addElement("Red Bird");
		modes = new DefaultListModel();
		modeList = new JList(modes);
		modes.addElement("Standard");
		modes.addElement("No gravity");   
		charlist.setBounds(425, 145, 127, 155); // setting bounds for lists,buttons,title,main menu
		title.setBounds(50,0,700,143);
		modeList.setBounds(425,400,127,155);
		btnStart.setBounds(196, 629, 127, 39);
		btnMainMenu.setBounds(475, 629, 128, 39);
		getContentPane().add(charlist); // adding everything onto window
		getContentPane().add(modeList);
		getContentPane().add(btnStart);
		getContentPane().add(btnMainMenu);
		getContentPane().add(title);
		getContentPane().add(pickChar);
		getContentPane().add(mode);
		getContentPane().add(bg);
		btnStart.setEnabled(false);
		charlist.addListSelectionListener(this); // adding listeners to jLists
		modeList.addListSelectionListener(this);
		btnStart.addActionListener(this); // adding listeners to buttons 
		btnMainMenu.addActionListener(this);
		setSize(800,720); // setting size of window and location
		setLocation (width/2 - 400, height/2 - 390);
		setResizable(false);  // setting so that user cannot increase/decrease size of screen
		setVisible(true); // setting visible true 
	}

	public void actionPerformed(ActionEvent e) 
	{



		if (e.getSource() == btnStart) // if use pressed start button, starts a new game with their settings
		{      
			flappyGame = new BainsyBird(Bird,x2); 
		}
		else if(e.getSource()==btnMainMenu)
		{
			this.dispose(); // closes window, goes back to main menu
		}



	}


	public void valueChanged(ListSelectionEvent e)
	{ 

		if ((JListChecker()==true)) // checks if user has picked something on the jlist, if not jlistchecker retruns true
		{ 
			btnStart.setEnabled(true); //sets start button to be clickable
		}

		int value = charlist.getSelectedIndex();
		// declaring int value to the index selected for the characters list
		//  If the user presses the first option, it get the value and set the bird's image to the desired character.
		int val2= modeList.getSelectedIndex();
		if(val2==0){
			this.x2=1;

		}
		else{
			this.x2=2;
		}

		if(value==-0)
		{

			birdImage = ("properbird.png"); 
		}

		else if (value == 1)
		{ 
			birdImage = ("icybird.png"); 
		}
		else  if (value == 2)
		{ 
			birdImage = ("angrybird.png");  
		}

		else  if (value == 3)
		{ 
			birdImage =("happybird.png"); 
		}
		else   if (value == 4)
		{ 
			birdImage = ("greenbird.png"); 
		}
		else if (value == 5)
		{ 
			birdImage = ("redyellowbird.png"); 
		}

		//Procedure for embedding images for JAR file
		try 
		{ 
			this.Bird = new ImageIcon (ImageIO.read(getClass().getResource(birdImage)));	      			
		}
		catch (Exception e2)
		{ 
		}



	}

	public boolean JListChecker()
	{ 
		int value = charlist.getSelectedIndex(); // gets selected index of char list and mde list
		int modelist=modeList.getSelectedIndex();
		if (value ==-1 || modelist ==-1 && !btnStart.isEnabled()) // checks if its less than 0, returns false
		{ 
			return false; 
		}
		else // returns true if user has selected an option
		{ 
			return true; 
		}
	}



	public int getFlappyScore() // getter method for score
	// returns score from flappy bird
	{ 
		if (flappyGame!=null)
		{ 
			return this.flappyGame.getScore();  // returns flappy game score 
		} 
		return -1;  // returns -1 if not found 
	}




}
