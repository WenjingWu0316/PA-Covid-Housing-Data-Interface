package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;

public class CovidProcessor {
	
	List<CovidData> covidList;
	Map<Integer, Integer> populationMap;
	Reader reader;
	List<Population> populationList;

	public CovidProcessor(PopulationProcessor populationProcessor, Reader reader){
		covidList = reader.getAllData();
		populationList = populationProcessor.populationList;
		populationMap = populationProcessor.getPopulationMap();
	}

	
	public Map<Integer, Double> getPartialVacc() {
		Map<Integer, Double> zipVaccMap = new TreeMap<>();
		String timeStamp;
		int zipCode;
		int ParPopulation;
		for(CovidData c: covidList) {
			timeStamp = c.getTimeStamp();
			zipCode = c.getZipCode();
			ParPopulation= c.getPartiallyVaccPopulation();
			if(timeStamp.substring(6,11).equals("05-20")&& ParPopulation!=0) {
				if(populationMap.containsKey(zipCode)) {
//					System.out.println(ParPopulation);
//					System.out.println(populationMap.get(zipCode));
					double vaccPerCapita = (double) ParPopulation/populationMap.get(zipCode);
					zipVaccMap.put(zipCode, vaccPerCapita);
//					System.out.println(zipCode);
//					System.out.println(vaccPerCapita);
				}
			}
		}
		return zipVaccMap;
	}
	
	public Map<Integer, Double> getFullVacc() {
		Map<Integer, Double> zipVaccMap = new TreeMap<>();
		String timeStamp;
		int zipCode;
		int FullPopulation;
		for(CovidData c: covidList) {
			timeStamp = c.getTimeStamp();
			zipCode = c.getZipCode();
			FullPopulation = c.getFullyVaccPopulation();
			if(timeStamp.substring(6,11).equals("05-20")&& FullPopulation!=0) {
				if(populationMap.containsKey(zipCode)) {
					double vaccPerCapita = (double) FullPopulation / populationMap.get(zipCode);
					zipVaccMap.put(zipCode, vaccPerCapita);
				}
			}
		}
		return zipVaccMap;
		
	}
}
