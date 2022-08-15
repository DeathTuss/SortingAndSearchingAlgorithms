package algorithms;

import java.util.List;

import algorithms.AbstractSearch.BinarySearch;
import algorithms.AbstractSearch.LinearSearch;
import algorithms.AbstractSort.BubbleSort;

public class SearchingFactory {

	public AbstractSearch getSearch(String searchAlgorithm, int[] integerArray, int numberToFind) {
		// TODO Auto-generated constructor stub
		if(searchAlgorithm == null) {
			return null;
		}
		if(searchAlgorithm.contains("Binary")) {
			return new BinarySearch(integerArray, numberToFind);
		}
		if(searchAlgorithm.contains("Linear")) {
			return new LinearSearch(integerArray, numberToFind);
		}
		return null;
	}


}
