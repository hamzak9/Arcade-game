import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.ImageIcon;


/*Author : Hamza Khan
 * Date : January 2017
 * Bird Class
 * Description : This class is a parent class of BirdXL class and allows the user to play BainsyBird in two game modes creating Bird and BirdXL objects
 * Methods list : Jump()     -- Allows bird to jump in opposite direction by setting velocity to -3
 *               : fall() -- allows bird to fall normally 
 *               :setY(int y) -- setter method for y location, takes in y and sets it to that
 *               :getY() -- getter method for y location, gets y location.
 *               :  public static void main(String[] args) --self testing main
 * 				:public void update() // updates current time
 * 				: public void setX(int x) // setter method for x location
				:public int getX() // getter method for x location


 * 
 * 
 */



public class Bird
{
	private static final int G = -9;
	private int velocity = 0; 
	public long startTime, endTime, elapsedTime;  
	public Point loc;

	public Bird()
	{  
		loc = new Point (3,100); 
		startTime = System.currentTimeMillis(); 
	} 

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis(); // gets starttime at the current time
		JOptionPane.showMessageDialog(null, "Wait"); // displays wait message
		long endtime = System.currentTimeMillis(); //gets end time after you close joptionPane
		long elapsedTime = endtime-startTime; // calculates elapsedtime after subtracting endtime and start time and prints elapsedtime
		// the longer you take to close joption,elapsedtime should be longer.
		System.out.println(elapsedTime);

	}


	public void jump() // jump method
	{
		startTime = System.currentTimeMillis(); //gets new start time 
		elapsedTime = endTime - startTime;  // calculates elapsed time and sets velocity to 3.
		velocity = 3;  // sets velocity to 3
	}

	public void fall() // fall method
	{
		elapsedTime = endTime - startTime;  // calculating elapsed time
		update();  // calculating new endtime

		int speed = (int)(((int)(G)*elapsedTime)/1000 + velocity); // calculating speed , setting new y location by subtracting the speed calculated
		setY(loc.y - speed); 
	}



	public void update() // update class
	{
		endTime = System.currentTimeMillis(); //gets new end time
	}




	public void setX(int x) // setter method for x location
	{
		this.loc.x = x;
	}

	public int getX() // getter method for x location
	{
		return this.loc.x;
	}

	public void setY(int y) //setter method for y location
	{
		this.loc.y = y;
	}

	public int getY() // getter method for y location
	{
		return this.loc.y;
	}



}