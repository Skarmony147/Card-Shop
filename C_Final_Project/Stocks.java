//This file will allow both managers and employees to access the stocks and to buy and sell based on clearance
import java.util.ArrayList;

public class Stocks {
	
	private ArrayList<String> cards = new ArrayList<String>();	//This sets up the list which tells the current cards in stock
	private ArrayList<Integer> cardInven = new ArrayList<Integer>();
	
	
	public Stocks(){	//This constructor sets up the inventory for the store to use
		cards.add("Pokemon");
		cards.add("Baseball");
		cards.add("YuGiOh");
		cards.add("Magic");
		
		for(int i = 0; i < 4; i++){
			cardInven.add(10);
		}
	}
	
	
	
	//public void BuyStock (
	
	
	
	public String toString(){
		return("Currnt items in stock are:\n" + cards + "\nCurrent amounts are:\n" + cardInven);
	}
}
