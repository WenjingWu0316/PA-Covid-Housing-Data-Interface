package edu.upenn.cit594.processor;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.util.Population;

public class PopulationProcessor {
	
	
	List<Population> populationList;
	

	public PopulationProcessor(PopulationFileReader reader) {
		populationList = reader.getAllData();
		
		}
	
	
	public Map<String, Integer> getPopulationMap(){
		Map<String, Integer> populationMap = new HashMap<>();
		for(Population p:populationList) {
			populationMap.put(p.getZipCode(), p.getPopulation());
		}
		return populationMap;
	}
	
	public int getAllPopulation() {
		int totalPopulation = 0;
		for(Population p : populationList) {
			totalPopulation += p.getPopulation();
		}
		return totalPopulation;
	}
	
	
}
