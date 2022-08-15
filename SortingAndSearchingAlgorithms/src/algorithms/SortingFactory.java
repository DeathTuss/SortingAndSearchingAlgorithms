package algorithms;

import java.util.List;

import algorithms.AbstractSort.*;

public class SortingFactory {

	
	public AbstractSort getSort(String sortAlgorithm, int[] randomArray) {
		// TODO Auto-generated constructor stub
		if(sortAlgorithm == null) {
			return null;
		}
		if(sortAlgorithm.contains("Bubble")) {
			return new BubbleSort(randomArray);
		}
		if(sortAlgorithm.contains("Selection")) {
			return new SelectionSort(randomArray);
		}
		if(sortAlgorithm.contains("Insertion")) {
			return new SelectionSort(randomArray);
		}
		return null;
	}

}
