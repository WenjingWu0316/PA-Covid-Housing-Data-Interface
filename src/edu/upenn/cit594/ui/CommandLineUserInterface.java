package edu.upenn.cit594.ui;

import java.util.Map;
import java.util.Scanner;

import edu.upenn.cit594.processor.CovidProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;

public class CommandLineUserInterface {
	
	PopulationProcessor populationProcessor;
	Scanner in;
	CovidProcessor covidProcessor;

	public CommandLineUserInterface(PopulationProcessor populationProcessor, CovidProcessor covidProcessor) {
		this.populationProcessor = populationProcessor;
		this.covidProcessor = covidProcessor;
		in = new Scanner(System.in);
	}
	
	public void start() {
		int choice;
		int run = 1;
		while(run==1) {
		System.out.println("Enter 0 to exit, enter 1 to show total population, enter 2 to show vaccinations per capita.> ");
		//System.out.flush();
		choice = in.nextInt();
		if(choice==1) {
			showAllPopulation();
		}else if(choice==2) {
			while(true) {
			System.out.println("Type 'partial' for partial vaccinations data, type 'full' for full vaccinations data.> ");
			//System.out.flush();
			String ans = in.next(); 
			if(ans.equalsIgnoreCase("partial")) {
				showPartialVacc();
				break;
			}else if(ans.equalsIgnoreCase("full")) {
				showFullVacc();
				break;
			}else {
				System.out.println("Invaid input, please type 'partial' or 'full'.> ");
				
			}}
		}else if(choice==3) {
			
		}else if(choice==4) {
			
		}else if(choice==5) {
			
		}
		else if(choice==6) {
			
		}else if(choice==0) {
			run =0;
		}else {
			System.out.println("ERROR:invalid input, program terminates.");
			//System.out.flush();
			run = 0;
		}
		System.out.flush();
		}
		in.close();
	}
	
	protected void showAllPopulation() {
		System.out.println("BEGIN OUTPUT");
		int allPopulation = populationProcessor.getAllPopulation();
		System.out.println(allPopulation);
		System.out.println("END OUTPUT");
		System.out.flush();
	}
	
	protected void showPartialVacc() {
		System.out.println("BEGIN OUTPUT");
		Map<Integer, Double> zipVaccMap = covidProcessor.getPartialVacc();
		for(Map.Entry<Integer, Double> entry : zipVaccMap.entrySet()) {
			double n = entry.getValue()* Math.pow(10, 4);
			n = Math.floor(n);
			n = n/Math.pow(10, 4);
			System.out.printf("%d %.4f%n",entry.getKey(),n);
		}
		System.out.println("END OUTPUT");
		System.out.flush();
		
	}
	
	protected void showFullVacc() {
		System.out.println("BEGIN OUTPUT");
		Map<Integer, Double> zipVaccMap = covidProcessor.getFullVacc();
		for(Map.Entry<Integer, Double> entry : zipVaccMap.entrySet()) {
			double n = entry.getValue()* Math.pow(10, 4);
			n = Math.floor(n);
			n = n/Math.pow(10, 4);
			System.out.printf("%d %.4f%n",entry.getKey(),n);
		}
		System.out.println("END OUTPUT");
		System.out.flush();
		
	}
}
