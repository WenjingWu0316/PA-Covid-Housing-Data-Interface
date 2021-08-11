package edu.upenn.cit594.util;

public class CovidData {
	private String timeStamp;
	private int zipCode;
	private int partiallyVaccPopulation;
	private int fullyVaccPopulation;

	public CovidData(String timeStamp, int zipCode, int partiallyVaccPopulation, int fullyVaccPopulation) {
		this.timeStamp = timeStamp;
		this.zipCode = zipCode;
		this.partiallyVaccPopulation = partiallyVaccPopulation;
		this.fullyVaccPopulation = fullyVaccPopulation;
	}
	
	public String getTimeStamp() {return timeStamp;}
	public int getZipCode() {return zipCode;}
	public int getPartiallyVaccPopulation() {return partiallyVaccPopulation;}
	public int getFullyVaccPopulation() {return fullyVaccPopulation;}
}
