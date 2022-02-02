import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;
import java.lang.Math;
// Program:	Mastermind
// Written by:	Gina Guerra
// Program Description:	Runs a program to allow the user to play the mastermind game
// File name:	Mastermind.java
// File description: Runs a mastermind game and allows the user to continue playing upon winning/losing
// Other files in this project: none
// Challenges: Fully forgot hashmaps work differently in C languages vs java and made a bit of a mess trying to make a temporary hashmap...
//
//               Revision History
// Date:                   By:                  Action:
// ---------------------------------------------------
// 4/16/20			    gg       	coded everything with a Hashmap, then dismantled everything to implement arrays instead (better idea)
// 4/17/20			    gg       	added comments

public class Mastermind {
	final static String TITLE = "Mastermind";	//title of program
	static boolean repeat = true;				//boolean to be used in main

	public static int[] generateMasterCode() {
		//creates an array of length 4 with random numbers between 1 and 6: this is to be the 4 digit code the user must guess
		int[] masterArray = new int[4];
		for (int i = 0; i < 4; i++) {
			int rand = (int) (Math.random() * 6) + 1;
			masterArray[i]=rand;
			}
		//System.out.println(Arrays.toString(masterArray)); this line was used for debugging purposes: allowed me to view what
		// the master code was
		return masterArray;
		}
		
	
	public static int[] checkGreen(int[] userArray, int[] tempArray){
		//method takes user array and masterCode copy as inputs to determine necessary data
		//method keeps track of how many ints are the right value at the right location (RVRL), then calls another method
		// to track how many ints are the right value at the wrong location (RVWL); method returns both counters to the main method
		
		int greenCount = 0;	//initialize RVRL counter
		for (int i = 0; i < 4; i++) {	//go through arrays
			if(tempArray[i] == userArray[i]){
				greenCount++;	//add to counter if we have a RVRL
				tempArray[i] = 88;	//change the copy of the masterCode so we don't have yellowCounter false positives 
			}
		}
		int yellowCount = checkYellow(userArray, tempArray);	//check for RVWL
		int[] returnArray = new int[] {greenCount, yellowCount};
		return returnArray;	//returns both RVRL and RVWL counters
	}
	
	public static int checkYellow(int[] userArray, int[] tempArray){
		//method takes user array and masterCode copy as inputs to determine necessary data
		//method keeps track of how many ints are the right value at the wrong location (RVWL);
		// method returns RVWL counter to the checkGreen method
		
		int yellowCount = 0;	//initialize RVWL counter
		for (int i = 0; i< 4; i++) {	
			for(int j = 0; j < 4; j++){	//nested for loops to let the program go through both input arrays
				if(userArray[i]==tempArray[j]){
					yellowCount++;	//if we find a RVWL, we count it
					tempArray[j]=99;	//change the copy of the masterCode out of an abundance of caution to avoid false positives
				}
			}
		}
		
		return yellowCount;	//returns RVWL counter
	}
	

	public static void main(String args[]) {
		System.out.println("Welcome to " + TITLE);	//prints out title of program
		int[] userArray = new int[4];		//creates empty array of length 4 to store user input
		Scanner sc = new Scanner(System.in);	//scanner allows the program access to user input
		while (repeat) {
			int[] masterArray = generateMasterCode();	//calls method that creates the 4 digit passcode
			for (int a = 0; a < 10; a++) {	//for loop to allow the user 10 attempts to crack the code
				for (int i = 0; i < 4; i++) {	//for loop to allow the user to input 4 ints
					System.out.println("Enter an integer between one and six ");	//lets user know what the acceptable parameters are
					int n = sc.nextInt();
					if (n < 1 || n > 6) {
						System.out.println("Invalid number");
						return;	//if the number is out of bounds, end the program
					} else {
						userArray[i] = n; //if the number is within range, add it to the empty array at the appropriate location
					}
				}
				
				
				int[] tempArray = new int[4];	//create temporary array of length 4
				for (int j = 0; j < 4; j++) {
					tempArray[j] = masterArray[j];
				}	//make the temporary array a copy of the masterCode
				int[] myCounts = checkGreen(userArray, tempArray);	//check for RVRL and RVWL and save these values
				
			
				System.out.println("You've guessed " + myCounts[1] + " right numbers in the wrong place"); //lets user know RVWL
				System.out.println("You've guessed " + myCounts[0] + " right numbers in the right place"); //lets user know RVRL
				System.out.println(Arrays.toString(userArray)); //remind the user what their guess was
				if(myCounts[0] ==4){	//if you have all four RVRL
					System.out.println("Congratulations! You're a master mind!! You deduced the code!!"); //you beat the game!!
					break;
				}

			}
			System.out.println("Do you want to try again? 1.yes 2.no"); 			//give user the option to play another round.

			int lastPrompt = sc.nextInt();

			if (lastPrompt == 2)
				repeat = false; // no means no
			else
				repeat = true;	// yes means yes
		}
		sc.close();
		System.out.println("Thank you for using " + TITLE);	//thanks user for using the program

	}

}
