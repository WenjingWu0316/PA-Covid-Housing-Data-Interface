package edu.upenn.cit594.util;

public class Population {
	
	private int zipCode;
	private int population;

	public Population(int zipCode, int population) {
		this.zipCode = zipCode;
		this.population = population;
	}
	
	public int getZipCode() {return zipCode;}
	public int getPopulation() {return population;}
}
