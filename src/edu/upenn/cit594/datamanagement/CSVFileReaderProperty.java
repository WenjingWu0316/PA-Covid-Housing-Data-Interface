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

public class CSVFileReaderProperty {
	
	protected String filename;
	
	public CSVFileReaderProperty(String name) {
		this.filename = name;
	}
	
	public ArrayList<String> parseOneLine(String line){
		
		final int START = 0;
		final int SEARCH = 1;
		final int FIELD = 2;
		final int NEXT_STATE = 3;
		
		int state = START;
		
		StringBuilder accum = null;
		ArrayList<String> record = null;
		
		//System.out.println("\nThis line is:  " + this.line);
		
		state = START;
		record = new ArrayList<String>();
		
		for(byte b: line.getBytes()) {
			
			char c = (char) b;
			
			//System.out.println("beginning state = "+ state + ", char = " + c+ ", accum = " + accum);
			
			switch(state) {
			case START:
				switch(c) {
				case ',':
					record.add("");
					continue;
				case '\"':
					accum = new StringBuilder();
					accum.append(c); // Added by Wu
					state = SEARCH;
					continue; // Added by Wu
				default:
					accum = new StringBuilder();
					accum.append(c); // Added by Wu
					state = FIELD;
					continue;	
				}	
			case SEARCH: // 1
				switch(c) {
				case '\"':
					state = NEXT_STATE;
					//state = START;
					//continue;				
				default:
					accum.append(c);
					continue;	// Added by Wu
				}
			case FIELD: // 2 (not , not ")
				switch(c) {
				case ',':
					record.add(accum.toString());
					//System.out.println(", accum = " + accum);
					state = START;
					continue;
				default:
					accum.append(c);
					continue;
				}
			case NEXT_STATE:
				switch(c) {
				case ',':
					record.add(accum.toString());
					state = START;
					continue;
				case '\"':
					state = SEARCH;
					accum.append('\"');	
					continue;
				}
			}
		}
		record.add(accum.toString()); // Add the last item. Added by Wu
		//System.out.println("----------->: " + record);
		
		return record;
	}
	

	public List<Property> getProperties() {

		if(!this.filename.endsWith(".csv")) {
			throw new IllegalArgumentException("The file has to be in the .csv format.");	
		}
		
        List<Property> Properties = new ArrayList<Property>();
     
        FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String line = null;
		
    	try {
    		fileReader = new FileReader(this.filename);
        	bufferedReader = new BufferedReader(fileReader);

        	String header = bufferedReader.readLine();
			ArrayList<String> headerTokens = parseOneLine(header);
			
			ArrayList<String> columns = new ArrayList<String>( Arrays.asList("zip_code", "building_code", "total_livable_area", "market_value"));
			Map<String, Integer> columnIndex = new HashMap<String, Integer>();
			for (String col: columns) {
				if(headerTokens.contains(col)) {
					columnIndex.put(col, headerTokens.indexOf(col));
				}else {
					columnIndex.put(col, -1);
				}
			}
			//System.out.println("The columns: " + columnIndex);
			ArrayList<String> tokens;
			Property property;
			Map<String, String> singleMap;
			
    		while ((line = bufferedReader.readLine()) != null) {
    			
    			//parseObj= new ParseLine(line);
    			//tokens = parseObj.doParseLine();
    			tokens = parseOneLine(line);
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
    	//System.out.println("Properties: " + Properties);
		return Properties;
	}
}
