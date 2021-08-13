package edu.upenn.cit594.util;

import java.util.Map;

public class Property {
	
	String zip_code;
	String building_code;
	Double total_livable_area;
	Double market_value;
		
	public Property(Map<String, String> singleLine) {
		
		// first five digits of zipcode
		String zipcode = singleLine.get("zip_code").strip();
		this.zip_code = zipcode.length() > 5 ? zipcode.substring(0, 5) : zipcode;

		this.building_code = singleLine.get("building_code").strip();
		
		try{
			this.total_livable_area = Double.parseDouble(singleLine.get("total_livable_area")) ;
			this.market_value = Double.parseDouble(singleLine.get("market_value"));	
		}catch(Exception e) {
			this.total_livable_area = null;
			this.market_value = null;			
        }
	}

	@Override
	public String toString() {
		return "\n zip_code = " + this.zip_code + ", building_code = " + this.building_code + 
				", total_livable_area = " + this.total_livable_area + ", market_value = " + this.market_value;
	}

	
	public String getZipcode() {
		return this.zip_code;
	}
	
	public String getBuildingcode() {
		return this.building_code;
	}
	
	public Double getTotalLivableArea() {
		return this.total_livable_area;
	}
	
	public Double getMarketvalue() {
		return this.market_value;
	}
	
	/*
	public double getValueByName (String name) {
		if(name == "Market Value") {
			return this.getMarketvalue();
		}else if(name == "Total Livable Area") {
			return this.getTotalLivableArea();
		}
		return -1;
	}
	*/
}
