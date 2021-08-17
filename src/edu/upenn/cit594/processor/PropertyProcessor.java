package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.datamanagement.CSVFileReaderProperty;
import edu.upenn.cit594.logging.Logging;
import edu.upenn.cit594.util.Property;

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
	
	
	public double calcStatisticsbyZipcode(String zipcode, String type, String calcType) {
	
		double result = 0;
		
		List<Double> values = new ArrayList<Double>();
		
		Double value;
		
		if (type.equalsIgnoreCase("Market Value")) {
			for(Property property: this.Properties) {
				if (property.getZipcode().equals(zipcode) ) {
					if((value=property.getMarketvalue()) != null ) {
						values.add(value);
					}	
				}
			}
		}else if (type.equalsIgnoreCase("Total Livable Area")){
			for(Property property: this.Properties) {
				if (property.getZipcode().equals(zipcode)) {
					if((value=property.getTotalLivableArea()) != null ) {
						values.add(value);
					}						
				}
				
			}
		}
		
		double sum = 0;
		if(values.size()>0) {
			for(Double val: values) {
				sum += val;
			}
			
			if(calcType == "average") {
				result = sum/values.size();
			}else {
				result = sum;
			}
			
		}
		
		return result;		
	}
}
