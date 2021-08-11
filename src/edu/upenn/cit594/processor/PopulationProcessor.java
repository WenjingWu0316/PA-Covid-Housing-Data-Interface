package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.util.CovidData;
import edu.upenn.cit594.util.Population;

public class PopulationProcessor {
	
	
	List<Population> populationList;
	

	public PopulationProcessor(Reader reader) {
		populationList = reader.getAllData();
		
		}
	
	
	public Map<Integer, Integer> getPopulationMap(){
		Map<Integer, Integer> populationMap = new HashMap<>();
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
