package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.logging.Logging;
import edu.upenn.cit594.util.CovidData;


public class CSVFileReaderCovid implements CovidFileReader{
	
protected String filename;
	
	public CSVFileReaderCovid(String name) {
		filename = name;
	}
	
	@Override
	public List<CovidData> getAllData(){
		
		String contentToLog =this.filename;
		Logging.logToFile(contentToLog);
		
		List<CovidData> covidList = new ArrayList<CovidData>();
		
		File file = new File(filename);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date timeStamp;

		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;
			String header;
			int columnNumber = 1;
			header = bufferedReader.readLine();
			String[] arrofStr = header.split(",");

			Map<String, Integer> headerColMap = new HashMap<>();
			for(String s: arrofStr) {
				headerColMap.put(s, columnNumber++);
		
			}
			
			int timestampCol = headerColMap.get("\"etl_timestamp\"");
			int zipcodeCol = headerColMap.get("\"zip_code\"");
			int partialVaccCol = headerColMap.get("\"partially_vaccinated\"");
			int fullVaccCol = headerColMap.get("\"fully_vaccinated\"");
			columnNumber--;

			String regex = "(.*)\\,";
			regex = regex.repeat(--columnNumber);

			regex = "^"+regex+"(.*)$";
			
			Pattern p = Pattern.compile(regex);
			
			int partialVacc;
			int fullVacc;
			String zipCode;
			while((line = bufferedReader.readLine())!=null) {
				Matcher m = p.matcher(line.trim());
				
				if(m.matches()) {
					timeStamp = sdf.parse(m.group(timestampCol));
					zipCode = m.group(zipcodeCol);
					partialVacc = (m.group(partialVaccCol).equals(""))? 0: Integer.parseInt(m.group(partialVaccCol));
					fullVacc = (m.group(fullVaccCol).equals(""))? 0: Integer.parseInt(m.group(fullVaccCol)); 
		

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
		return covidList;
	}

}
