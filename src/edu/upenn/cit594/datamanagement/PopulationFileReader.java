package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.util.Population;

public class PopulationFileReader{
protected String filename;
	
	public PopulationFileReader(String name) {
		filename = name;
	}
	public List<Population> getAllData(){
		//System.out.println("-----TxtFileReader: getAllData called");
		List<Population> populationList = new ArrayList<Population>();
		
		//Read the file
		File file = new File(filename); 
		
		//Initialize fileReader and bufferedReader
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String regex = "^([0-9]*)\s([0-9]*)$";
		Pattern p = Pattern.compile(regex);
		
		
		//try-catch statement to catch IOException
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while((line = bufferedReader.readLine())!=null) {
				Matcher m = p.matcher(line.trim());
				if(m.matches()) {
				
				String zipCode = m.group(1);
				int population = Integer.valueOf(m.group(2));
				Population pop = new Population(zipCode, population);
				populationList.add(pop);}
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
		//System.out.println("First TweetData is: "+tweetList.get(0).getLatitude()+", "+tweetList.get(0).getLongtitude()+", "+tweetList.get(0).getText());
		return populationList;
	}

}
