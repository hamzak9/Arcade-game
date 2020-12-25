import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;

/*
 * Author: Richard Persaud
 * Date: January, 2016
 * Description: This class is the pipe class for the Bainsy bird game, it has private variables for the location,
 * size and to randomize the height of the pipe.
 * 
 */

public class Pipe 
{
	//Private variables for the x, y, length and random of the pipe height
	private  int x;
	private int length;
	private int length2; 
	private int randomy;
	private int randomy2;

	public Pipe() 
	//Default constructor randomizes the height of the pipe
	{        
		//Generates a random length of the pipe
		length = (int)(Math.random() *(300 - 100) + 1)+100; 
		length2 = 500 - length - 100; 

		//Starting x location of the pipe
		x = 900; 

		//sets random y point of painting the pipe
		randomy = (int)(Math.random()*(300-120)+1)+100;
		randomy=randomy*-1;
		randomy2 = 500-randomy-150;

	}
	public int getRandomy2(){ 
		//getter method to get the random length
		return randomy2;
	}

	public int getRandomy(){ 
		//getter method to get the random length

		return randomy;
	}


	public int getX() { 
		//getter method to get the x value 
		return x;

	}

	public void setX(int x) {
		//setter Method to set x
		this.x = x;
	}

	public int getLength() { 
		//getter method to get the length
		return length;

	}

	public void setLength(int length) 
	//Setter method to set the length
	{
		this.length = length;
	}

	public int getLength2() { 
		//getter method to get the length
		return length2;
	}

	public void setLength2(int length2) {
		//Setter method to set the length
		this.length2 = length2;
	}
	public static void main(String[] args) 
	//Self Testing Main
	{
		//Create a pipe object
		Pipe p = new Pipe(); 

		//Print the randomized lenghts of the pipe
		System.out.println(p.getLength());
		System.out.println(p.getLength2());

		//Print the randomized lenghts of the pipe
		System.out.println(p.getRandomy());
		System.out.println(p.getRandomy2());

	}



}