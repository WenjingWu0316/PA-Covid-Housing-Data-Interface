package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logging {

	private static Logging instance = new Logging();
	private PrintWriter out = null;

	private Logging() {
		
	}
	
	public static Logging getInstance() {	
		return instance;		 
	}
	
	public void setInstance(String filename) {
		if(this.out!=null) {out.close();}
		File file = new File(filename);	
		try {
			this.setOut(new PrintWriter(new FileWriter(file, true)));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("The log file " + filename + " cannot be opened.");	
		} 
	}
	
	public void writeLog(String msg) {
		this.out.println(msg);
		this.out.flush();
	}
	
	public void closeLog() {
		this.out.flush();
		this.out.close();
	}
	
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	
	public PrintWriter getOut() {
		return this.out;
	}
	
	
	public static void logToFile(String content) {
		Logging logging = Logging.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date timeStamp = new Date(System.currentTimeMillis());
		String currentTime = sdf.format(timeStamp);
		String contentToLog =currentTime+" "+ content;
		logging.writeLog(contentToLog);
	}
}
