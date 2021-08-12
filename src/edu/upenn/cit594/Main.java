package edu.upenn.cit594;

import java.io.File;

import java.io.IOException;

import edu.upenn.cit594.datamanagement.CSVFileReaderCovid;
import edu.upenn.cit594.datamanagement.JSONFileReaderCovid;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.TxtFileReader;
import edu.upenn.cit594.processor.CovidProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.ui.CommandLineUserInterface;


public class Main {
	//String[] args = {"covid_data.csv", "property.txt", "population.txt", "log.txt"};
	public static void main(String[] args) {
		if (args.length!=4) {
			System.out.println("ERROR: Command line input is incorrect");
			return;
		}
		String covidFileName = args[0];
		String propertyFileName = args[1];
		String populationFileName = args[2];
		String logFileName = args[3];
		
//		System.out.println(covidFileName+", "+populationFileName+", "+logFileName);
//		System.out.println(covidFileName.substring(covidFileName.length()-4));
		Reader readerForPopulation;
		Reader readerForCovid;
		//check whether tweet file is valid
		if(covidFileName.substring(covidFileName.length()-4).equals(".csv") ) {
			//System.out.println("tweetFile is a valid txt file");
			File file = new File(covidFileName);
			if(!file.exists() || !file.canRead()) {return;}
			readerForCovid = new CSVFileReaderCovid(covidFileName);
		}else if(covidFileName.substring(covidFileName.length()-5).equals(".json")) {
			//System.out.println("tweetFile is a valid json file");
			File file = new File(covidFileName);
			if(!file.exists() || !file.canRead()) {return;}
			readerForCovid = new JSONFileReaderCovid(covidFileName);
		}else {
			System.out.println("ERROR: covid file does not match a recognized extension");
			return;
		}
		
		//check whether states file is valid
		if(populationFileName.substring(populationFileName.length()-4).equals(".txt")) {
			//System.out.println("stateFile is a valid csv file");
			File file = new File(populationFileName);
			if(!file.exists() || !file.canRead()) {return;}
			readerForPopulation = new TxtFileReader(populationFileName);
		}else{
			System.out.println("ERROR: population file does not match a recognized extension");
			return;
		}
		
		//check whether log file is valid
		File file = new File(logFileName);
		if(!file.exists()) {//if file doesn't exist, create new file
			try {
				if (!file.createNewFile()) {//if can't create new file, return and terminate the program
					return;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {//if file does exist but can't be written, return and terminate the program
			if(!file.canWrite()) {return;}
		}
		
		PopulationProcessor populationProcessor = new PopulationProcessor(readerForPopulation);
		CovidProcessor covidProcessor = new CovidProcessor(populationProcessor, readerForCovid);
		CommandLineUserInterface ui = new CommandLineUserInterface(populationProcessor, covidProcessor);
		ui.start();
		
	}
}