import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Author: Shahzada Gulfam
 * Date: January, 2016
 * Description: This class is a list of HighscoreRecords. The user is able to insert and delete records. 
 * 				New manipulation methods include, searching the list for duplicates and searching the list
 * 				to see if the user name exists. 
 * 
 * Method List: public HighscoresList() - Default constructor, sets max size to 20
 * 				public boolean insert (HighscoreRecord record) - Attempts to insert a record in the list, returns true if successful 
 * 				public boolean delete (String userName) - Attempts to delete a record, returns true if successful
 * 				public void insertSort () - insertion sort that sorts the list by String
 * 				public void insertSortScore (int game) - sorting method that will sort the list by highest score, for either game
 * 				public String [] fileLoader(String fileName) - loads info from a file onto the list
 * 				public int binarySearch (String searchKey) - searching algorithm, used by methods in this class
 * 				public boolean checkExisiting (String name) - searches the array, returns true if user exists, used by Login 
 * 				public boolean checkDuplicate (HighscoreRecord input) - returns true if there is a user already with the name, used by insert method
 * 				public boolean writeToFile(String fileName) - writes the info on the list to a file			
 * 
 */

public class HighscoresList 
{
	private int size, maxSize; 
	private HighscoreRecord recordList[]; 

	//Default constructor
	public HighscoresList() 
	{
		this.size = 0; 
		this.maxSize = 20; 
		recordList = new HighscoreRecord[maxSize]; 
	}

	//Sets size of highscore list, based on parameters 
	public HighscoresList(int size) 
	{
		this.size = size; 
		this.maxSize = 20; 
		recordList = new HighscoreRecord[maxSize]; 
	}

	// this attempts to insert a highscore record into the record list
	public boolean insert (HighscoreRecord record)
	{
		//First checks, if there is a duplicate record
		if (checkDuplicate(record))
		{ 
			if (size < maxSize)// checks if there is space , if there is the record is placed
			{
				size++;
				recordList [size - 1] = record;
				return true;
			}
		} 
		return false;
	}

	//Deleting a record from the list
	public boolean delete (String userName)
	{ 
		//Delete method which uses the binary search
		int where = 0; 
		where = binarySearch(userName); 

		//Index of what to delete is given by search method
		if (where >= 0)
		{ 
			//Remove the method from the list, and decrease the size of the list
			recordList [where] = recordList [size - 1]; 
			size--; 
			return true; 
		}
		return false; 
	}

	//Sorting for strings
	public void insertSort ()// sorting method
	{
		for (int top = 1 ; top < size ; top++)
		{
			HighscoreRecord item = recordList [top];
			int i = top;
			while (i > 0 && (item.getUserName().compareToIgnoreCase (recordList [i-1].getUserName()) < 0))
			{
				recordList [i] = recordList [i - 1];
				i--;
			}
			recordList [i] = item;
		}
	}

	//Method to check if an existing user already is in the list
	public boolean checkDuplicate (HighscoreRecord input)
	{ 
		for (int i = 0; i < size; i++)
		{ 
			if (recordList[i].getUserName().equals(input.getUserName()))
			{ 
				return false; 
			}
		}
		return true; 
	}

	//Used by LoginGUI to make sure the name entered is on the list
	public boolean checkExisiting (String name)
	{ 
		for (int i = 0; i < size; i++)
		{ 
			if (recordList[i].getUserName().equals(name))
			{ 
				return true; 
			}
		}
		return false; 
	}

	//Sorts the score of the game 
	public void insertSortScore (int game)// sorting method
	{
		for (int top = 1 ; top < size ; top++)
		{
			HighscoreRecord item = recordList [top];
			int i = top;

			//Gets Flappy score
			if (game == 1)
			{ 
				while (i > 0 && (item.getFlappyScore() > recordList [i-1].getFlappyScore()))
				{
					recordList [i] = recordList [i - 1];
					i--;
				}
			}

			//Gets snake score
			else if (game == 2)
			{
				while (i > 0 && (item.getSnakeScore() > recordList [i-1].getSnakeScore()))
				{
					recordList [i] = recordList [i - 1];
					i--;
				}
			}

			recordList [i] = item;
		}
	}


	//Gets the pin
	public int getPin(int loc)
	{ 
		return recordList[loc].getLoginKey(); 
	}

	//To string method, converts whole list to a string
	public String toString()
	{ 
		String output = ""; 
		for (int i = 0; i < size; i++)
		{ 
			output += recordList[i].toString() + "\n"; 
		}
		return output; 
	}

	//Binary search, locates a search key 
	public int binarySearch (String searchKey)
	{
		insertSort ();// sorts first 

		int low = 0;
		int high = size - 1;   // set bottom and top of array
		int middle;
		while (low <= high)
		{
			middle = (high + low) / 2; //divide array in two
			if (searchKey.equalsIgnoreCase (recordList[middle].getUserName()))
			{
				return middle;
			}

			//If not found, the search is narrowed down again
			else if (searchKey.compareToIgnoreCase (recordList[middle].getUserName()) < 0)
			{
				high = middle - 1;
			}
			else
			{
				low = middle + 1;
			}
		}
		//Returns -1, if searchKey is not found
		return -1;
	}

	//If the user wants to save the data
	public boolean writeToFile(String fileName)
	{ 
		try
		{
			//Declare a print writer to write to the new file
			PrintWriter writer = new PrintWriter(fileName);

			writer.println(size);

			//For the length of the list
			for (int i = 0; i < size; i++)
			{ 
				writer.println(recordList[i].toString()); //Write a record
			}
			writer.close(); //Close writer
			return true; 
		} 
		//Catch exception
		catch (IOException e1) 
		{
			return false; 
		}
	}

	//returns size
	public int getSize() {
		return size;
	}

	//sets size
	public void setSize(int size) {
		this.size = size;
	}

	//Retrns a highscore record
	public HighscoreRecord getRecordList(int i) 
	{
		return recordList[i];
	}

	//Returns max size
	public int getMaxSize() {
		return maxSize;
	}

	//Sets max size of list
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	//Sets the record list
	public void setRecordList(HighscoreRecord[] recordList) 
	{
		this.recordList = recordList;
	}

	//Loads a file and saves info on the file to the list
	public String [] fileLoader(String fileName){

		String aRecord[];   // declare String array to return

		try{
			FileReader fr = new FileReader (fileName); // opening the file to read
			BufferedReader inputFile = new BufferedReader (fr);

			int size = Integer.parseInt (inputFile.readLine());// read size

			aRecord = new String[size];  // create String array
			// loop to read file strings into the Array     
			for (int i = 0; i < size; i++){

				// String to read the record line and load array 
				String lineInfo = inputFile.readLine(); 
				aRecord [i] = lineInfo; 
			}
			inputFile.close(); // close the file
		}
		catch(Exception e)
		{
			aRecord = new String [0];  // Set array to zero length if error
		}
		return aRecord;  // return the array
	} // loadFile

	//Retruns the list
	public HighscoreRecord[] getRecordList() 
	{
		return recordList;
	}

	//Self testing
	public static void main(String[] args) 
	{
		//Creating two string record
		String record = "Shahzada,1212,100,5,10"; 
		String record2 = "Shahzada,1999,1000,150,30"; 

		//Creating list object
		HighscoresList list = new HighscoresList(); 

		//Creating record, processing the record and inserting it into the list
		HighscoreRecord scoreInfo = new HighscoreRecord(); 
		scoreInfo.processRecord(record);
		list.insert(scoreInfo); 

		//Creating record, processing the record and inserting it into the list
		HighscoreRecord scoreInfo2 = new HighscoreRecord(); 
		scoreInfo2.processRecord(record2);
		list.insert(scoreInfo2); 

		//testing the sort
		list.insertSort();
		System.out.println(list.toString()); 
		System.out.println(); 

		//Testing flappy
		list.insertSortScore(2);
		System.out.println(list.toString()); 


		//testing the delete method
		if (list.delete("Shahzada Gulfam")) 
		{ 
			System.out.println("SUCCESS");
		}
		else 
		{ 
			System.out.println("UNABLE TO FIND RECORD");
		}

		System.out.println(list.toString()); 


	}

}
