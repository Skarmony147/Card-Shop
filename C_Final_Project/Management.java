import java.util.*;


public class Management{
	
	public ArrayList<String> workers = new ArrayList<String>();	//This array is all your possible workers workin gat your store
	public ArrayList<Double> hourlyWage = new ArrayList<Double>();	//This array is how much each of them gets paid
	private ArrayList<String> currentEmployees = new ArrayList<String>();	//This array is which two of your workers are in rotation at the moment
	
	//may need an instance of the Report class here so I can print out everything to a file
	
	/**
	 * This constructor sets up a basic card shop with a set amount of workers and money.
	 */
	public Management(){
		//Maybe make a list of a bunch of names for the constructor to choose from.
		workers.add("Bob");	//Adding workers into the array
		workers.add("Kat");
		workers.add("Mikayla");
		workers.add("Jim");
		
		for(int i = 0; i < 4; i++){	//Adding pay to array
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
	
	/**
	 * This is an overloaded constructor that allows you to customize your shop more than the basic one.
	 * That includes custom worker names and custom pay
	 * 
	 * @param passer is a string ArrayList and is the list of names you would like to have as workers.
	 * @param money is your custom amount of pay you are paying every memeber of the card shop.
	 */
	public Management(ArrayList<String> passer, double money){
		workers = passer;
		//(Optional)
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
	 * This method tells you what employees are currently working.
	 */
	public String inHouse(){
		return("Current employees working are: " + currentEmployees.get(0) + " and " + currentEmployees.get(1));
	}
	
	/**
	 * This method allows the user or "manager" rank personel to fire employees and give a reason why.
	 * If no reason has been provided, "..." will be provided instead.
	 * 
	 * @param employee is the name of the person you would like to fire.
	 * @param reason is the text that you can write for why they were fired. It will automatically be set to no comment if you do not put anything there.
	 */
	public void Fire(String employee, String reason){
		boolean notFound = true;
		String statement = reason;
		if(reason.length() < 6)
			statement = "...";
		
		for(int i = 0; i < workers.size(); i++){	//Checks to see if the employee you want to fire is there.
			if(workers.get(i).equalsIgnoreCase(employee)){
				System.out.printf("Fired %s. When questioned, the manager said, '%s'\n", employee, statement);
				workers.remove(i);
				hourlyWage.remove(i);
				notFound = false;
				break;	
			}
		
		}
		if(notFound)
			System.out.println("Selected employee was not found. Please double check spelling of their name.");
		
	}
	
	/**
	 * This method allows the user to hire employees and give a reason why.
	 * If you have too many workers (6) nothing will happen.
	 * 
	 * @param name is a string that is the name of the employee to hire.
	 * @param pay is a double that is how much the new employee will be getting paid.
	 */
	public void Hire(String name, double pay){
		if(workers.size() < 6){
			System.out.printf("A new member has join the ranks. Please welcome %s!\n", name);
			workers.add(name);
			hourlyWage.add(pay);
		}
		else
			System.out.println("You have too many workers than this shop can contain/pay!");
		
		
		
	}
	
	
	
	/**
	 * This method allows the user to change the pay of an employee you have hired.
	 * If if the case the name you typed in could not be found, it will do nothing.
	 * 
	 * @param name is a string and is the name of the employee whose wage you want to change.
	 * @param pay is the amount that you will be paying them.
	 */
	public void ChangePay(String name, double pay){
		boolean notFound = true;
		for(int i = 0; i < workers.size(); i++){
			if(workers.get(i).equalsIgnoreCase(name)){
				hourlyWage.set(i, pay);
				System.out.println(workers.get(i) + " has had their pay changed!");
				notFound = false;
				break;
			}
		}
		if(notFound){
			System.out.println("The selected worker could not be found. Please ensure name spelling is accurate!");
		}
		
	}
	
	/**
	 * This method allows the user to see the exact wage that is associated with an employee.
	 * 
	 * @param name is a string that is the employee's name.
	 * 
	 * @return is a double and will return the corresponding wage. If the wage cannot be found, it will return as -1.0.
	 */
	public double ViewWages(String name){
		double wage = 0.0;
		boolean notFound = true;
		for(int i = 0; i < workers.size(); i++){
			if(workers.get(i).equalsIgnoreCase(name)){
				notFound = false;
				wage = hourlyWage.get(i);
				break;
			}
		}
		if(notFound)
			return(-1.0);
		
		else
			return(wage);
		
	}
	
	/**
	 * shiftChange allows for the user to have the shift change and get new people in. 
	 * This has no real value besides acting like your people get a break/chnaging out character names.
	 */
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
	/**
	 * overrides the basic toString method and instead displays all of the workers you currently have hired and how much they are getting paid.
	 */
	public String toString(){
		return("Your currently employed employees are: " + workers + "\nHourly wages are: " + hourlyWage + "\n");  
		
	}
	
	
}
