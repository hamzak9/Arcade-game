import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

/* Author : Hamza Khan
 * Date : January 2017
 * BirdXL Class
 * Description : This class is a child class of Bird class and allows the user to play BainsyBird in two game modes by overriding methods from the Bird Class.
 * Methods list : Jump()     -- Allows bird to jump in opposite direction by setting velocity to -3
 *               : fall() -- allows bird to fall
 *               :setY(int y) -- setter method for y location, takes in y and sets it to that
 *               :getY() -- getter method for y location, gets y location.
 * 
 * 
 * 
 */
public class BirdXL extends Bird {

	Bird bird;  //declaring variables
	private static final int G = -9;
	private int velocity = -3; 

	public BirdXL() {
		super(); // calls parent function, calls bird
	}

	public static void main(String[] args) {
		
		ImageIcon birde = new ImageIcon("properbird.png"); // setting bird imageicon to flappy 
		
		new BainsyBird(birde,2); // creating bainsybird game using birdXl object to test methods 
		
		

	}

	public void jump() // overriding jump method from bird class
	{
		startTime = System.currentTimeMillis();  // get starting time in milliseconds, calculate elapsed time and set velocity to -3 instead of 3 
		elapsedTime = endTime - startTime; 
		velocity = -3; 
	}


	public void fall() // overriding fall method from bird class
	{
		elapsedTime = endTime - startTime;  // gets elapsed time, 
		update();  // gets new end time

		int speed = (int)(((int)(G*-1)*elapsedTime)/1000 + velocity);  // calculates speed by multiplying gravity by -1 instead of 1
		setY(loc.y - speed);  
	}

	public void setY(int y) // setter method for y location. 
	{
		this.loc.y = y;
	}

	public int getY() // getter method for y location
	{
		return this.loc.y;
	}




}


