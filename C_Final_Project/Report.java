import java.io.*;
import java.util.*;

public class Report{
	
	public static void Begin(double remainingFunds, String employeeData, double employeeMoney){
		try{
			Random rand = new Random();
			int randNum = rand.nextInt(5) + 1;
			PrintWriter printy = new PrintWriter("DailyReport.txt");
			
			remainingFunds = remainingFunds - employeeMoney;
			System.out.println("Paid employees. Total amount: " + employeeMoney);
			randNum = rand.nextInt(5) + 1;
			
			printy.printf("Your total amount in your business account is: $%.2f dollars\n", remainingFunds);
			printy.printf(employeeData);
			if(remainingFunds <= 0)
				printy.println("Please ensure you are making profit, this cannot happen again.");
			else if(remainingFunds < 100)
				printy.println("You are doing all right. Keep it up!");
			else if(remainingFunds < 500)
				printy.println("Very very nice manager. Please keep up this behavior to ensure you get more holidays!");
			else
				printy.println("Wow! Very good! Your store is going well. Superb job manager!");
			
			System.out.println("Please check your files for 'DailyReport.txt'");
			printy.close();
		}
		
		catch(Exception e){
			System.out.println("There was an error with your daily report: " + e);
		}
		
	}
	
	
	
	
}
