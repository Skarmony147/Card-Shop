//This file will allow both managers and employees to access the stocks and to buy and sell based on clearance
import java.util.*;

public class Stocks {
	
	private ArrayList<String> cards = new ArrayList<String>();	//This sets up the list which tells the current cards brands in stock
	private ArrayList<Integer> cardInven = new ArrayList<Integer>();  //This will tell you how much stock you have of each brand in a brand
	private Map<String, Integer> prices = new HashMap<String, Integer>(); //Provides a dictionary for each kind of card box that may be available.
	
	/**
	 * The constructor allows the user to have a basic inventory set up of card brands
	 */
	public Stocks(){	//This constructor sets up the inventory for the store to use
		cards.add("Pokemon");
		cards.add("Baseball");
		cards.add("YuGiOh");
		cards.add("Magic");
		
		for(int i = 0; i < 4; i++){
			cardInven.add(10);
		}
		//If you want to modify, the set up is (key, value). In this case it is the product in a string and then the price as an integer
		prices.put("ETB", 50);
		prices.put("Booster bundle", 30);
		prices.put("Booster box", 150);
		prices.put("UPC", 130);
		
	}
	
	
	/**
	 * This method allows the user to buy card brands for their card shop
	 * 
	 * @param cardType takes in the card brand that you are trying to buy stock for
	 * 
	 * @param amount stores how much of that specific card brand you want to buy stock for.
	 */
	public void BuyStock(String cardType, int amount){
		if(cards.get(0).equals(cardType))
			cardInven.set(0, cardInven.get(0) + amount);
		else if(cards.get(1).equals(cardType))
			cardInven.set(1, cardInven.get(1) + amount);
		else if(cards.get(2).equals(cardType))
			cardInven.set(2, cardInven.get(2) + amount);
		else if(cards.get(3).equals(cardType))
			cardInven.set(3, cardInven.get(3) + amount);	
		else
			System.out.println("There was an error with your selected card brand and the purchase did not go through. Please ensure spelling is correct.");
	}
	/**
	 * This method allows the user to view current stock for a specific card brand
	 * 
	 * @param cardType takes in a string to find the current stock
	 */	
	public int ViewStock(String cardType){
		if(cards.get(0).equals(cardType))
			return(cardInven.get(0));
		else if(cards.get(1).equals(cardType))
			return(cardInven.get(1));
		else if(cards.get(2).equals(cardType))
			return(cardInven.get(2));
		else if(cards.get(3).equals(cardType))
			return(cardInven.get(3));	
		else{
			System.out.println("There was an error with your selected card brand and it could not be found. Please ensure spelling is correct."); //Maybe turn this print statement to a window opening
			return(-1);	//Thinking of an idea that uses this -1 as a sentinal value and just does nothing if this is there
		}
	}
	/**
	 * This method allows the user to view the prices of a certain kind of product regardless of brand
	 * 
	 * @param boxType Is needed for pulling the price from the prices map.
	 */
	public int viewPrice(String boxType){
		if(prices.get(boxType) == null)
			return(-1);	//This could also be used with a sentinal value aswell. 
		else
			return(prices.get(boxType));
	}
	
	public String toString(){
		return("Currnt items in stock are:\n" + cards + "\nCurrent amounts are:\n" + cardInven + "\n");
	}
}
