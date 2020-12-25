import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

/*
 * Author: Shahzada Gulfam
 * Date: January, 2016
 * Description: This class is a child of the food class. The super food class overrides, the get colour method
 * 				to return a random colour everytime, and create a flashing pattern. The super food also creates
 * 				a random added length, the getter method of this class also overrides the food getter method. 
 * 
 * Method List:  public SuperFood() - Constructor method, calls parents function and makes a new added length
 * 				 public Color getCol() - Overrides, getCol() of parents, return new colour everytime
 * 				 public int getAddedLength() - Overrides, getAddedLengh of parents, returns random number
 */


public class SuperFood extends Food
{
	//Private variable for the colour of the super food
	private Color col; 
	private int addedLength;
	private int scale; 

	//Default constructor 
	public SuperFood() 
	{
		//Calls parent 
		super(); 
		this.scale = 20; 
		//Functionality is a random length, could be better or worse
		addedLength = (int)(Math.random() * 15 + 0); 
	}

	//Get colour method overrides get colour in parent 
	public Color getCol() 
	{
		//Creates random R,G,B, so whenver colour is returned a new colour will form (flashing)
		int r = (int)(Math.random() * 256+ 0);
		int g = (int)(Math.random() * 256+ 0);
		int b = (int)(Math.random() * 256+ 0);

		//Create a new colour based on random rgb
		col = new Color (r,g,b); 

		//return the colour
		return col;
	}

	//Getter method for the length food will add
	public int getAddedLength() 
	{
		return addedLength;
	}

	//Self testing main
	public static void main(String[] args) 
	{
		JFrame f = new JFrame("Food Testing");

		//Creates the food at a random location
		SuperFood sFood = new SuperFood(); 

		//Add food to frame and set frame as visible
		f.setSize(800, 800);
		f.add(sFood);
		f.setVisible(true);

		System.out.println(sFood.getAddedLength());

	}

}
