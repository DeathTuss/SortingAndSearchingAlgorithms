package algorithms;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class MainFrame {
	/**
	 * 
	 */
	private String[] sortingOptions = {"Bubble sort", "Selection sort", "Insertion sort"};
	private HashMap<String, Boolean> searchingOptions;
	private static final int MIN = 0;
	private static final int MAX = 999;//9999;
	private int[] randomArray, sortedArray;
	private static final long serialVersionUID = 1L;
	private JFrame jFrame;
	private String titleString;
	private JTextArea sortResultTextArea, searchResultTextArea;
	private JComboBox<String> sortingComboBox, searchingComboBox;
	private JFormattedTextField inputSizeArea;
	private JLabel sizeTextJLabel;
	private GridBagLayout gridBag;
	private GridBagConstraints gridBagConstraints;
	private JButton okButton, cancelButton, clearButton;
	private NumberFormat numberFormat;
	private NumberFormatter numberFormatter;
	private SortingFactory sortingFactory = new SortingFactory();
	private SearchingFactory searchingFactory = new SearchingFactory();
	private Insets insets = new Insets(3,3,3,3);
	private int numberToFind;
	private ActionListener searchComboboxListener;
	private SwingWorker swingWorker;
	
	public MainFrame() {}
	
	private void fillSearchHashmap() {
		searchingOptions = new HashMap<String, Boolean>();
		// boolean represents if the array needs to be sorted

		searchingOptions.put("Binary search", true);
		searchingOptions.put("Linear search", false);
	}
	
	public void buildFrame() {
		jFrame = new JFrame();
		titleString = new String("Algoritmer");
		jFrame.setTitle(titleString);
		gridBag = new GridBagLayout();
		gridBagConstraints = new GridBagConstraints();
		jFrame.setLayout(gridBag);
		jFrame.setSize(700,600);
		interfaceLayout();
		jFrame.setVisible(true);
	}
	
	public void interfaceLayout() {
		
		sizeTextJLabel = new JLabel();		
		sizeTextJLabel.setText("Enter size of list ");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = insets;
		jFrame.add(sizeTextJLabel, gridBagConstraints);
		/* Setting the text field to only accept digits in a certain range and place
		it in JFrame using gridBagConstraint. */
		numberFormat = NumberFormat.getInstance();
		numberFormatter = new NumberFormatter(numberFormat);
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setMinimum(MIN);
		numberFormatter.setMaximum(MAX);
		numberFormatter.setAllowsInvalid(false);
		inputSizeArea = new JFormattedTextField(numberFormatter);
		inputSizeArea.setColumns(12);
		//inputSizeArea.setPreferredSize(new Dimension(10,20));
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = insets;
		jFrame.add(inputSizeArea, gridBagConstraints);
		
		
		
		okButton = new JButton("ok");
		okButton.addActionListener(event -> { fixRandomArray((int) inputSizeArea.getValue()); });
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = insets;
		jFrame.add(okButton, gridBagConstraints);	
		
		sortingComboBox = new JComboBox<>(sortingOptions);
		sortingComboBox.addActionListener(event -> {
			if(randomArray == null) {
				JOptionPane.showMessageDialog(jFrame, "Enter size of array first.");
			} else {
				startBackgroundTask("sort");
			}
			
		});
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = insets;
		jFrame.add(sortingComboBox, gridBagConstraints);
		
		sortResultTextArea = new JTextArea();
		sortResultTextArea.setEditable(false);
		sortResultTextArea.setPreferredSize(new Dimension(300, 400));
		sortResultTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = insets;
		jFrame.add(sortResultTextArea, gridBagConstraints);
		
		fillSearchHashmap();
		searchingComboBox = new JComboBox<String>();
		populateSearchCombobox(false);
		searchComboboxListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(randomArray == null) {
					JOptionPane.showMessageDialog(jFrame, "Enter size of array first.");
				} else {
					String input = JOptionPane.showInputDialog(jFrame, "Number between "+MIN+" and " + MAX +" to search for?", null);
					try {
						numberToFind = Integer.parseInt(input);
						startBackgroundTask("search");
					} catch (NumberFormatException e2) {
						// TODO: handle exception
						JOptionPane.showInternalMessageDialog(null, "Enter a number!");
					}
				}
				
			}
		};
		searchingComboBox.addActionListener(searchComboboxListener);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = insets;
		jFrame.add(searchingComboBox, gridBagConstraints);
		
		searchResultTextArea = new JTextArea();
		searchResultTextArea.setEditable(false);
		searchResultTextArea.setPreferredSize(new Dimension(300, 400));
		searchResultTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = insets;
		jFrame.add(searchResultTextArea, gridBagConstraints);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(event -> { stopProcess(); });
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 3;
	//	gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = insets;
		jFrame.add(cancelButton, gridBagConstraints);	
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(event -> { reset(); });
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 4;
	//	gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = insets;
		jFrame.add(clearButton, gridBagConstraints);	
		
	}
	
	private void stopProcess() {
		// TODO Auto-generated method stub
		if(swingWorker != null) {
			swingWorker.cancel(true);
		}
		
	}

	private void fixRandomArray(int size) {
		Random random = new Random();
		randomArray = new int[size];
		for(int i = 0; i < randomArray.length; i++) {
			randomArray[i] = random.nextInt(MAX - MIN) - MIN;
		}
	}
	 
	//------------------------------------------------------------------------
	// Populates the options for searching algorithms. Removes all items first 
	// to avoid duplicates.
	// the extra if statement 
	private void populateSearchCombobox(boolean needsSortedList) {
		searchingComboBox.removeActionListener(searchComboboxListener);
		searchingComboBox.removeAllItems();
		for(Map.Entry<String, Boolean> entry : searchingOptions.entrySet()) {
			if(entry.getValue() == needsSortedList) {
				searchingComboBox.addItem(entry.getKey());
			} if(needsSortedList == true && entry.getValue() == false) {
				searchingComboBox.addItem(entry.getKey());
			}
		}
		searchingComboBox.addActionListener(searchComboboxListener);
	}

	private void reset() {
		// TODO Auto-generated method stub
		randomArray = null;
		searchResultTextArea.setText("");
		sortResultTextArea.setText("");
		searchingComboBox.removeAllItems();
		populateSearchCombobox(false);
	}

	private void startBackgroundTask(String triggerEvent) {
		// TODO Auto-generated method stub
		swingWorker = new SwingWorker() {
			AbstractSort abstractSort;
			AbstractSearch abstractSearch;
			@Override
			protected Object doInBackground() throws Exception {
				if(triggerEvent.equals("sort")) {
					abstractSort = sortingFactory.getSort(sortingComboBox.getSelectedItem().toString(), randomArray);
					abstractSort.sortAlgorithm();
					return abstractSort;
				} else if(triggerEvent.equals("search")) {
					if(sortedArray == null) {
						abstractSearch = searchingFactory.getSearch(searchingComboBox.getSelectedItem().toString(), randomArray, numberToFind);
					} else {
						abstractSearch = searchingFactory.getSearch(searchingComboBox.getSelectedItem().toString(), sortedArray, numberToFind);
					}
					abstractSearch.searchAlgorithm();
					return abstractSearch;
					// TODO Auto-generated method stub
				}
				return null;
			}
			
			@Override 
			protected void done() {
				if(triggerEvent.equals("sort")) {
					try {
						abstractSort = (AbstractSort) get();
						sortedArray = abstractSort.getSortedList();
						sortResultTextArea.append(abstractSort.getAlgorithmName() + " time: " + abstractSort.getTime()+" milliseconds\n");
						populateSearchCombobox(true);
					} catch (InterruptedException | CancellationException | ExecutionException e ) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					
					for(int i = 0; i < sortedArray.length; i++) {
						System.out.println(sortedArray[i]);
					}
				} else if(triggerEvent.equals("search")) {
					try {
						abstractSearch = (AbstractSearch) get();
						if(abstractSearch.isNumberFound()) {
							searchResultTextArea.append(abstractSearch.getAlgorithmName() + " time: " + abstractSearch.getTime()+" milliseconds position: " +
									abstractSearch.getPosition()+ "\n");
						} else {
							searchResultTextArea.append(abstractSearch.getAlgorithmName() + " time: " + abstractSearch.getTime()+" milliseconds number not in list. \n");
						}
					} catch (InterruptedException | CancellationException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		};
		swingWorker.execute();
	}

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.buildFrame();
	}

}
