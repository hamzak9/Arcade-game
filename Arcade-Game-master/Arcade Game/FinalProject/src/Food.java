/*
 * Author: Gurbir Bains 
 * Date: January 25. 2016 
 * Description: Creates a food for a snake with a random location, length, colour and scale. 
 * Method List: public Food() - constructor that spawns a food at a random location using x and y 
 * 				public Food(Color col) - overloads constructor 
 * 				public int getAddedLength() - gets length that food will add
 * 				public int getFoodX() - get x location of food 
 * 				public int getFoodY() - gets y location of food
 * 				public void setX(int x) - sets x location of food
 * 				public void setY(int y) - sets y location of food 
 * 				public Color getCol() - gets colour of the food 
 * 				public Color setCol(Color col) - sets colour of the food 
 * 				public void paint(Graphics g) - paints the food and scales it 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Food extends JComponent
{
	//Private variables for the location, length and colour of the food
	private Point location;
	private int addedLength;  
	private Color col; 
	private int scale; 

	//Default constructor spawns food at random location
	public Food() 
	{
		setPreferredSize(new Dimension(782, 738));
		this.scale = 20; 
		this.col = Color.GREEN;
		this.addedLength = 1; 
		//x and y are set to random locations in the window 
		int x = (int)(Math.random() * 35 + 0);
		int y = (int)(Math.random() * 32 + 0);
		location =  new Point(x, y); //creates new point at this location 
	}

	//Overloading food method, spawns it at certain x and y
	public Food(Color col) 
	{
		setPreferredSize(new Dimension(782, 738));
		this.scale = 20; 
		this.col = col;
		this.addedLength = 1; 

		int x = (int)(Math.random() * 35 + 0);
		int y = (int)(Math.random() * 32 + 0);
		location =  new Point(x, y); 
	}

	//Getter method for the length food will add
	public int getAddedLength() 
	{
		return addedLength;
	}

	//Getter method for the X of the food
	public int getFoodX() 
	{
		return location.x;
	}

	//Setter method for the X location
	public void setX(int x) 
	{
		location.x = x;
	}

	//Getter method for the Y of the food
	public int getFoodY() 
	{
		return location.y;
	}

	//Setter method for the Y location
	public void setY(int y) 
	{
		location.y = y;
	}


	//Getter method for the colour of the food
	public Color getCol() {
		return this.col;
	}

	//Setter method for the colour of the food
	public void setCol(Color col) 
	{
		this.col = col;
	}

	public void paint (Graphics g)
	{ 
		//Paint the food and also scale it by 20
		g.setColor(getCol());
		g.fillRect(getFoodX()*scale, getFoodY()*scale, scale, scale);
	} 

	//Self testing main
	public static void main(String[] args) 
	{
		//creates frame 
		JFrame f = new JFrame("Food Testing");
		JPanel p = new JPanel(); 

		//Creates the food at a random location
		Food food = new Food();  

		//Add food to frame and set frame as visible
		f.setSize(800, 800);
		p.add(food);
		f.add(p); 
		f.setVisible(true); 
	}

}
