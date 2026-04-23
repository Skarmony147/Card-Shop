//This file will allow both managers and employees to access the stocks and to buy and sell based on clearance
import java.util.ArrayList;

public class Stocks {
	
	private ArrayList<String> cards = new ArrayList<String>();	//This sets up the list which tells the current cards in stock
	private ArrayList<Integer> cardInven = new ArrayList<Integer>();
	
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
	}
	
	
	/**
	 * This method allows the user to buy card brands for their card shop
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
			System.out.println("There was an error with your selected card brand and it could not be found. Please ensure spelling is correct.");
			return(-1);
		}
	}
	
	
	
	public String toString(){
		return("Currnt items in stock are:\n" + cards + "\nCurrent amounts are:\n" + cardInven + "\n");
	}
}
