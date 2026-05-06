import java.util.*;


public class Management{
	
	private ArrayList<String> workers = new ArrayList<String>();	//This array is all your possible workers workin gat your store
	private ArrayList<Double> hourlyWage = new ArrayList<Double>();	//This array is how much each of them gets paid
	private ArrayList<String> currentEmployees = new ArrayList<String>();	//This array is which two of your workers are in rotation at the moment
	
	
	public Management(){
		//Maybe make a list of a bunch of names for the constructor to choose from.
		workers.add("Bob");	//Adding workers into the array
		workers.add("Kat");
		workers.add("Mikayla");
		workers.add("Jim");
		
		for(int i = 0; i < 5; i++){	//Adding pay to array
			hourlyWage.add(14.50);
		}
		
		Random rando = new Random();
		boolean dupe = true;
		while(dupe){
			String wrkr1 = workers.get(rando.nextInt(4));
			String wrkr2 = workers.get(rando.nextInt(4));
			if(wrkr1.equals(wrkr2)){
				continue;
			}
			else
				dupe = false;
		currentEmployees.add(wrkr1);
		currentEmployees.add(wrkr2);
		}
	}
	public Management(ArrayList<String> passer, double money){
		workers = passer;
		//Make sure they can only have max of six workers using a while loop to check and see if it equals six and a scanner maybe?
		
		for(int i = 0; i < workers.size(); i++){	//Adding pay to array
			hourlyWage.add(money);
		}
		
		Random rando = new Random();
		boolean dupe = true;
		while(dupe){
			String wrkr1 = workers.get(rando.nextInt(workers.size()));
			String wrkr2 = workers.get(rando.nextInt(workers.size()));
			if(wrkr1.equals(wrkr2)){
				continue;
			}
			else
				dupe = false;
		currentEmployees.add(wrkr1);
		currentEmployees.add(wrkr2);
		}
		
	}
	/**
	 * This function tells you what employees are currently working.
	 */
	public String inHouse(){
		return("Current employees working are: " + currentEmployees.get(0) + " and " + currentEmployees.get(1));
	}
	
	/**
	 * This method allows the user or "manager" rank personel to fire employees.
	 * 
	 * @param employee is the name of the person you would like to fire.
	 * @param reason is the text that you can write for why they were fired. It will automatically be set to no comment if you do not put anything there.
	 */
	public void Fire(String employee, String reason){
		boolean notFound = true;
		for(int i = 0; i < workers.size(); i++){
			if(workers.get(i).equals(employee)){
				System.out.printf("Fired %s. When questioned, the manager said, '%s'\n", employee, reason);
				workers.remove(i);
				hourlyWage.remove(i);
				notFound = false;
				break;	
			}
		
		}
		if(notFound){
			System.out.println("Selected employee was not found. Please double check spelling of their name.");
		}
		
	}
	
	public void shiftChange(){
		String current1 = currentEmployees.get(0);
		String current2 = currentEmployees.get(1);
		
		Random rando = new Random();
		boolean dupe = true;
		while(dupe){
			String wrkr1 = workers.get(rando.nextInt(workers.size()));	//Gets a random worker from your list of current workers
			String wrkr2 = workers.get(rando.nextInt(workers.size()));
			if(wrkr1.equals(wrkr2)){
				continue;
			}
			else if(wrkr1.equals(current1) || wrkr1.equals(current2) /*|| wrkr2.equals(current1) || wrkr2.equals(current2)*/){
				continue;
			}
			else
				dupe = false;
		currentEmployees.set(0, wrkr1);
		currentEmployees.set(1, wrkr2);
		System.out.printf("A new shift has started bringing in %s and %s!\n", wrkr1, wrkr2);
		}
		
	}
	
	public String toString(){
		return("Your currently employed employees are: " + workers + "\nHourly wages are: " + hourlyWage + "\n");  
		
	}
	
	
}
