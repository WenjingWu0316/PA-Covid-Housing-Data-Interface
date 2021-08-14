package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.logging.Logging;
import edu.upenn.cit594.util.CovidData;

public class JSONFileReaderCovid implements CovidFileReader{
	
protected String filename;
	
	public JSONFileReaderCovid(String name) {
		filename = name;
	}
	@Override
	public List<CovidData> getAllData(){
		String contentToLog =this.filename;
		Logging.logToFile(contentToLog);
		List<CovidData> covidList = new ArrayList<CovidData>();
		
		FileReader filereader=null;

		Object obj = null;
		
			try {
				filereader = new FileReader(filename);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			try {
				obj = new JSONParser().parse(filereader);
			} catch (IOException| ParseException e) {
	
				e.printStackTrace();
			}
			JSONArray ja = (JSONArray) obj;
			
			
			Object objLine;
			JSONObject jo;
			
			String zipCode;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date timeStamp = null;
			int parVacc;
			int fullVacc ;
			for(int i = 0; i<ja.size(); i++) { 
				objLine = ja.get(i);
				jo = (JSONObject) objLine;

				try {
					timeStamp = sdf.parse((String)jo.get("etl_timestamp"));
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				zipCode = String.valueOf(jo.get("zip_code")) ;

				if(jo.containsKey("partially_vaccinated")) {
					parVacc = (int)(long)jo.get("partially_vaccinated");
				}else {
					parVacc =0;
				}
				if(jo.containsKey("fully_vaccinated")) {
					fullVacc = (int)(long)jo.get("fully_vaccinated");
				}else {
					fullVacc =0;
				}
				CovidData c = new CovidData(timeStamp, zipCode, parVacc, fullVacc);

				covidList.add(c);
			
			}

		return covidList;
		
	}

}
