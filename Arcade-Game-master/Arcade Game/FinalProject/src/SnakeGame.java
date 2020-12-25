import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;

/*
 * Author: Shahzada Gulfam and Gurbir Bains
 * Date: January, 2016
 * Description: This class is what the SnakeGame itself is. It has an array of SnakeBody objects and also has 
 * 				a food object within it. The SnakeGame handles key presses, and other interaction although does not
 * 				paint the food or snakebody objects, as they paint themsevles. These objects are JComponents on this JPanel. 
 * 
 * Method List:  public SnakeGame() - 
 * 				 public void StartGame() - Resets all of the variables, and game can be started again
 * 				 public void actionPerformed() - called whenever an action takes place
 * 			     public boolean checkBodyCollision() - returns true if snake crashes in it self (Gurbir)
 * 				 public boolean checkFoodCollision() - returns true if head of snake eats food (Gurbir)
 * 				 public boolean foodEqualsSnake() - returns true if new food location inside of body (User cannot see, therefore spawns again)
 * 				 public void pauseMenu (boolean show) - sets the GUI components of the pause menu, to visible or not visible (Gurbir)
 *               public void playAgainMenu (boolean show) - sets the GUI components of the play again menu, to visible or not visible (Gurbir)
 *               public static void main(String[] args) - Self testing main
 *               public void keyPressed(KeyEvent e) - Called when a key is pressed
 *               public void PaintComponent(Graphics g) - Called to paint the objects on the screen
 * 
 */

public class SnakeGame extends JPanel implements KeyListener, ActionListener
{
	//Arraylist of SnakeBodies
	private ArrayList<SnakeBody> body = new ArrayList<SnakeBody>();
	private SnakeBody head = new SnakeBody(); 
	
	//Has a food object
	private Food food; 

	//Frame, to allow JPanel to be added to it
	private JFrame frame;

	//TImer, which controls the animations
	private Timer timer; 

	//Variables to be used by the game
	private int score = 0, countdownForSuper = 0, speed = 0, highscore = 0;  
	private boolean pause;  
	private String dir; 
	private boolean superFood, flag, crash = false; 
	private Color snakeCol, foodCol; 
	private int tailLength = 0; 

	//JButtons required for pause Menu
	private JButton btnResume = new JButton ("Resume"); 
	private JButton btnQuit = new JButton ("Quit"); 
	private JButton btnPlayAgain = new JButton ("Play Again"); 

	public SnakeGame(int speed, Color snakeCol, Color foodCol)
	{ 
		//Setting the title of the window
		frame = new JFrame("Snake");

		//For getting the screen dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int) screenSize.getHeight();


		//Setting the location of the buttons
		btnResume.setBounds(250, 300, 120, 90);
		btnPlayAgain.setBounds(250, 300, 120, 90);
		btnQuit.setBounds(430, 300, 120, 90);

		//Setting the background colours of the buttons
		btnResume.setBackground(Color.blue);
		btnPlayAgain.setBackground(Color.blue);
		btnQuit.setBackground(Color.red);

		//Adding the buttons to the frame
		frame.add(btnResume); 
		frame.add(btnPlayAgain);
		frame.add(btnQuit); 

		//Initially setting all the buttons as invisible
		btnResume.setVisible(false);
		btnPlayAgain.setVisible(false);
		btnQuit.setVisible(false);

		//Closes JFrame when exit button is presed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Setting the size, and location of the frame
		frame.setSize(800,720);
		frame.setLocation (width/2 - 400, height/2 - 390); 
		frame.addKeyListener(this); //Adding keylistener to the frame

		//Adding action listeners to the buttons and setting their focus to false
		btnResume.addActionListener (this);
		btnQuit.addActionListener (this);
		btnPlayAgain.addActionListener (this);
		btnResume.setFocusable(false); 
		btnPlayAgain.setFocusable(false); 

		this.setLayout(new OverlayLayout(this)); 

		//Setting the speeds and colours, based on info provided by GUI
		this.speed = speed; 
		this.snakeCol = snakeCol; 
		this.foodCol = foodCol; 

		startGame(); //Calling the start game method

		frame.add(this); //Adding the JPanel to the frame
		frame.setResizable(false);
		frame.setVisible(true); //Setting frame to visible  

	}

	//Method to start the game
	public void startGame()
	{ 
		//Resets all of the variables used by the game
		this.removeAll();
		this.body.clear();
		this.crash = false; 
		this.superFood = false; 
		this.countdownForSuper = 0; 
		this.tailLength = 1;
		this.score = 0;
		this.dir = null; 
		this.timer = new Timer(speed, this); //Timer is based on difficulty selected
		this.pause = false; 
		this.timer.start();

		//Creating new food objects, based on colour passed in
		food = new Food(foodCol); 
		add(food); 

		//Creating head object, based on colour passed in
		head = new SnakeBody(5, 5, snakeCol); 
		this.body.add(head);  
		add(head);

		frame.repaint(); 
	}

	//Method that is called as the timer is run
	public void actionPerformed (ActionEvent e)
	{ 
		//Check if the play again button has been pressed
		if (e.getSource() == btnPlayAgain)
		{
			//If the button has been pressed, call the start game method
			startGame();
			playAgainMenu(false); //Make the play again menu invisible
		}

		//If the quit button has been pressed
		else if (e.getSource() == btnQuit)
		{
			//Exit the game
			frame.dispose(); 
		}

		//If they click resume
		else if (e.getSource() == btnResume)
		{
			//set pause to false, and close the menu
			pause = false;
			pauseMenu(pause);       
		}  

		//Run this code only if the game is not paused
		if (pause == false && crash == false)
		{ 
			//Set the flag to false, this flag makes sure two arrow keys can't be pressed at once
			flag = false; 

			//Add a new body to the snake, at the location of where the head is
			SnakeBody body = new SnakeBody(head.getBodyX(), head.getBodyY(), snakeCol); 
			this.body.add(body); 
			add(body); 
			frame.setVisible(true);

			//Repaint the panel to update 
			repaint(); 

			//If the direction is set as left
			if (dir == "LEFT")
			{ 
				//Change the x location of the snake by one
				head.setBodyLocation(head.getBodyX()-1, head.getBodyY());
			}

			//If the direction is set as right
			else if (dir == "RIGHT")
			{ 
				head.setBodyLocation(head.getBodyX()+1, head.getBodyY());
			}

			//If the direction is set as up
			else if (dir == "UP")
			{ 
				//Change the Y direction up one
				head.setBodyLocation(head.getBodyX(), head.getBodyY()-1);
			}

			//If the direction is set as down
			else if (dir == "DOWN")
			{ 
				head.setBodyLocation(head.getBodyX(), head.getBodyY()+1);
			}

			head.repaint();
			frame.setVisible(true);

			//If the number of body parts is greater than the length of the snake
			if (this.body.size() > tailLength)
			{ 
				this.remove(this.body.get(1));
				this.body.remove(1); //removes last one (first one added)
			}

			//Perform all the checks
			if (head.checkCrash())
			{ 
				//Stop the timer and show the play again menu
				timer.stop();
				playAgainMenu(true); 
			}

			if (checkBodyCollision()) //Checks if snake collides with itself
			{ 
				//If there is a collision, stop the timer 
				timer.stop();
				playAgainMenu(true); //Show the play again menu
			}

			if (checkFoodCollision()) //Checks if snake has collided with the food
			{ 
				this.remove(food); //Removes old component
				superFood = false; //Set super food boolean to false

				//Add to the length of the tail, based on the food eaten
				tailLength += food.getAddedLength(); 

				countdownForSuper++; //Counter for super food is added

				//If the counter reaches 5, so after player has collected 5 foods
				if (countdownForSuper == 5)
				{ 
					//The new food spawned is a super food
					food = new SuperFood(); 

					//Set counter to 0 and boolean to indicate super food has been spawned
					countdownForSuper = 0; 
					superFood = true; 
				}

				//If the counter has not reached 5, spawn a regular food
				else 
				{ 
					food = new Food();  
				}

				//While loop calls food equals snake method
				while (foodEqualsSnake()) 	//While the new food spawned is still inside the snake
				{ 
					//If the super food boolean is true
					if (superFood)
					{ 
						//Spawn a new super food
						food = new SuperFood(); 
					}
					//Otherwise
					else 
					{ 
						//Spawn a regular food
						food = new Food();
					} 
				}
				this.add(food); 
				score++; //Add to the score
				food.setCol(foodCol); //Set the colour of the new food
			}
		}
	}


	//Method to check if the snake has collided with itself
	public boolean checkBodyCollision()
	{ 
		//For the length of the whole snake
		for (int i = 1; i < body.size(); i++)
		{ 
			//Check if the head has collided with any of the body parts
			if ((head.getBodyX() == body.get(i).getBodyX()) && (head.getBodyY() == body.get(i).getBodyY()))
			{
				return true; 
			}
		}
		return false; 
	} 

	//Method to check if the snake head has collected a food
	public boolean checkFoodCollision()
	{ 
		//If the snake head's location is equal to the foods location
		if ((head.getBodyX() == food.getFoodX()) && (head.getBodyY() == food.getFoodY()))
		{  
			return true; 
		} 
		else 
		{ 
			return false; 
		}
	}

	//Method that ensures a food does not spawn inside of the snake itself
	public boolean foodEqualsSnake()
	{
		//For loop which runs through the length of the snake
		for (int x = 0; x < body.size(); x++)
		{  
			//If any body location is equal to the foods location 
			if (body.get(x).getBodyX() == food.getFoodX() && body.get(x).getY() == food.getFoodY())
			{ 
				//Return true, that the food has spawned inside of the snake
				return true;  
			}
		}

		//If the food has spawned inside of the head of the snake
		if ((head.getBodyX() == food.getFoodX()) && (head.getBodyY() == food.getFoodY()))
		{ 
			//Return true that the food has spawned inside of the snake
			return true; 
		}

		//If the location of the food is correct and not inside the snake return false
		return false; 
	}

	//Paint method
	protected void paintComponent(Graphics g)
	{
		//Cals the parent paint component
		super.paintComponent(g);

		//Paint the background as black
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 720);

		//Live score in corner
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
		g.drawString("Tail Length: " + tailLength, 680, 50);
		g.drawString("Score: " + score, 680, 25);

		//If the dir is null, or the game has not been started yet
		if (dir == null)
		{
			//Tell ther user to press any arrow to start the game
			g.setFont(new Font("Aharoni", Font.PLAIN, 30));              
			g.drawString("Press Any Arrow Key To Start", 400 - 200, 380); 
		}   

		if (superFood)
		{ 
			//Tell ther user to press any arrow to start the game
			g.setFont(new Font("Aharoni", Font.PLAIN, 15));              
			g.drawString("Length Of SuperFood: " + food.getAddedLength(), 300, 25); 
		}

		//If the player has crashed, show their score, and tell them game over
		if (crash)
		{

			g.setFont(new Font("Aharoni", Font.PLAIN, 40));              
			g.drawString("Game Over", 400 - 100, 200); 
			g.setFont(new Font("Aharoni", Font.PLAIN, 30)); 
			g.drawString("Your Score: " + score, 400 - 85, 260); 
		}
	}

	//Key Listener
	public void keyPressed(KeyEvent e)
	{ 
		//Get the keycode of the key pressed
		int i = e.getKeyCode();

		//If teh user pressed the left key, and the snake is not already moving right and the flag is false
		if (i == KeyEvent.VK_LEFT && dir!= "RIGHT" && flag == false)
		{
			//Set the direction to LEFT
			dir = "LEFT"; 
		}

		//If the right arrow is pressed
		else if (i == KeyEvent.VK_RIGHT && dir!="LEFT" && flag == false)
		{
			dir = "RIGHT"; 
		}

		//If the up arrow is pressed
		else if (i == KeyEvent.VK_UP && dir!= "DOWN" && flag == false)
		{
			dir = "UP"; 
		}

		//If the down arrow is pressed
		else if (i == KeyEvent.VK_DOWN && dir!= "UP" && flag == false)
		{
			dir = "DOWN"; 
		}

		//If the user presses P to pause the game
		else if (i == KeyEvent.VK_P)
		{ 
			//If pause is already true
			if (pause == true && crash != true)
			{ 
				//Unpause the game
				pause = false; 
				pauseMenu (pause); 
			}

			//If game is not already paused
			else if (pause == false && crash!= true)
			{ 
				//Pause it
				pause = true; 
				pauseMenu(pause); 
			}
		}

		//Set the flag which doesnt allow two buttons to be selected at once to true
		flag = true; 
	}


	//Method called to show or not show the menu
	public void pauseMenu(boolean show)
	{   
		btnResume.setVisible(show);
		btnQuit.setVisible(show);
	}

	//Method to show, or not show the play again menu
	public void playAgainMenu(boolean show)
	{   
		btnPlayAgain .setVisible(show);
		btnQuit.setVisible(show);

		//Whenever this method is called, var crash is set to true
		if (show)
		{ 
			crash = true; 
			
			//Only updates highscore if current score is better than last
			if (this.highscore < this.score)
			{
				this.highscore = this.score; 
			}
		} 
	}

	//Self testing main
	public static void main(String[] args) 
	{
		//Throughly play game to test all the methods
		new SnakeGame(50, Color.GREEN, Color.PINK); 
	}

	//Invole key presses
	public void keyReleased(KeyEvent x)
	{
	}

	public void keyTyped(KeyEvent y)
	{
	}

	//Get the snake's score
	public int getSnakeScore() 
	{
		return highscore;
	}

	//Get the speed 
	public int getSpeed() {
		return speed;
	}

	//Set the speed
	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}
	
	
	

}
