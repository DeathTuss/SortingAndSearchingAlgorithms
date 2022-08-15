package algorithms;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

abstract class AbstractSort {
	
	protected int[] integerArray;
	protected Instant timerStartInstant, timerStopInstant;
	private String algorithmNameString;
	
	protected AbstractSort(String algorithmNameString, int[] randomArray) {
		this.algorithmNameString = algorithmNameString;
		this.integerArray = randomArray;
	}

	abstract void sortAlgorithm();
	
	protected String getAlgorithmName() {
		return this.algorithmNameString;
	}
	
	protected int[] getSortedList() {
		return this.integerArray;
	}
	
	protected long getTime() {
		return Duration.between(timerStartInstant, timerStopInstant).toMillis();
	}
	
	
	public static class BubbleSort extends AbstractSort {
		
		public BubbleSort(int[] randomArray) {
			super("Bubble sort", randomArray);
		}

		@Override
		public void sortAlgorithm() {
			// TODO Auto-generated method stub
			timerStartInstant = Instant.now();
			int arraySize = integerArray.length;
			for(int i = 0; i < arraySize -1; i++) {
				for(int j = 0; j < arraySize - i -1; j++) {
					if(integerArray[j] > integerArray[j+1]) {
						int temp = integerArray[j];
						integerArray[j] = integerArray[j+1];
						integerArray[j+1] = temp;
					}
				}
			}
			timerStopInstant = Instant.now();
		}
	}
	
	public static class SelectionSort extends AbstractSort {
			
			public SelectionSort(int[] randomArray) {
				super("Selection sort", randomArray);
			}
	
			@Override
			public void sortAlgorithm() {
				// TODO Auto-generated method stub
				timerStartInstant = Instant.now();
				int arraySize = integerArray.length;
				for(int i = 0; i < arraySize -1; i++) {
					int minIndex = i;
					for(int j = i+1; j < arraySize; j++) {
						if(integerArray[minIndex] > integerArray[j]) {
							minIndex = j;
						}
					}
					int temp = integerArray[minIndex];
					integerArray[minIndex] = integerArray[i];
					integerArray[i] = temp;
				}
				timerStopInstant = Instant.now();
			}
		}
	public static class InsertionSort extends AbstractSort {
		
		public InsertionSort(int[] randomArray) {
			super("Insertion sort", randomArray);
		}

		@Override
		public void sortAlgorithm() {
			// TODO Auto-generated method stub
			timerStartInstant = Instant.now();
			int arraySize = integerArray.length;
			for(int i = 1; i < arraySize; i++) {
				int j = i -1;
				int target = integerArray[i];
				
				while(j >= 0 && integerArray[j] > target) {
					integerArray[j+1] = integerArray[j];
					j--;
				}
				integerArray[j+1] = target;
			}
			timerStopInstant = Instant.now();
		}
	}
}
