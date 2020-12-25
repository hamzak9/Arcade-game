
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;

/*
 * Author: Richard Persaud
 * Date: January, 2016
 * Description: This class is the GUI for the main menu. It allows the user to select a game, either snake or
 * 				Bainsy Bird. Also along with the game selection the admin has a settings option, the users can
 *  			check the high scores and also allows to logout. 
 * 
 */
public class MainGUI implements ActionListener{

	//Has a snake game, bird game and a list 
	private SnakeGUI snakeGame; 
	private BirdGUI birdGame; 
	private int snakeHS = 0, birdHS = 0; 
	private HighscoresList list; 
	private JList list_1; 
	private DefaultListModel listModel, listModelHS; 

	//GUI Elements 
	private JButton btnHighscore, btnSettings, btnDelete, btnClose, btnSnakeStart, btnFlappyStart; 
	private JFrame frmArcade;
	private String player = "", fileName = ""; 
	private JLabel settingsBG, lblSettingsTitle, hsBG, hsTitle, playSnake, lblNewLabel, background, playFlappy, lblPlayer,dSnake,dFlappy; 
	private JTextField textField;
	private JButton btnLogout;
	private JList hsList; 
	private JButton btnSort;
	private JButton btnSort2;
	private JButton btnCloseHS;

	//private static HighscoresList list2;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{ 
		HighscoresList list2 = new HighscoresList(); 

		String fileName = "Test.txt"; 
		new MainGUI(list2, "Bains", fileName); 
	}

	/**
	 * Creates the application
	 * @param list
	 * @param player
	 */
	public MainGUI(HighscoresList list, String player, String fileName) 
	{
		this.list = list; 
		this.player = player;
		this.fileName = fileName; ;

		//For getting the screen dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int) screenSize.getHeight();


		//Procedure for embedding images for JAR file
		try 
		{ 
			settingsBG = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("createAccountBG.jpg"))));
			playSnake = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("playSnake.png"))));
			playFlappy = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("playFlappy.png"))));
			hsBG = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("hsBG.png"))));
			background = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("mainBG.png"))));	
			lblNewLabel = new JLabel (new ImageIcon (ImageIO.read(getClass().getResource("maintitle.png"))));	
			dSnake = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("orangetext.png"))));
			dFlappy = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("greentext.png"))));

		}
		catch (Exception e)
		{ 
		}



		//Creates the Frame for the GUI
		frmArcade = new JFrame();
		frmArcade.setResizable(false);
		frmArcade.setTitle("Arcade");
		frmArcade.getContentPane().setEnabled(false);
		frmArcade.setSize(800,720); //Setting the size, and location of the frame
		frmArcade.setLocation (width/2 - 400, height/2 - 390);
		frmArcade.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmArcade.getContentPane().setLayout(null);

		//Creates label buttons
		lblNewLabel.setBounds(-100, 40, 961, 84);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		frmArcade.getContentPane().add(lblNewLabel);

		//Setting the labels
		lblSettingsTitle = new JLabel("Admin Settings");
		lblSettingsTitle.setBounds(148, 40, 476, 20);
		lblSettingsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettingsTitle.setForeground(Color.GREEN);
		lblSettingsTitle.setBackground(Color.WHITE);
		lblSettingsTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblSettingsTitle.setVisible(false);
		
		
		dFlappy.setBounds(500, 300, 231, 120);
		frmArcade.getContentPane().add(dFlappy);
		
		dSnake.setBounds(50, 300, 231, 120);
		frmArcade.getContentPane().add(dSnake);
		//Add a desription for the snake game

		// option to play snake game
		playSnake.setBounds(50, 231, 280, 59);
		frmArcade.getContentPane().add(playSnake);

		//option to play bainsy bird
		playFlappy.setBounds(500,231,280,54);
		frmArcade.getContentPane().add(playFlappy);

		//adding delete buttons
		btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setBounds(239, 476, 97, 25);
		btnDelete.setVisible(false);

		//adding close button 
		btnClose = new JButton("Close");
		btnClose.setBackground(Color.WHITE);
		btnClose.setBounds(440, 476, 97, 25);
		btnClose.setVisible(false);

		// creates a list
		listModel = new DefaultListModel();
		list_1 = new JList(listModel);
		list_1.setFont(new Font("Century Gothic", Font.PLAIN, 15)); 
		list_1.setBounds(237, 185, 300, 263);
		frmArcade.getContentPane().add(list_1);
		list_1.setVisible(false);

		// creates a leader board
		hsTitle = new JLabel("Leaderboads");
		hsTitle.setBounds(148, 40, 476, 20);
		hsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		hsTitle.setForeground(Color.GREEN);
		hsTitle.setBackground(Color.WHITE);
		hsTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		hsTitle.setVisible(false);

		//list for high score
		listModelHS = new DefaultListModel();
		hsList = new JList(listModelHS);
		hsList.setFont(new Font("Century Gothic", Font.PLAIN, 15)); 
		hsList.setBounds(158, 83, 476, 482);
		frmArcade.getContentPane().add(hsList);
		hsList.setVisible(false);

		// button to sort snakes high score
		btnSort = new JButton("Sort by Snake");
		btnSort.setBackground(Color.WHITE);
		btnSort.setBounds(209, 594, 127, 25);
		frmArcade.getContentPane().add(btnSort);
		btnSort.setVisible(false);

		// button to sort baisny high score
		btnSort2 = new JButton("Sort by Flappy");
		btnSort2.setBackground(Color.WHITE);
		btnSort2.setBounds(362, 594, 118, 25);
		frmArcade.getContentPane().add(btnSort2);
		btnSort2.setVisible(false);

		//Close button for highscores 
		btnCloseHS = new JButton("Close");
		btnCloseHS.setBackground(Color.WHITE);
		btnCloseHS.setBounds(506, 594, 70, 25);
		frmArcade.getContentPane().add(btnCloseHS);
		btnCloseHS.setVisible(false);

		//button to start snake game
		btnSnakeStart = new JButton();
		btnSnakeStart.setBounds(50, 231, 280, 59);
		btnSnakeStart.setOpaque(false);			// make button invisible but still clickable
		btnSnakeStart.setContentAreaFilled(false);
		btnSnakeStart.setBorderPainted(false);
		frmArcade.getContentPane().add(btnSnakeStart);	

		//Button to start BainsyBird
		btnFlappyStart = new JButton();
		btnFlappyStart.setBounds(500, 231, 280, 54);
		btnFlappyStart.setOpaque(false);
		btnFlappyStart.setContentAreaFilled(false);
		btnFlappyStart.setBorderPainted(false);
		frmArcade.getContentPane().add(btnFlappyStart);

		//High scores picture 
		hsBG.setBackground(Color.WHITE);
		hsBG.setBounds(0, 0, 800, 785);
		settingsBG.setBounds(0, 0, 800, 785);
		hsBG.setVisible(false);

		//adding to frame
		frmArcade.getContentPane().add(hsTitle);
		frmArcade.getContentPane().add(hsBG);
		frmArcade.getContentPane().add(btnDelete);
		frmArcade.getContentPane().add(btnClose);
		frmArcade.getContentPane().add(lblSettingsTitle);
		frmArcade.getContentPane().add(settingsBG);
		settingsBG.setVisible(false);

		//label select a game
		JLabel lblSelectAGame = new JLabel("SELECT A GAME");
		lblSelectAGame.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblSelectAGame.setForeground(Color.CYAN);
		lblSelectAGame.setBackground(Color.CYAN);
		lblSelectAGame.setBounds(293, 154, 187, 20);
		frmArcade.getContentPane().add(lblSelectAGame);

		//High scores button
		btnHighscore = new JButton("Highscore");
		btnHighscore.addActionListener(this);
		btnHighscore.setBackground(Color.WHITE);
		btnHighscore.setBounds(655, 594, 97, 25);
		frmArcade.getContentPane().add(btnHighscore);

		//Settings button
		btnSettings = new JButton("Settings");
		btnSettings.setBackground(Color.WHITE);
		btnSettings.setBounds(655, 514, 97, 25);
		frmArcade.getContentPane().add(btnSettings);

		//logout button
		btnLogout = new JButton("Logout");
		btnLogout.setBackground(Color.WHITE);
		btnLogout.setBounds(655, 556, 97, 25);
		frmArcade.getContentPane().add(btnLogout);

		//welcome player label
		lblPlayer = new JLabel ("Welcome: " + player); 
		lblPlayer.setForeground(Color.WHITE);
		lblPlayer.setBounds(10, 10, 800, 15); 
		frmArcade.getContentPane().add(lblPlayer);

		//background for the main
		background.setBounds(0, 0, 800, 720);
		frmArcade.getContentPane().add(background);


		//cant see unless admin
		btnSettings.setVisible(false);

		//admin login to see settings 
		if(this.player.equals("Bains"))
		{ 
			btnSettings.setVisible(true); 
		}
		//Adding action listeners to the buttons and frame
		btnSettings.addActionListener(this);
		btnFlappyStart.addActionListener(this);
		btnSnakeStart.addActionListener(this);
		btnClose.addActionListener(this);
		btnDelete.addActionListener(this);
		btnLogout.addActionListener(this);
		btnCloseHS.addActionListener(this);
		btnSort.addActionListener(this);
		btnSort2.addActionListener(this);
		frmArcade.setVisible(true);
	}
	// updates high Score 
	public void updateHighscores()
	{
		if (snakeGame != null)
		{ 
			if (snakeHS < snakeGame.getSnakeScore()) //checks for higher score 
			{ 
				this.snakeHS = snakeGame.getSnakeScore();//gets the snake score 
				int where = list.binarySearch(player); //searches for player
				list.getRecordList(where).setSnakeScore(snakeGame.getSnakeScore()); //places the player and score
			} 
		} 

		if (birdGame != null)
		{ 
			if (birdHS < birdGame.getFlappyScore())//checks for higher score 
			{ 
				this.birdHS = birdGame.getFlappyScore();//gets the snake score
				int where = list.binarySearch(player);//searches for player
				list.getRecordList(where).setFlappyScore(birdGame.getFlappyScore());//places the player and score

			} 
		}

		list.writeToFile(fileName);  //writes to file data
	}

	public void actionPerformed(ActionEvent e) 
	//action performed method
	{
		if (e.getSource() == btnHighscore)
		{
			//highscore is displayed 
			updateHighscores();
			highscoreMenu(true);
			settingsMenu(false); 
			mainMenu (false); 
			updateList(listModelHS); 
		}


		else if (e.getSource() == btnSnakeStart)
		{ 
			//snake game is ran 
			highscoreMenu(false);
			settingsMenu(false); 
			mainMenu (true);
			snakeGame = new SnakeGUI(); 
		} 
		else if (e.getSource() == btnFlappyStart)
		{ 
			// Baisny bird is ran
			highscoreMenu(false);
			settingsMenu(false); 
			mainMenu (true);
			birdGame  = new BirdGUI();

		}

		else if (e.getSource() == btnSettings)
		{ 
			//settings is displayed 
			updateHighscores(); 
			list.insertSort(); //sorts alphabetically
			updateList(listModel); 
			highscoreMenu (false); 
			mainMenu(false); 
			settingsMenu (true);
		}

		else if (e.getSource() == btnClose)
		{  
			//goes back to the main menu
			highscoreMenu (false); 
			mainMenu(true); 
			settingsMenu (false);
		}

		else if (e.getSource() == btnCloseHS)
		{ 
			//if highscores is closed back to main menu
			highscoreMenu (false); 
			mainMenu(true); 
			settingsMenu (false);
		}

		else if (e.getSource() == btnDelete)
		{ 
			//Gets the index of what customer is selected on the JList
			int index = -1; 
			index = list_1.getSelectedIndex();

			if (index > 0)
			{ 
				listModel.remove(index); 
				list.delete(list.getRecordList(index).getUserName()); 
				list.writeToFile(fileName); 
				updateList(listModel); 
			}
		}

		else if (e.getSource() == btnLogout)
		{ 
			//if logged out frame closes 
			frmArcade.dispose();
		}

		else if (e.getSource() == btnSort)
		{ 
			list.insertSortScore(2); //Int 1 represents Snake Game
			updateList(listModelHS); 
		}

		else if (e.getSource() == btnSort2)
		{ 
			list.insertSortScore(1); //Int 2 represents Flappy Game
			updateList(listModelHS); 
		}
	}


	public void updateList (DefaultListModel model)
	{ 
		model.removeAllElements(); //Remove all of the previous elements

		for (int i = 0 ; i < list.getSize(); i++) //For the length of the account list
		{
			if (model.equals(listModelHS))
			{ 
				model.addElement(list.getRecordList(i).toHSString()); //Read all of the elements back to the list
			}
			else 
			{ 
				model.addElement(list.getRecordList(i).toString()); //Read all of the elements back to the list
			} 
		}
	}

	public void highscoreMenu (boolean show)
	{ 
		//displays for the highscores menu
		hsBG.setVisible (show); 
		hsTitle.setVisible(show); 
		hsList.setVisible(show);
		btnSort.setVisible(show);
		btnSort2.setVisible(show);
		btnCloseHS.setVisible(show);
	}
	public void settingsMenu(boolean show)
	{ 
		//displays for the settings menu
		settingsBG.setVisible (show); 
		list_1.setVisible(show);
		lblSettingsTitle.setVisible(show);
		btnClose.setVisible(show);
		btnDelete.setVisible(show);
	}

	public void mainMenu (boolean show)
	{ 
		//displays on the main menu
		btnSnakeStart.setVisible(show);
		btnFlappyStart.setVisible(show);
		btnHighscore.setVisible(show);
		btnLogout.setVisible(show);
		lblNewLabel.setVisible(show); 
		background.setVisible(show);
		playSnake.setVisible(show);
		playFlappy.setVisible(show);
		dSnake.setVisible(show);
		dFlappy.setVisible(show);

		//if logged in as bains, settings button is shown
		if(this.player.equals("Bains"))
		{ 
			btnSettings.setVisible(show); 
		}
	}
}