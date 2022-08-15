package algorithms;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

abstract class AbstractSearch {

	protected int[] integerArray;
	protected Instant timerStartInstant, timerStopInstant;
	protected String algorithmNameString;
	protected int foundAtPosition;
	protected int numberToFind;
	protected boolean numberFound;
	
	protected AbstractSearch(String algorithmNameString, int[] integerArray, int numberToFind) {
		this.algorithmNameString = algorithmNameString;
		this.integerArray = integerArray;
		this.numberToFind = numberToFind;
	}

	abstract void searchAlgorithm();
	
	protected boolean isNumberFound() {
		return this.numberFound;
	}
	
	protected String getAlgorithmName() {
		return this.algorithmNameString;
	}
	
	protected int getPosition() {
		return this.foundAtPosition;
	}
	
	protected int getNumberToFind() {
		return this.numberToFind;
	}
	
	protected long getTime() {
		return Duration.between(timerStartInstant, timerStopInstant).toMillis();
	}
	
	
	public static class BinarySearch extends AbstractSearch {
		private int middle;

		public BinarySearch(int[] integerArray, int numberToFind) {
			super("Binary search", integerArray, numberToFind);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void searchAlgorithm() {
			this.numberFound = false;
			timerStartInstant = Instant.now();
			this.foundAtPosition = BinarySearchRecursive(integerArray, numberToFind, 0, integerArray.length);
			timerStopInstant = Instant.now();
		}
		
		private int BinarySearchRecursive(int[] integerArray, int numberToFind, int low, int high) {
			
			if(low <= high) {
				middle = (low + high) / 2;
				if(integerArray[middle] == numberToFind) {
					this.numberFound = true;
					return middle;
				} else if(integerArray[middle] > numberToFind) {
					return BinarySearchRecursive(integerArray, numberToFind, low, middle-1);
				} else {
					return BinarySearchRecursive(integerArray, numberToFind, middle+1, high);
				}
			}
			return -1;
		}
	}

	public static class LinearSearch extends AbstractSearch {
		private int middle;

		public LinearSearch(int[] integerArray, int numberToFind) {
			super("Linear search", integerArray, numberToFind);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void searchAlgorithm() {
			timerStartInstant = Instant.now();
			for(int i = 0; i < integerArray.length; i++) {
				if(integerArray[i] == numberToFind) {
					this.numberFound = true;
					this.foundAtPosition = i;
					break;
				}
			}
			timerStopInstant = Instant.now();
		}
	}	
}
