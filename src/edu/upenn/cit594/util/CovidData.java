package edu.upenn.cit594.util;

import java.util.Date;

public class CovidData {
	private Date timeStamp;
	private String zipCode;
	private int partiallyVaccPopulation;
	private int fullyVaccPopulation;

	public CovidData(Date timeStamp, String zipCode, int partiallyVaccPopulation, int fullyVaccPopulation) {
		this.timeStamp = timeStamp;
		this.zipCode = zipCode;
		this.partiallyVaccPopulation = partiallyVaccPopulation;
		this.fullyVaccPopulation = fullyVaccPopulation;
	}
	
	public Date getTimeStamp() {return timeStamp;}
	public String getZipCode() {return zipCode;}
	public int getPartiallyVaccPopulation() {return partiallyVaccPopulation;}
	public int getFullyVaccPopulation() {return fullyVaccPopulation;}
}
