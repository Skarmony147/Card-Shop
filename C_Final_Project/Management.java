import java.util.*;


public class Management{
	
	private ArrayList<String> workers = new ArrayList<String>();	//This array is all your possible workers workin gat your store
	private ArrayList<Double> hourlyWage = new ArrayList<Double>();	//This array is how much each of them gets paid
	private ArrayList<String> currentEmployees = new ArrayList<String>();	//This array is which two of your workers are in rotation at the moment
	
	
	public Management(){
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
		System.out.println(workers);
		
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
	
	public String inHouse(){
		return("Current employees working are: " + currentEmployees.get(0) + " and " + currentEmployees.get(1));
	}
	//public void Fire(String Employee){
		
		
	//}
	//For future reference, make a method called shiftChange to swap out our current employees. use the random number stuff.
	/*public void shiftChange(){
		String current1 = currentEmployees.get(0);
		String current2 = currentEmployees.get(1);
		
		Random rando = new Random();
		boolean dupe = true;
		while(dupe){
			String wrkr1 = workers.get(rando.nextInt(workers.size()));
			String wrkr2 = workers.get(rando.nextInt(workers.size()));
			if(wrkr1.equals(wrkr2) && current1.equals(current1) && current2.equals(current2)){
				continue;
			}
			else
				dupe = false;
		currentEmployees.add(wrkr1);
		currentEmployees.add(wrkr2);
		}
		
	}*/
	
	public String toString(){
		return("Your currently employed employees are: " + workers + "\nHourly wages are: " + hourlyWage + "\n");  
		
	}
	
	
}
