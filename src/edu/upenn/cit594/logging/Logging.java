package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logging {

	private static Logging instance = new Logging();
	private FileWriter out = null;

	private Logging() {
		
	}
	
	public static Logging getInstance() {	
		return instance;		 
	}
	
	public void setInstance(String filename) {
		
		File file = new File(filename);	
		try {
			this.setOut(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("The log file " + filename + " cannot be opened.");	
		} 
	}
	
	public void writeLog(String msg) {
		
		try {
			this.out.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeLog() {
		try {
			this.out.flush();
			this.out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setOut(FileWriter out) {
		this.out = out;
	}
	
	public FileWriter getOut() {
		return this.out;
	}
}
