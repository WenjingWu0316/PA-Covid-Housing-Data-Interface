package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.List;

import edu.upenn.cit594.logging.Logging;
import edu.upenn.cit594.util.Property;
import edu.upenn.cit594.datamanagement.CSVFileReaderProperty;

public class PropertyProcessor {
	
	protected CSVFileReaderProperty csvPropertyReader;
	protected List<Property> Properties;
	
	protected Logging logging;
	
	public PropertyProcessor(CSVFileReaderProperty csvPropertyReader, Logging logging) {
		this.csvPropertyReader = csvPropertyReader;
		this.Properties = this.csvPropertyReader.getProperties();	
		this.logging = logging;		
	}
	
	public List<Property> getListofProperties() {
		return this.Properties;
	}
	
	public Logging getLog() {
		return this.logging;
	}
	
	public double calcAveragebyZipcode(String zipcode, String type) {
		double avg = 0;
		
		List<Double> values = new ArrayList<Double>();
		if (type.equals("Market Value")) {
			for(Property property: this.Properties) {
				if (property.getZipcode().equals(zipcode)) {
					values.add(property.getMarketvalue());
				}
			}
		}else if (type.equals("Total Livable Area")){
			for(Property property: this.Properties) {
				if (property.getZipcode().equals(zipcode)) {
					values.add(property.getTotalLivableArea());
				}
			}
		}
		
		double sum = 0;
		for(Double value: values) {
			sum += value;
		}
		
		avg = sum/values.size();
		System.out.println(zipcode + avg);
			
		return avg;		
	}
}
