package edu.upenn.cit594.datamanagement;

import java.util.List;

public interface Reader {
	public <T> List<T>  getAllData();
}
