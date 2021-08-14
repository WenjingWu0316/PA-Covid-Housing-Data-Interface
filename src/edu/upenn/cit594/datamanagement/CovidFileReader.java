package edu.upenn.cit594.datamanagement;

import java.util.List;

import edu.upenn.cit594.util.CovidData;

public interface CovidFileReader {//2 sub readers will implement CovidFileReader
	public List<CovidData>  getAllData();
}
