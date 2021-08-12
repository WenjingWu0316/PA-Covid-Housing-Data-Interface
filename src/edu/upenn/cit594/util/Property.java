package edu.upenn.cit594.util;

import java.util.Map;

public class Property {
	
	// zip_code, total_livable_area, market_value, building_code
	String zip_code;
	String building_code;
	Double total_livable_area;
	Double market_value;
	
	

	public Property(Map<String, String> singleLine) {
		this.zip_code = singleLine.get("zip_code").strip().substring(0, 5); // first five digits of zip code
		this.building_code = singleLine.get("building_code").strip();
		this.total_livable_area = Double.parseDouble(singleLine.get("total_livable_area")) ;
		this.market_value = Double.parseDouble(singleLine.get("market_value"));	
	}
	

	@Override
	public String toString() {
		return "\n zip_code = " + this.zip_code + ", building_code = " + this.building_code + 
				", total_livable_area = " + this.total_livable_area + ", market_value = " + this.market_value;
	}
	
	
}
