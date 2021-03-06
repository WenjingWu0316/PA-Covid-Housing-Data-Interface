package edu.upenn.cit594.ui;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

import edu.upenn.cit594.logging.Logging;
import edu.upenn.cit594.processor.CovidProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyProcessor;

public class CommandLineUserInterface {
	
	PopulationProcessor populationProcessor;
	Scanner in;
	CovidProcessor covidProcessor;
	PropertyProcessor propertyProcessor;
	Logging logging;
	
	public CommandLineUserInterface(PopulationProcessor populationProcessor, CovidProcessor covidProcessor, PropertyProcessor propertyProcessor) {
		this.populationProcessor = populationProcessor;
		this.covidProcessor = covidProcessor;
		this.propertyProcessor = propertyProcessor;
		this.logging = Logging.getInstance();
		
		in = new Scanner(System.in);
	}
	
	public void start() {
		String contentToLog;
		int choice;
		int run = 1;
		
		// Memoization map for #3
		Map<String, Double> avgMarketValueMap = new HashMap<>();
		
		// Memoization map for #4
		Map<String, Double> avgLivableAreaMap = new HashMap<>();
		
		// Memoization map for #5
		Map<String, Double> MarketValuePerCapitaMap = new HashMap<>();
				
		
		while(run==1) {
		System.out.println("Enter 0 to exit, enter 1 to show total population, enter 2 to show vaccinations per capita, enter 3 to show average market value,"
				+ " enter 4 to show average total livable area, enter 5 to show total residential market value per capita> ");
		System.out.flush();
		
		choice = in.nextInt();
		contentToLog =String.valueOf(choice);
		Logging.logToFile(contentToLog);
		
		if(choice==1) {
			showAllPopulation();
		}else if(choice==2) {
			while(true) {
			System.out.println("Type 'partial' for partial vaccinations data, type 'full' for full vaccinations data> ");
			System.out.flush();
			String ans = in.next(); 
			contentToLog = ans;
			Logging.logToFile(contentToLog);
			
			if(ans.equalsIgnoreCase("partial")) {
				showVaccPerCapita(true);
				break;
			}else if(ans.equalsIgnoreCase("full")) {
				showVaccPerCapita(false);
				break;
			}else {
				System.out.println("Invaid input, please type 'partial' or 'full'> ");
				System.out.flush();
			}}
			
		}else if(choice==3) {
			
			avgMarketValueMap = getAverageProperyInfobyZipCode(avgMarketValueMap, "Market Value", "average", true);
			
		}else if(choice==4) {
			
			avgLivableAreaMap = getAverageProperyInfobyZipCode(avgLivableAreaMap, "Total Livable Area", "average", true);
			
		}else if(choice==5) {
			
			MarketValuePerCapitaMap = getMarketValuePerCapitalbyZipCode(MarketValuePerCapitaMap, "Market Value", true);
	
		}
		else if(choice==6) {
			System.out.println("Type 'partial' for partial vaccinations data, type 'full' for full vaccinations data> ");
			System.out.flush();
			boolean partialFlag = checkValidUserInput(in, "partial", "full");
			
			System.out.println("Type 'highest' or 'lowest' to search for town with the highest/lowest vaccination.");
			System.out.flush();
			boolean highestVaccFlag = checkValidUserInput(in, "highest", "lowest");
			
			printVaccAreaInfo(partialFlag, highestVaccFlag, avgMarketValueMap, avgLivableAreaMap);
			
		}else if(choice==0) {
			run =0;
			logging.closeLog();
		}else {
			System.out.println("ERROR:invalid input, program terminates.");
			System.out.flush();
			run = 0;
			logging.closeLog();
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

	

	public String truncateInteger(double number) {
		
		String numberString = String.valueOf(number);
		
		String regex = "^[^.]*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(numberString);
		
		if(m.find()) {
			return m.group();
		}
		
		return "0";
	}

	protected Map<String, Double> getAverageProperyInfobyZipCode(Map<String, Double> resultsMap, String type, String calcType, boolean truncateFlag) {
		System.out.println("Please enter a zip-code> ");
		System.out.flush();
		String zipcode = in.next(); 
		String contentToLog = zipcode;
		Logging.logToFile(contentToLog);
		

		// Check if the memoization map already contains the infomation. If yes, just retieve it; otherwise, calculate it.
		double result;
		if(resultsMap.containsKey(zipcode)) {
			result = resultsMap.get(zipcode);
		}else {
			result = this.propertyProcessor.calcStatisticsbyZipcode(zipcode, type, calcType);	
			resultsMap.put(zipcode, result);
		}
		
		System.out.println("BEGIN OUTPUT");
		System.out.flush();
		if(truncateFlag) {
			System.out.println(zipcode + " " + truncateInteger(result));
		}
		System.out.println("END OUTPUT");
		System.out.flush();
		
		return(resultsMap);
	}
	
	protected Map<String, Double> getMarketValuePerCapitalbyZipCode(Map<String, Double> resultsMap, String type, boolean truncateFlag) {
		System.out.println("Please enter a zip-code> ");
		System.out.flush();
		String zipcode = in.next(); 
		String contentToLog = zipcode;
		Logging.logToFile(contentToLog);

		// Check if the memoization map already contains the infomation. If yes, just retieve it; otherwise, calculate it.
		double result = 0;
		if(resultsMap.containsKey(zipcode)) {
			result = resultsMap.get(zipcode);
		}else {
			double sum = this.propertyProcessor.calcStatisticsbyZipcode(zipcode, type, "sum");	
			int population = this.populationProcessor.getPopulationByZipcode(zipcode);
			
			if(population != 0 ) {
				result = sum/population;
			}
			resultsMap.put(zipcode, result);
		}
		
		System.out.println("BEGIN OUTPUT");
		System.out.flush();
		if(truncateFlag) {
			System.out.println(zipcode + " " +  truncateInteger(result));
		}
		System.out.println("END OUTPUT");
		System.out.flush();
		
		return(resultsMap);
	}
	
	
	protected boolean checkValidUserInput(Scanner in, String expectedInput1, String expectedInput2) {
		
		boolean flag;
		String ans = in.next(); 
		while(true) {
			if(ans.equalsIgnoreCase(expectedInput1)) {
				flag = true;
				break;
			}else if(ans.equalsIgnoreCase(expectedInput2)) {
				flag = false;
				break;
			}else {
				System.out.format("Invaid input, please type '%s' or '%s'.\n", expectedInput1, expectedInput2);
				System.out.flush();
				flag = checkValidUserInput(in, expectedInput1, expectedInput2);		
				break;
			}		
		}
		return flag;			
	}
	
	/*
	 * Find the max/min entry of a map
	 */
	protected Entry<String, Double> findMaxorMinEntry(Map<String, Double> map, boolean maxFlag) {
		
		Entry<String, Double> resultEntry = null;
		
		if (maxFlag) {
			for (Entry<String, Double> entry : map.entrySet()) {
			    if (resultEntry == null || resultEntry.getValue() < entry.getValue()) 
			    	resultEntry = entry;
			}
		}else {
			for (Entry<String, Double> entry : map.entrySet()) {
			    if (resultEntry == null || resultEntry.getValue() > entry.getValue()) 
			    	resultEntry = entry;
			}
		}
		return resultEntry;
	}
	
	
	
	/**
	 * Print information for the area with the highest/lowest partially/fully vaccination rate.
	 * @param partialFlag boolean to indicate whether to look at partial vaccination data.
	 * @param highestVaccFlag boolean to indicate whether to select an area with the highest vaccination rate.
	 * @param avgMarketValueMap  Memoized map of average market value (used in #3)
	 * @param avgLivableAreaMap Memoized map of average total livable area (used in #4)
	 */
	protected void printVaccAreaInfo(boolean partialFlag, boolean highestVaccFlag, 
			Map<String, Double> avgMarketValueMap, 
			Map<String, Double> avgLivableAreaMap) {
		
		Map<String, Double> VaccCount = this.covidProcessor.getVaccCount(partialFlag);
		
		// Select the highest/lowest vaccinated area based on highestVaccFlag
		Entry<String, Double> resultEntry;
		resultEntry = findMaxorMinEntry(VaccCount, highestVaccFlag);
		
		String zipcode = resultEntry.getKey();
		Double VaccRate = resultEntry.getValue();
		
		// Get average market value by zipcode
		double avgMarketValue;
		if(avgMarketValueMap.containsKey(zipcode)) {
			avgMarketValue = avgMarketValueMap.get(zipcode);
		}else {
			avgMarketValue = this.propertyProcessor.calcStatisticsbyZipcode(zipcode, "Market Value", "average");	
			avgMarketValueMap.put(zipcode, avgMarketValue);
		}
		
		// Get average livable area by zipcode
		double avgLivableArea;
		if(avgLivableAreaMap.containsKey(zipcode)) {
			avgLivableArea = avgLivableAreaMap.get(zipcode);
		}else {
			avgLivableArea = this.propertyProcessor.calcStatisticsbyZipcode(zipcode, "Total Livable Area", "average");	
			avgLivableAreaMap.put(zipcode, avgLivableArea);
		}
		
		// Get population by zipcode
		int population = this.populationProcessor.getPopulationByZipcode(zipcode);
		
		
		String highestVaccFlagString = ((highestVaccFlag)? "highest": "lowest");
		String partialFlagString = ((partialFlag)? "partial": "full");
		
		System.out.format("The area (zipcode: %s) has the %s %s vaccination rate: %.1f%%.\n", zipcode, highestVaccFlagString, partialFlagString, VaccRate * 100);
		System.out.flush();
		
		System.out.format("There are %d people living in that area.\n", population);
		System.out.flush();
		
		System.out.format("The average property value there is %.0f, and the average liveable area is %.0f.\n", avgMarketValue, avgLivableArea);
		System.out.flush();	
	}
}
