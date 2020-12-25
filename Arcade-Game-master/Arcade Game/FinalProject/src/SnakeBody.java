/*
 * Author: Gurbir Bains 
 * Date: January 25. 2016 
 * Description: Sets location, colour and scale of the snake. Checks if snake hits the edge of 
 * the window. Creates two different green snake bodies and ouputs them in a frame at two different
 * locations. 
 * Method List: public SnakeBody() - constructor that sets location of snake body at (5,5)
 * 			    public SnakeBody(int x, int y, Color col) - overloads constructor using x and y
 * 															as the location, sets colour and sets 
 * 															scale to 20
 * 				public void setBodyLocation(int x, int y) - sets the location of body 
 * 				public int getBodyX() - gets x coordinate of body 
 * 				public int getBodyY() - gets y coordinate of body
 * 				public Color getColor() - gets the colour of the snake 
 * 				public void paint(Graphic g) - paints the snake and scales it 
 * 				public boolean checkCrash() - checks if snake goes outside the window, if so 
 * 				it will return true, if not returns false
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class SnakeBody extends JComponent
{
	//Private variables for the location and colour of the snake body
	private Point location; 
	private Color col; 
	private int scale;  

	//Default constructor spawns the body at 5,5
	public SnakeBody() 
	{
		location =  new Point(5, 5); 
	}

	//Overloading snake body method, spawns it at x and y
	public SnakeBody(int x, int y, Color col)
	{ 
		setPreferredSize(new Dimension(782, 738));
		location =  new Point(x, y);// sets location  
		this.col = col; 
		this.scale = 20; //sets scale to 20 
		repaint(); 
	}

	//Getter method for the x location
	public void setBodyLocation (int x, int y)
	{
		this.location = new Point(x, y); 
	}

	//Getter method for the x location
	public int getBodyX ()
	{
		return this.location.x; 
	}

	//Getter method for the y location
	public int getBodyY()
	{ 
		return this.location.y; 
	}

	//Getter method for the colour of the snake
	public Color getColor()
	{ 
		return this.col; 
	}

	public void paint (Graphics g)
	{ 
		//Paint the body and also scale it by 20
		g.setColor(getColor());
		g.fillRect(getBodyX()*scale, getBodyY()*scale, scale, scale);

		g.setColor(Color.WHITE);//sets colour to white 
		g.drawRect(getBodyX()*scale, getBodyY()*scale, scale, scale);
	} 

	//Collision to check if the snake has hit the edge of the window
	public boolean checkCrash()
	{ 
		//If the heads X or Y exceeds the window 
		if (getBodyX() > 38 || getBodyY() > 33 || getBodyX() < 0 || getBodyY() < 0)//the out of bounds 
		{  
			return true; //if snake body exceed window returns true
		}
		return false; //if not returns false 
	}

	//Self testing main
	public static void main(String[] args) 
	{
		//creating frame 
		JFrame f = new JFrame("SnakeBody Testing");
		JPanel p = new JPanel(); 
		p.setLayout(new OverlayLayout(p)); 

		//Creates two snakes at tow different locations
		SnakeBody body = new SnakeBody(5, 5, Color.GREEN);  
		SnakeBody body2 = new SnakeBody(12, 5, Color.GREEN); 

		//Add snake to frame and set frame as visible
		f.setSize(800, 720);
		p.add(body2); 
		p.add(body);
		f.add(p); 
		f.setVisible(true); 
	}

}
