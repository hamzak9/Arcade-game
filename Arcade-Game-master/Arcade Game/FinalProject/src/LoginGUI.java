import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.*; 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * Author: Shahzada Gulfam
 * Date: January, 2016
 * Description: This class is the login screen, in which the user has to input the correct
 * 				LoginID and Password to gain access to the MainGUI. The user is also able to create
 * 				an account if he does not already have one.
 * 
 * Method List: public void load (String fileName) - Loads the old data from a file, and saves name into the Highscores List
 * 				public static void main(String[] args) - Main method that is run
 * 				public void actionPerformed(ActionEvent e) - Called when action is performed
 * 				public boolean pinToInt () - returns true if the PIN is made of only numbers
 * 				public void accountUI (boolean show) - Can show or not show account UI
 * 				public void loginUI (boolean show) - Can show or not show Login UI
 * 
 * 				
 * 
 */

public class LoginGUI extends JFrame implements ActionListener
{
	//Private variables for the GUI
	private JLabel title, background, userNameTitle, passwordTitle, lblEnterName, lblEnterPin; 
	private JTextField userName; 
	private JPasswordField password; 
	private JButton btnLogin, btnClear, btnCreateAccConfirm, btnCreateAccClose, btnExit; 
	private JButton btnCreateAccount; 
	private JTextField enterName;
	private JTextField enterPin;
	private String fileName = "data.txt"; 

	//Object of a list for the Login
	private HighscoresList list; 

	public LoginGUI()
	{ 
		//Title of the Frame
		super("Login");

		//For getting the screen dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int) screenSize.getHeight();

		//Using no certain layout
		getContentPane().setLayout(null);

		//Procedure for embedding images for JAR file
		try 
		{ 
			background = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("createAccountBG.jpg"))));
		}
		catch (Exception e)
		{ 
		}

		//Initializing GUI variables to be used
		title = new JLabel ("Bainsy Arcade"); 
		userName = new JTextField("");
		password = new JPasswordField (""); 
		userNameTitle = new JLabel ("User Name"); 
		passwordTitle = new JLabel ("Password"); 
		btnLogin = new JButton ("Login"); 
		btnClear = new JButton ("Clear"); 

		//Setting the size and font of the titles
		title.setFont(new Font("Century Gothic", Font.PLAIN, 50)); 
		title.setForeground(Color.WHITE);

		//Setting the bounds of all the GUI components
		title.setBounds(220, 0, 800, 150);
		background.setBounds(0,0, 800, 800);
		userName.setBounds(352, 276, 150, 50);
		userNameTitle.setBounds(272,291,100,20);
		password.setBounds(352, 336, 150, 50);
		passwordTitle.setBounds(272,351,100,20);
		btnLogin.setBounds(272, 406, 115, 40); 
		btnClear.setBounds(402, 406, 100, 40); 

		//Setting the foreground/background of buttons and text 
		userNameTitle.setForeground(Color.WHITE);
		passwordTitle.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.WHITE);
		btnClear.setBackground(Color.WHITE);

		//Adding the GUI elements to the Frame
		getContentPane().add(title);
		getContentPane().add (userName); 
		getContentPane().add (userNameTitle); 
		getContentPane().add (password); 
		getContentPane().add (passwordTitle); 
		getContentPane().add (btnLogin); 
		getContentPane().add (btnClear); 

		//Removing the focus box around the button for a cleaner look
		btnLogin.setFocusPainted(false);
		btnClear.setFocusPainted(false);

		//Create account button 
		btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setFocusPainted(false);
		btnCreateAccount.setBackground(Color.WHITE);
		btnCreateAccount.setBounds(27, 23, 141, 40);
		getContentPane().add(btnCreateAccount);

		//Field for entering name, when creating account
		enterName = new JTextField();
		enterName.setBounds(286, 245, 185, 20);
		getContentPane().add(enterName);
		enterName.setColumns(10);
		enterName.setVisible(false);

		//Field for entering PIN when creating account
		enterPin = new JTextField();
		enterPin.setBounds(286, 327, 185, 22);
		getContentPane().add(enterPin);
		enterPin.setColumns(10);
		enterPin.setVisible(false);

		//Title for entering name
		lblEnterName = new JLabel("Enter Your Name");
		lblEnterName.setForeground(Color.WHITE);
		lblEnterName.setBounds(286, 223, 129, 16);
		getContentPane().add(lblEnterName);
		lblEnterName.setVisible(false);

		//Title for entering PIN
		lblEnterPin = new JLabel("Enter Your Pin");
		lblEnterPin.setForeground(Color.WHITE);
		lblEnterPin.setBounds(286, 304, 129, 16);
		getContentPane().add(lblEnterPin);
		lblEnterPin.setVisible(false);

		//Confrim button, to create and finalize account
		btnCreateAccConfirm = new JButton("Confirm");
		btnCreateAccConfirm.setBackground(Color.WHITE);
		btnCreateAccConfirm.setBounds(282, 387, 97, 25);
		getContentPane().add(btnCreateAccConfirm);
		btnCreateAccConfirm.setVisible(false);

		//To close the create an account menu
		btnCreateAccClose = new JButton("Close");
		btnCreateAccClose.setBackground(Color.WHITE);
		btnCreateAccClose.setBounds(394, 387, 97, 25);
		btnCreateAccClose.setVisible(false);

		btnExit = new JButton("Exit");
		btnExit.setBackground(Color.WHITE);
		btnExit.setBounds(656, 627, 97, 25);
		getContentPane().add(btnExit);

		//Add background and button to close
		getContentPane().add(btnCreateAccClose);
		getContentPane().add(background); 


		//Setting the size, and location of the frame
		setSize(800,720);
		setLocation (width/2 - 400, height/2 - 390); 
		setVisible(true);
		setResizable (false); 

		//Action listeners for button
		btnLogin.addActionListener(this);
		btnCreateAccount.addActionListener(this);
		btnClear.addActionListener(this);
		btnCreateAccClose.addActionListener(this);
		btnCreateAccConfirm.addActionListener(this);
		btnExit.addActionListener(this);

		//Initialize list object
		list = new HighscoresList(); 

		//Call load method, to save info onto the list
		load("data.txt"); 

	}

	//Class that is run
	public static void main(String[] args) 
	{
		new LoginGUI(); 
	}

	//If any button is pressed
	public void actionPerformed(ActionEvent e)
	{ 
		//If the login button is pressed
		if (e.getSource() == btnLogin)
		{ 
			//If the login entry is correct
			if (list.checkExisiting(userName.getText()))
			{ 
				//Find the name of the user in the list
				int where = list.binarySearch(userName.getText());
				int pin  = -1; 

				//Try to convert password to int, to see if in correct format
				try 
				{ 
					pin = Integer.parseInt(password.getText()); 
				} 

				//As it is a password, there will be no message to display they have entered incorrectly
				catch (NumberFormatException e3)
				{ 
				}

				//If the pin and username match up
				if (list.getPin(where) == pin)
				{ 
					//Allow access to the main GUI, passing in the name of the USER and the LIST
					new MainGUI(list, userName.getText(), fileName); 

					//Empty the userName and password entries
					userName.setText("");
					password.setText("");
				} 
				else 
				{ 
					//User friendly message 
					JOptionPane.showMessageDialog(null, "Incorrect PIN");
				}
			}
			//If the login entry is incorrect
			else 
			{ 
				//User friendly message 
				JOptionPane.showMessageDialog(null, "Sorry user name does not exist");
			}
		}
		//If the clear button is pressed
		else if (e.getSource() == btnClear)
		{ 
			//Empty the userName and password entries
			userName.setText("");
			password.setText("");
		}

		//If the user clicks create account button
		else if (e.getSource() == btnCreateAccount)
		{ 
			//Show the create an account UI
			accountUI(true); 
		}

		//If the user closes the create account UI
		else if (e.getSource() == btnCreateAccClose)
		{ 
			//Dont show account UI, show the Login UI
			accountUI(false); 
			loginUI(true); 
		}

		//If the user closes the login GUI
		else if (e.getSource() == btnExit)
		{ 
			System.exit(0);
		}

		//If the user confirms to create an account
		else if (e.getSource() == btnCreateAccConfirm)
		{ 
			//Check if the fields are empty
			if (enterPin.getText().equals("") || enterName.getText().equals(""))
			{ 
				//If so, show a user friendly message
				JOptionPane.showMessageDialog(null, "Missing fields");
			}

			//Check if the length of the pin is no 4
			else if (enterPin.getText().length() != 4 && enterPin.getText().length() != 0)
			{ 
				JOptionPane.showMessageDialog(null, "Pin needs to be 4 numbers long");
			}

			//If the length is 4, and no fiels are empty, check if they are all numbers
			else if (enterPin.getText().length() == 4 && !pinToInt())
			{ 
				JOptionPane.showMessageDialog(null, "Pin needs to be numbers only");
			}

			//If the user has correctly entered all the field
			else 
			{ 
				//Create a record of the user information
				String record = enterName.getText() + "," + enterPin.getText() + "," + "0" + "," + "0"; 

				//Create a highscore record object and process it
				HighscoreRecord hsInfo = new HighscoreRecord(); 
				hsInfo.processRecord(record);

				if (!list.insert (hsInfo))// if the record isnt added 
				{
					JOptionPane.showMessageDialog (null, "Unable to register, check readME for more details");
				}

				//If correctly inserted to the list
				else 
				{ 
					//User friendly message and update the data file
					JOptionPane.showMessageDialog(null, "Success");

					//Dont show account UI, show the Login UI
					accountUI(false); 
					loginUI(true);

					list.writeToFile(fileName); 
				}
			}

		}
	}

	//Method to wrap pin to an integer
	public boolean pinToInt ()
	{ 
		//Try to convert string to int
		try
		{
			int pin = Integer.parseInt(enterPin.getText()); 
		} 

		//If error, return false
		catch (NumberFormatException e2)
		{ 
			return false; 
		}
		return true; 
	}

	//All GUI components within AccountUI, either can be shown or not shown
	public void accountUI (boolean show)
	{ 
		loginUI(false); 
		enterName.setVisible(show);
		enterPin.setVisible(show);
		btnCreateAccConfirm.setVisible(show);
		btnCreateAccClose.setVisible(show);
		lblEnterName.setVisible(show);
		lblEnterPin.setVisible(show);
	} 

	//All GUI components within LoginUI, either can be shown or not shown
	public void loginUI (boolean show)
	{
		userName.setVisible(show); 
		userNameTitle.setVisible(show);
		password.setVisible(show);
		passwordTitle.setVisible(show);
		btnLogin.setVisible(show);
		btnClear.setVisible(show); 
		btnCreateAccount.setVisible(show);
		btnExit.setVisible(show);

	}

	//If the user wants to save the data
	public void load (String fileName)
	{ 
		String [] records = list.fileLoader(fileName);
		//list.setMaxSize(records.length);
		HighscoreRecord newList [] = new HighscoreRecord[list.getMaxSize()];
		list.setRecordList(newList);
		list.setSize(0);  //reset size to empty before filling the list.

		for (int i = 0; i < records.length; i++)
		{
			// create object
			HighscoreRecord hsInfo = new HighscoreRecord ();
			hsInfo.processRecord(records[i]);
			list.insert(hsInfo);
		}
	}
} 
