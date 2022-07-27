package algorithms;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import javax.swing.JFormattedTextField;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.DirectoryStream.Filter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final int MIN = 0;
	private static final int MAX = 99999999;
	private List<Integer> randomList;
	private static final long serialVersionUID = 1L;
	private BorderLayout bLayout;
	private JFrame jFrame;
	private String titleString;
	private JFormattedTextField inputSizeArea;
	private JLabel sizeTextJLabel;
	private GridBagLayout gridBag;
	private GridBagConstraints gridBagConstraints;
	private JButton okButton;
	private NumberFormat numberFormat;
	private NumberFormatter numberFormatter;
	
	public MainFrame() {
		
	}
	
	public void buildFrame() {
		jFrame = new JFrame();
		titleString = new String("Algoritmer");
		setTitle(titleString);
		bLayout = new BorderLayout();
		gridBag = new GridBagLayout();
		gridBagConstraints = new GridBagConstraints();
		jFrame.setLayout(bLayout);
		jFrame.setSize(600,700);
		
		//jFrame.pack();
		topPanel();
		jFrame.setVisible(true);
	}
	
	public void topPanel() {
		numberFormat = NumberFormat.getInstance();
		numberFormatter = new NumberFormatter(numberFormat);
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setMinimum(MIN);
		numberFormatter.setMaximum(MAX);
		numberFormatter.setAllowsInvalid(false);
		inputSizeArea = new JFormattedTextField(numberFormatter);
		inputSizeArea.setColumns(12);
		sizeTextJLabel = new JLabel();
		okButton = new JButton("ok");
		okButton.addActionListener(e -> { fixRandomList((int) inputSizeArea.getValue()); });
		JPanel topP = new JPanel(new FlowLayout());
		sizeTextJLabel.setText("Enter size of list ");
		topP.add(sizeTextJLabel);
	//	System.out.println(inputSizeArea);
		topP.add(inputSizeArea);
		topP.add(okButton);
		jFrame.add(topP, BorderLayout.PAGE_START);
		
	}
	
	


	private void fixRandomList(int size) {
		// TODO Auto-generated method stub
		randomList = new Random().ints(size, MIN, MAX)
		        .boxed().collect(Collectors.toList());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame mainFrame = new MainFrame();
		mainFrame.buildFrame();
	}

}
