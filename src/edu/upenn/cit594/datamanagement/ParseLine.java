package edu.upenn.cit594.datamanagement;
import java.util.ArrayList;

public class ParseLine {
	public static final int START = 0;
	public static final int SEARCH = 1;
	public static final int FIELD = 2;
	public static final int NEXT_STATE = 3;
	
	protected String line;
	
	public ParseLine(String line) {
		this.line = line;
	}
   
	public ArrayList<String> doParseLine() throws Exception{
		
		int state = START;
		
		StringBuilder accum = null;
		ArrayList<String> record = null;
		
		//System.out.println("\nThis line is:  " + this.line);
		
		state = START;
		record = new ArrayList<String>();
		
		for(byte b: this.line.getBytes()) {
			
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
	

}
