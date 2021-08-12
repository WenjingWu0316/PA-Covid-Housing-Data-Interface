package edu.upenn.cit594.ui;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

import edu.upenn.cit594.processor.CovidProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyProcessor;

public class CommandLineUserInterface {
	
	PopulationProcessor populationProcessor;
	Scanner in;
	CovidProcessor covidProcessor;
	PropertyProcessor propertyProcessor;
	
	public CommandLineUserInterface(PopulationProcessor populationProcessor, CovidProcessor covidProcessor, PropertyProcessor propertyProcessor) {
		this.populationProcessor = populationProcessor;
		this.covidProcessor = covidProcessor;
		this.propertyProcessor = propertyProcessor;
		in = new Scanner(System.in);
	}
	
	public void start() {
		int choice;
		int run = 1;
		while(run==1) {
		System.out.println("Enter 0 to exit, enter 1 to show total population, enter 2 to show vaccinations per capita.> ");
		System.out.flush();
		choice = in.nextInt();
		if(choice==1) {
			showAllPopulation();
		}else if(choice==2) {
			while(true) {
			System.out.println("Type 'partial' for partial vaccinations data, type 'full' for full vaccinations data.> ");
			System.out.flush();
			String ans = in.next(); 
			if(ans.equalsIgnoreCase("partial")) {
				showVaccPerCapita(true);
				break;
			}else if(ans.equalsIgnoreCase("full")) {
				showVaccPerCapita(false);
				break;
			}else {
				System.out.println("Invaid input, please type 'partial' or 'full'.> ");
				System.out.flush();
			}}
		}else if(choice==3) {
			
			getAverageProperyInfobyZipCode("Market Value", "average", true);

		}else if(choice==4) {
			
			getAverageProperyInfobyZipCode("Total Livable Area", "average", true);
			
		}else if(choice==5) {
			
			getMarketValuePerCapitalbyZipCode("Market Value", true);
	
		}
		else if(choice==6) {
			
		}else if(choice==0) {
			run =0;
		}else {
			System.out.println("ERROR:invalid input, program terminates.");
			System.out.flush();
			run = 0;
		}
		}
		in.close();
	}
	
	protected void showAllPopulation() {
		System.out.println("BEGIN OUTPUT");
		System.out.flush();
		int allPopulation = populationProcessor.getAllPopulation();
		System.out.println(allPopulation);
		System.out.flush();
		System.out.println("END OUTPUT");
		System.out.flush();
	}
	
	protected void showVaccPerCapita(boolean partial) {
		System.out.println("BEGIN OUTPUT");
		System.out.flush();
		Map<String, Double> zipVaccMap = covidProcessor.getVaccCount(partial);
		DecimalFormat df = new DecimalFormat("0.0000");
		df.setRoundingMode(RoundingMode.DOWN);
		for(Entry<String, Double> entry : zipVaccMap.entrySet()) {
			double n = entry.getValue();
			System.out.println(entry.getKey()+ " "+df.format(n));
			System.out.flush();
		}
		System.out.println("END OUTPUT");
		System.out.flush();
		
	}
	
//	protected void showFullVacc() {
//		System.out.println("BEGIN OUTPUT");
//		Map<Integer, Double> zipVaccMap = covidProcessor.getFullVacc();
//		for(Map.Entry<Integer, Double> entry : zipVaccMap.entrySet()) {
//			double n = entry.getValue()* Math.pow(10, 4);
//			n = Math.floor(n);
//			n = n/Math.pow(10, 4);
//			System.out.printf("%d %.4f%n",entry.getKey(),n);
//		}
//		System.out.println("END OUTPUT");

//		
//	}
	

	public String truncateInteger(double number) {
		
		String numberString = String.valueOf(number);
		
		String regex = "^[^.]*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(numberString);
		
		return m.group();
	}


	protected void getAverageProperyInfobyZipCode(String type, String calcType, boolean truncateFlag) {
		System.out.println("Please enter a zip-code. ");
		System.out.flush();
		String zipcode = in.next(); 
		double result = this.propertyProcessor.calcStatisticsbyZipcode(zipcode, type, calcType);	
		
		if(truncateFlag) {
			System.out.println(zipcode + truncateInteger(result));
		}
	}
	

	protected void getMarketValuePerCapitalbyZipCode(String type, boolean truncateFlag) {
		System.out.println("Please enter a zip-code. ");
		System.out.flush();
		String zipcode = in.next(); 
		
		double sum = this.propertyProcessor.calcStatisticsbyZipcode(zipcode, type, "sum");	
		int population = this.populationProcessor.getPopulationByZipcode(zipcode);
		
		double MarketValuePerCapital = 0;
		if(population != 0 ) {
			MarketValuePerCapital = sum/population;
		}
		
		if(truncateFlag) {
			System.out.println(zipcode + truncateInteger(MarketValuePerCapital));
		}
		
	}
	
	
}
