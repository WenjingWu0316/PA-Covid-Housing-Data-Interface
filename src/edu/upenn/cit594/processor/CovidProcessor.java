package edu.upenn.cit594.processor;

import java.util.Date;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.CovidFileReader;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;

public class CovidProcessor {
	
	List<CovidData> covidList;
	Map<String, Integer> populationMap;
	CovidFileReader reader;
	List<Population> populationList;
	Map<String, Double> zipVaccMap;

	public CovidProcessor(PopulationProcessor populationProcessor, CovidFileReader reader){
		covidList = reader.getAllData();
		populationList = populationProcessor.populationList;
		populationMap = populationProcessor.getPopulationMap();
		zipVaccMap = null;
	}

	public Date findLatestTime() {
		Date timeStamp;
		Date lastestTime = covidList.get(0).getTimeStamp();
		for(CovidData c: covidList) {
			timeStamp = c.getTimeStamp();
			if(timeStamp.after(lastestTime)) {
				lastestTime = timeStamp;
			}
		}
		return lastestTime;
	}
	
	
	public Map<String, Double> getVaccCount(boolean partial) {
		if(this.zipVaccMap!=null) {return this.zipVaccMap;}
		Date lastestTime = findLatestTime();
		this.zipVaccMap = new TreeMap<>();
		Date timeStamp;
		String zipCode;
		int Population;
		for(CovidData c: covidList) {
			timeStamp = c.getTimeStamp();
			zipCode = c.getZipCode();
			if(partial) {Population= c.getPartiallyVaccPopulation();
			}else {
				Population= c.getFullyVaccPopulation();
			}
			
			if(timeStamp.equals(lastestTime) && Population!=0) {
				if(populationMap.containsKey(zipCode)) {
					double vaccPerCapita = (double) Population/populationMap.get(zipCode);
					this.zipVaccMap.put(zipCode, vaccPerCapita);

				}
			}
		}
		return this.zipVaccMap;
	}

}
