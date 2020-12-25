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

/*
 * Author: Shahzada Gulfam
 * Date: January, 2016
 * Description: This class is the GUI for the snake game. It takes 3 input from the user, and passes it onto the
 * 				snakeGame through the use of a constructor. The 3 variables passed in are Speed, Snake Colour and Food 
 * 				colour. 
 * 
 */

public class SnakeGUI extends JFrame implements ActionListener, ListSelectionListener
{
	//Has a SnakeGame
	private SnakeGame snakeGame; 
	private int snakeHS, hard = 30, medium = 50, easy = 80; 

	//GUI Elements
	private DefaultListModel snakeColourModel, foodColourModel;
	private JRadioButton rdbtnEasy, rdbtnMedium, rdbtnHard; 
	private JButton btnStart, btnMainMenu;  
	private JList snakeColourList, foodColourList; 
	private ButtonGroup btnGroup; 
	private Color snakeCol, foodCol; 
	private JLabel background; 

	//Self testing main 
	public static void main(String[] args) 
	{
		new SnakeGUI(); 
	}

	//Default constructor 
	public SnakeGUI() 
	{
		//Setting the title of the window
		super("Snake");

		//For getting the screen dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int) screenSize.getHeight(); 

		//Procedure for embedding images for JAR file
		try 
		{ 
			background = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("SnakeBackground.jpg"))));
		}
		catch (Exception e)
		{ 
		}


		//Setting the layout to null
		getContentPane().setLayout(null); 

		//Creating a button group, so that only one radmiobutton can be selected
		btnGroup = new ButtonGroup();

		// Creating JButtons for GUI
		btnStart = new JButton("Start");
		btnStart.setBackground(Color.WHITE);
		btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBackground(Color.WHITE);

		//Creating JLabels
		JLabel lblTitle = new JLabel("Welcome to Snake");
		JLabel lblDifficulty = new JLabel("<html>Select Difficulty <br> Select Hard for Leaderboard Rankings: </html>");
		lblDifficulty.setForeground(Color.WHITE);
		JLabel lblSelectSColour = new JLabel("Select Snake Colour:"); 
		lblSelectSColour.setForeground(Color.WHITE);
		JLabel lblSelectFoodColour = new JLabel("Select Food Colour:");
		lblSelectFoodColour.setForeground(Color.WHITE);

		//Creating List Model, and adding colours to the lists
		snakeColourModel = new DefaultListModel(); 
		foodColourModel = new DefaultListModel(); 
		snakeColourList = new JList(snakeColourModel); //Creating List based on the models
		foodColourList = new JList(foodColourModel);
		snakeColourModel.addElement("Green");
		snakeColourModel.addElement("Blue");
		snakeColourModel.addElement("Red");
		snakeColourModel.addElement("Orange");
		foodColourModel.addElement("Green");
		foodColourModel.addElement("Blue");
		foodColourModel.addElement("Red");
		foodColourModel.addElement("Orange");

		//Creating Radio Buttons
		rdbtnEasy = new JRadioButton("Easy");
		rdbtnEasy.setOpaque(false);
		rdbtnMedium = new JRadioButton("Medium");
		rdbtnMedium.setOpaque(false);
		rdbtnHard = new JRadioButton("Hard");
		rdbtnHard.setOpaque(false);

		//Setting font size and colour
		lblTitle.setForeground(new Color(0, 128, 0));
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblDifficulty.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSelectSColour.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSelectFoodColour.setFont(new Font("Tahoma", Font.PLAIN, 17));

		//Setting bounds
		lblTitle.setBounds(255, 33, 900, 67);
		lblDifficulty.setBounds(77, 160, 170, 70);
		rdbtnEasy.setBounds(255, 158, 127, 25);
		rdbtnMedium.setBounds(394, 158, 127, 25);
		rdbtnHard.setBounds(543, 158, 127, 25);
		lblSelectSColour.setBounds(77, 290, 167, 16);
		snakeColourList.setBounds(255, 291, 127, 155);
		foodColourList.setBounds(614, 291, 127, 155);
		lblSelectFoodColour.setBounds(436, 290, 167, 16);
		btnStart.setBounds(196, 600, 127, 39);
		btnMainMenu.setBounds(475, 600, 128, 39);
		background.setBounds(0, 0, 800, 800);

		//Adding to frame
		getContentPane().add(lblTitle);
		getContentPane().add(lblDifficulty);
		getContentPane().add(rdbtnEasy);
		getContentPane().add(rdbtnMedium);
		getContentPane().add(rdbtnHard);
		getContentPane().add(lblSelectSColour);
		getContentPane().add(snakeColourList);
		getContentPane().add(lblSelectFoodColour);
		getContentPane().add(foodColourList);
		getContentPane().add(btnStart);
		getContentPane().add(btnMainMenu);
		getContentPane().add(background); 

		//Setting buttons to non-focusable, for a cleaner look
		rdbtnEasy.setFocusable(false);
		rdbtnMedium.setFocusable(false);
		rdbtnHard.setFocusable(false);

		//Setting start button as unclickable
		btnStart.setEnabled(false);

		//Adding radio buttons to a group
		btnGroup.add(rdbtnEasy);
		btnGroup.add(rdbtnMedium);
		btnGroup.add(rdbtnHard);

		//Adding action listeners to the radio buttons and list listener to the JList
		rdbtnEasy.addActionListener(this);
		rdbtnMedium.addActionListener(this);
		rdbtnHard.addActionListener(this);
		snakeColourList.addListSelectionListener(this);
		foodColourList.addListSelectionListener(this);
		btnStart.addActionListener(this);
		btnMainMenu.addActionListener(this); 

		//Setting the size, and location of the frame
		setSize(800,720);
		setLocation (width/2 - 400, height/2 - 390);
		setResizable (false); 

		//Setting the frame as visible
		setVisible(true);

	}

	//Called whenever a button is pressed
	public void actionPerformed(ActionEvent e) 
	{
		//Initializing the speed variable that will be passed to the Snake Game
		int speed = 0; 

		//If a radio button has been pressed, and a colour for both food and snake selected
		if ((checkRadioSelected() && checkListSelected()) && !btnStart.isEnabled())
		{ 
			//Allow the start button to be clicked
			btnStart.setEnabled(true);
		}

		//If the radio button for the easy setting is selected
		if (rdbtnEasy.isSelected())
		{ 
			//Set the speed to 80, which will be 80 milliseconds and slower than other settings
			speed = easy; 
		}
		else if (rdbtnMedium.isSelected())
		{ 
			speed = medium; 
		}
		else if (rdbtnHard.isSelected())
		{ 
			speed = hard; 
		}

		//If the start button is clicked
		if (e.getSource() == btnStart)
		{ 
			//Open the snake game, passing on the settings collected
			snakeGame = new SnakeGame(speed, snakeCol, foodCol); 	
		}

		//If the user clicks main menu button
		else if (e.getSource() == btnMainMenu)
		{ 
			//Close this frame
			this.dispose();
		}
	}

	//Method that is called whenever a new value is selected in the JList
	public void valueChanged(ListSelectionEvent e)
	{ 
		//Get the values selected on the JLists
		int value = snakeColourList.getSelectedIndex(); 
		int value2 = foodColourList.getSelectedIndex(); 

		//Check for whether all 3 settings have been filled
		if ((checkRadioSelected() && checkListSelected()) && !btnStart.isEnabled() )
		{ 
			btnStart.setEnabled(true);
		}

		//Setting colours for snake, checking which value has been selected
		if (value == 0)
		{ 
			//If the value of 0 is selected, make the snake Colour green
			this.snakeCol = Color.GREEN; 
		}
		else if (value == 1) 
		{ 
			this.snakeCol = Color.BLUE; 
		}
		else if (value ==2)
		{ 
			this.snakeCol = Color.RED; 
		}
		else if (value == 3)
		{ 
			this.snakeCol = Color.ORANGE; 
		}

		//Checking which index on JList has been selected and assigning a colour to the 
		//food based on the inputted
		if (value2 == 0)
		{ 
			this.foodCol = Color.GREEN; 
		}
		else if (value2 == 1)
		{ 
			this.foodCol = Color.BLUE; 
		}
		else if (value2 == 2)
		{ 
			this.foodCol = Color.RED; 
		}
		else if (value2 == 3)
		{ 
			this.foodCol = Color.ORANGE; 
		}
	}

	//Method that will return true, if both indexes on food and snake list have been selected
	public boolean checkListSelected()
	{ 
		//GetSelectedIndex will return -1, if no value has been chosen 
		if (snakeColourList.getSelectedIndex() == -1 || foodColourList.getSelectedIndex() == -1)
		{ 
			return false; 
		}
		else
		{ 
			return true; 
		}
	}

	//Method that will return true is any of the radio buttons have been clicked
	public boolean checkRadioSelected()
	{ 
		//If no button is selected return false
		if (!(rdbtnEasy.isSelected() || rdbtnMedium.isSelected() || rdbtnHard.isSelected()))
		{ 
			return false; 
		}
		//Otherwise return true
		else 
		{ 
			return true; 
		}
	}

	//Method to return the snake score
	public int getSnakeScore()
	{ 
		if (snakeGame!=null)
		{ 
			//Return only if the speed is on the hard difficulty
			if (snakeGame.getSpeed() == hard)
			{ 
				return this.snakeGame.getSnakeScore(); 
			} 

		} 
		return -1; 

	}
}
