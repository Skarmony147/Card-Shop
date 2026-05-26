/**
 * This class tallies up the action done in the app and gives an 
 * end of day report about profit or losses.
 **/

import java.io.*; // For writing to file
import java.util.*; // For reading from file and arraylists

public class Report{
	private double funds = 500.00; // Double holding funds
	private ArrayList<String> actions = new ArrayList<String>(); // Arraylist holding what actions have been done
	private ArrayList<Double> actionCost = new ArrayList<Double>(); // Arraylist holding the cost of each action
	
	/**
	 * Function to add an action done to the arraylists
	 * 
	 * @param action to take what action was done
	 * @param cost to take how much action cost
	 **/
	public void addAction(String action, Double cost){
		actions.add(action);
		actionCost.add(cost);
	}
	
	/**
	 * Function to calculate remaining funds
	 * 
	 * @return remaining funds
	 **/
	public double remaining(){
		double remain = funds;
		for(double d : actionCost){
			remain = remain + d;
		}
		return(remain);
	}
	
	/**
	 * Function to print out final report
	 * 
	 * @return String containing report
	 **/
	public String report(){
		String total ="";
		total = total + String.format("At the beginning, you had $500.00 /n/n");
		total = total + String.format("You did these actions during the day: /n");
		for(int i = 0; i < actions.size(); i++){
			total = total + String.format("%s which resulted in %.2f /n", actions.get(i), actionCost.get(i));
		}
		total = total + String.format("/nNow, your remaining funds are %.2f /n", remaining());
		if (funds > remaining()){
			total = total + String.format("You lost money. Better luck next time!");
		} else if (funds == remaining()){
			total = total + String.format("You either did nothing or bought and sold the same amount.");
		} else {
			total = total + String.format("Woohoo! You made profit!");
		}
		
		return(total);
	}
}
