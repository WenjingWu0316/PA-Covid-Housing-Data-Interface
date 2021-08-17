package edu.upenn.cit594.processor;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.util.Population;

public class PopulationProcessor {
	
	
	List<Population> populationList;
	int totalPopulation;//int for memoization
	Map<String, Integer> populationMap;//Map for memoization

	public PopulationProcessor(PopulationFileReader reader) {
		populationList = reader.getAllData();
		totalPopulation = 0;
		populationMap = null;
		}
	
	
	public Map<String, Integer> getPopulationMap(){
		if(this.populationMap!=null) {return this.populationMap;}
		this.populationMap = new HashMap<>();
		for(Population p:populationList) {
			this.populationMap.put(p.getZipCode(), p.getPopulation());
		}
		return this.populationMap;
	}
	
	public int getAllPopulation() {
		if(this.totalPopulation!=0) {return this.totalPopulation;}
		int total = 0;
		for(Population p : populationList) {
			total += p.getPopulation();
		}
		this.totalPopulation = total;
		return total;
	}
	
	public int getPopulationByZipcode(String zipcode) {
		Map<String, Integer> populationMap = this.getPopulationMap();
		if(populationMap.containsKey(zipcode)) {
			return populationMap.get(zipcode);
		}
		return 0;
	}
	
	
}
