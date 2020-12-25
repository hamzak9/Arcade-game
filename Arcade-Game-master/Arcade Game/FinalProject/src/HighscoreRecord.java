/*
 * Author: Gurbir Bains 
 * Date: January 25. 2016 
 * Description: Creates a user and admin record that holds their username, password, snake score 
 * 				and flappy score. Outputs them all as a string. 
 * Method List: public HighScoreRecord() - constructor 
 * 				public HighScoreRecord(String name, int login, intsScore, int fScore) - gives values
 * 				to the private variables 
 * 				public void processRecord(String Record) - processes a record and splits the word at
 * 				commas. 
 * 				public String getUserName() - gets usernames
 * 				public int getLoginKey() - gets login key 
 * 				public int getSnakeScore() - gets users snake score
 * 				public int getFlappyScore() - gets users bainsybird score 
 * 				public void setUserName(String userName) - sets usernames
 * 				public void setLoginKey(int loginKey) - sets login key 
 * 				public void setSnakeScore(int snakeScore) - sets snake score 
 * 				public void setFlappyScore(int flappyScore) - sets bainsybird score
 * 				public String toString() - converts and returns admins record information 
 * 				as all strings
 * 				public HSString toStrign () - converts and returns all users excepts admins 
 * 				record information, all as strings				 
 */
import javax.swing.JOptionPane;

public class HighscoreRecord 
{
	//declaring private variables for snake/bainsybird score, username and login key 
	private String userName; 
	private int snakeScore, flappyScore, loginKey; 

	public HighscoreRecord()
	{ 
		//sets username, loginkey, snake score and bainsybird score
		this.userName = ""; 
		this.loginKey = 0000; 
		this.snakeScore = 0; 
		this.flappyScore = 0; 
	}

	public HighscoreRecord(String name, int login, int sScore, int fScore)
	{ 
		//sets username, loginkey, snake score and bainsybird score
		this.userName = name; 
		this.loginKey = login; 
		this.snakeScore = sScore; 
		this.flappyScore = fScore; 
	}

	public void processRecord (String record) {// processes the record

		String word[];
		word = record.split(",");// the word is separated at every comma

		this.userName = word[0]; //first word is username
		this.loginKey = Integer.parseInt(word[1]); //second word is login key
		this.snakeScore = Integer.parseInt(word[2]); //third word is snake score
		this.flappyScore = Integer.parseInt(word[3]); //fourth word is bainsybird score
	}

	//gets username
	public String getUserName() 
	{
		return userName;
	}

	//sets username
	public void setUserName(String userName) {
		this.userName = userName;
	}

	//gets login key 
	public int getLoginKey() {
		return loginKey;
	}

	//sets login key  
	public void setLoginKey(int loginKey) 
	{
		this.loginKey = loginKey;
	}

	//gets snake score
	public int getSnakeScore() {
		return snakeScore;
	}

	//sets snake score
	public void setSnakeScore(int snakeScore) 
	{
		this.snakeScore = snakeScore;
	}

	//gets bainsybird score
	public int getFlappyScore() 
	{
		return flappyScore;
	}

	//sets bainsybird score 
	public void setFlappyScore(int flappyScore) 
	{
		this.flappyScore = flappyScore;
	}

	public String toString () //admin record 
	{
		return (this.userName + "," + this.loginKey + "," + this.snakeScore + "," + this.flappyScore); 
	}

	public String toHSString () //user record 
	{
		return (this.userName + ", Snake Score: " + this.snakeScore + ", BainsyBird Score: " + this.flappyScore); 
	}

	public static void main(String[] args) {

		String recs ="";

		HighscoreRecord records = new HighscoreRecord();//creates a new record 

		//prompts user for record information
		recs = JOptionPane.showInputDialog(null,"Enter:"+ "<username>,<Login Key>,<snake score>,<bainsy bird score>");


		records.processRecord(recs);//splits up words

		System.out.println(records.toString());//what an admin record displays
		System.out.println(records.toHSString());//what an user record displays
	}
}
