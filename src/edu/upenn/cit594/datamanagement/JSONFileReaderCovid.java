package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.util.CovidData;

public class JSONFileReaderCovid implements Reader{
	
protected String filename;
	
	public JSONFileReaderCovid(String name) {
		filename = name;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CovidData> getAllData(){
		//System.out.println("-----dataManagement-JSON: getAllData called");
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
			
			//time pattern /\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d\.\d+([+-][0-2]\d:[0-5]\d|Z)/
			Object objLine;
			JSONObject jo;
			String timeStamp;
			int zipCode;
			int parVacc;
			int fullVacc ;
			for(int i = 0; i<ja.size(); i++) { //
				objLine = ja.get(i);
				jo = (JSONObject) objLine;
				//System.out.println(jo.get("location"));
				timeStamp = (String) jo.get("etl_timestamp");
				zipCode = (int)(long)jo.get("zip_code") ;
				//System.out.println(jo.get("partially_vaccinated").equals(""));
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
//				System.out.println("timestamp is : "+timeStamp);
//				System.out.println("zipcode is : "+zipCode);
//				System.out.println("partialVacc is : "+parVacc);
//				System.out.println("fullVacc is : "+fullVacc);
				covidList.add(c);
			
			}

		return covidList;
		
	}

}
