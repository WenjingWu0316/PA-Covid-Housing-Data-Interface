package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.util.CovidData;

public class CSVFileReaderCovid implements Reader{
	
protected String filename;
	
	public CSVFileReaderCovid(String name) {
		filename = name;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CovidData> getAllData(){
		//System.out.println("-----dataManagement: getAllStates called");
		List<CovidData> covidList = new ArrayList<CovidData>();

		
		File file = new File(filename);
		
		//Initialize fileReader and bufferedReader
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		//String regex = "^([a-zA-Z ]*)\\,([0-9]*\\.?[0-9]+)\\,([+-]?[0-9]*\\.?[0-9]+)$";
		
		
		
		//Time stamp 
		//DateFormat f = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//find records on 20/05/2021 17:20

		
		//try-catch statement to catch IOException
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;
			String header;
			int columnNumber = 1;
			header = bufferedReader.readLine();
			String[] arrofStr = header.split(",");
			//System.out.println(header);
			Map<String, Integer> headerColMap = new HashMap<>();
			for(String s: arrofStr) {
				headerColMap.put(s, columnNumber++);
				//System.out.println(s);
				//System.out.println(columnNumber);
			}
			//System.out.println(headerColMap.size());
			//System.out.println(headerColMap.containsKey("\"etl_timestamp\""));
			
			int timestampCol = headerColMap.get("\"etl_timestamp\"");
			int zipcodeCol = headerColMap.get("\"zip_code\"");
			int partialVaccCol = headerColMap.get("\"partially_vaccinated\"");
			int fullVaccCol = headerColMap.get("\"fully_vaccinated\"");
			columnNumber--;
//			System.out.println("timestampCol is : "+timestampCol);
//			System.out.println("zipcodeCol is : "+zipcodeCol);
//			System.out.println("partialVaccCol is : "+partialVaccCol);
//			System.out.println("fullVaccCol is : "+fullVaccCol);
			String regex = "(.*)\\,";
			regex = regex.repeat(--columnNumber);
			//System.out.println(regex);
			regex = "^"+regex+"(.*)$";
			//System.out.println("****DEBUG: "+regex);
			Pattern p = Pattern.compile(regex);
			
			int partialVacc;
			int fullVacc;
			while((line = bufferedReader.readLine())!=null) {
				Matcher m = p.matcher(line);
				
				if(m.matches()) {
					String timeStamp = m.group(timestampCol);
					timeStamp = timeStamp.substring(1, timeStamp.length()-1);
					int zipCode = Integer.valueOf(m.group(zipcodeCol));
					partialVacc = (m.group(partialVaccCol).equals(""))? 0: Integer.parseInt(m.group(partialVaccCol));
					fullVacc = (m.group(fullVaccCol).equals(""))? 0: Integer.parseInt(m.group(fullVaccCol)); 
		
//					System.out.println("timestamp is : "+timeStamp);
//					System.out.println("zipcode is : "+zipCode);
//					System.out.println("partialVacc is : "+partialVacc);
//					System.out.println("fullVacc is : "+fullVacc);
					CovidData c = new CovidData(timeStamp, zipCode, partialVacc, fullVacc);
					covidList.add(c);}
			}
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		try {
			fileReader.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("First StateData is: "+stateList.get(0).getlatitude()+", "+stateList.get(0).getlongtitude()+", "+stateList.get(0).getstateName());
		return covidList;
	}

}
