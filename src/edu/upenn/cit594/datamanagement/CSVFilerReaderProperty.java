package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.util.Property;

public class CSVFilerReaderProperty {
	
	protected String filename;
	
	public CSVFilerReaderProperty(String name) {
		this.filename = name;
	}

	public List<Property> getProperties() {

		if(!this.filename.endsWith(".csv")) {
			throw new IllegalArgumentException("The file has to be in the .csv format.");	
		}
		
        List<Property> Properties = new ArrayList<Property>();
     
        FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		ParseLine parseObj = null;

		String line = null;
		
    	try {
    		fileReader = new FileReader(this.filename);
        	bufferedReader = new BufferedReader(fileReader);

        	String header = bufferedReader.readLine();
        	parseObj= new ParseLine(header);
			ArrayList<String> headerTokens = parseObj.doParseLine();
			
			ArrayList<String> columns = new ArrayList<String>( Arrays.asList("zip_code", "building_code", "total_livable_area", "market_value"));
			Map<String, Integer> columnIndex = new HashMap<String, Integer>();
			for (String col: columns) {
				if(headerTokens.contains(col)) {
					columnIndex.put(col, headerTokens.indexOf(col));
				}else {
					columnIndex.put(col, -1);
				}
			}
			System.out.println("The columns: " + columnIndex);
			ArrayList<String> tokens;
			Property property;
			Map<String, String> singleMap;
			
    		while ((line = bufferedReader.readLine()) != null) {
    			parseObj= new ParseLine(line);
    			tokens = parseObj.doParseLine();

    			singleMap = new HashMap<String, String>();
    			for ( String col: columns) {
    				if( columnIndex.get(col) != -1) {
    					singleMap.put(col, tokens.get(columnIndex.get(col)));
    				}else {
    					singleMap.put(col, null);
    				}
    			}
    			//System.out.println("The property: " + singleMap);
    			property = new Property(singleMap);
    			Properties.add(property);

    			
    		}     		
		}catch (Exception e) {
            e.printStackTrace();
        } finally{
        	try {
        		bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  
    	System.out.println("Properties: " + Properties);
		return Properties;
	}
}
