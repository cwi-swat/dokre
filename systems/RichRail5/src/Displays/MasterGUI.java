package Displays;

import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
//import javax.swing.JTextField;
//import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import TaskSpecific.*;

public class MasterGUI extends JFrame {

	private DataEntry DE;
	private Controller controller = Controller.getInstance();
	DrawningDisplay DD;
	LogDisplay LD;
	TextDisplay TD;
	GridLayout grid = new GridLayout(0,2);
	private JPanel upPanel, leftPanel, rightPanel, bottomLeftPanel, bottomRightPanel = new JPanel();
	private JButton btnNewDrawingDisplay, btnNewLogDisplay, btnNewTextDisplay, btnHelp;

	public MasterGUI(){

		GridBagLayout thisLayout = new GridBagLayout();
		
		getContentPane().add(new JScrollPane(), BorderLayout.CENTER);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		thisLayout.rowHeights = new int[] {5, 5, 5, 5};
		thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
		thisLayout.columnWidths = new int[] {7, 7, 7, 7};
		

		//upPanel(Drawing display)
		getContentPane().setLayout(thisLayout);
		{	

			upPanel = new JPanel();
			upPanel.setLayout(new BorderLayout());

			getContentPane().add(upPanel, new GridBagConstraints(0, 0, 4, 3, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			DD = new DrawningDisplay(upPanel);
			upPanel.add(DD,BorderLayout.CENTER);
			
			setResizable(false);

		}


		//leftPanel (textDisplay)
		leftPanel = new JPanel();
		GridBagLayout jPanel2Layout = new GridBagLayout();
		//jPanel2.setLayout(null);
		leftPanel.setLayout(jPanel2Layout);
		getContentPane().add(leftPanel, new GridBagConstraints(0, 3, 2, 2, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		{

//			leftPanel.setBounds(10, 10, 100, 15);
//			jPanel2Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
//			jPanel2Layout.rowHeights = new int[] {7, 7, 7, 7};
//			jPanel2Layout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
//			jPanel2Layout.columnWidths = new int[] {7, 7, 7, 7};

		}

		TD = new TextDisplay(leftPanel);
		leftPanel.add(TD);

		//rightPanel (logDisplay)
		rightPanel = new JPanel();
		GridBagLayout jlayout = new GridBagLayout();
		//jPanel2.setLayout(null);
		rightPanel.setLayout(jlayout);
		getContentPane().add(rightPanel, new GridBagConstraints(2, 3, 2, 2, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		{

//			rightPanel.setBounds(10, 10, 100, 15);
//			rightPanel.setBounds(5, 5, 5, 5);
//			jlayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
//			jlayout.rowHeights = new int[] {7, 7, 7, 7};
//			jlayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
//			jlayout.columnWidths = new int[] {7, 7, 7, 7};

		}

		LD = new LogDisplay(rightPanel);
		rightPanel.add(LD);


		//bottomPanel (dataEntry)
		bottomLeftPanel = new JPanel();
		GridBagLayout jlayout2 = new GridBagLayout();
		//jPanel2.setLayout(null);
		bottomLeftPanel.setLayout(jlayout);
		getContentPane().add(bottomLeftPanel, new GridBagConstraints(0, 4, 2, 2, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		{

//			bottomLeftPanel.setBounds(10, 10, 100, 15);
//			jlayout2.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
//			jlayout2.rowHeights = new int[] {7, 7, 7, 7};
//			jlayout2.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
//			jlayout2.columnWidths = new int[] {7, 7, 7, 7};

		}

		DE = new DataEntry(bottomLeftPanel);

		
		
		//bottomPanel (newPanel)
		bottomRightPanel = new JPanel();
		GridBagLayout jlayout3 = new GridBagLayout();
		//jPanel2.setLayout(null);
		bottomRightPanel.setLayout(jlayout);
		getContentPane().add(bottomRightPanel, new GridBagConstraints(2, 4, 2, 2, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		{

			bottomRightPanel.setBounds(10, 10, 100, 15);
//			jlayout3.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
//			jlayout3.rowHeights = new int[] {7, 7, 7, 7};
//			jlayout3.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
//			jlayout3.columnWidths = new int[] {7, 7, 7, 7};
			btnNewDrawingDisplay = new JButton("New Drawing Display");
			btnNewLogDisplay  = new JButton("New Log Display");
			btnNewTextDisplay = new JButton("New Text Display");
			btnHelp = new JButton("Command help");
			btnNewDrawingDisplay.addActionListener(actionListn1);
			btnNewLogDisplay.addActionListener(actionlistn2);
			btnNewTextDisplay.addActionListener(actionlistn3);
//			bottomRightPanel.add(btnNewDrawingDisplay);
			bottomRightPanel.add(btnNewLogDisplay);
//			bottomRightPanel.add(btnNewTextDisplay);
		}
		DD.setController(controller);
		LD.setController(controller);
		TD.setController(controller);
		pack();
		setSize(1000, 700);
		show();
		 fillTemporaryNewCommans();
	}
	
	private void fillTemporaryNewCommans(){
		controller.newTrain("sprinter");
//		controller.newTrain("sprinterxl");
		controller.newWagonType("smallwagon", 20);
		controller.newWagonType("normalwagon", 30);
		controller.newWagonType("bigwagon", 50);
		controller.newWagon(1111, "smallwagon");
		controller.newWagon(2222, "smallwagon");
		controller.newWagon(3333, "normalwagon");
		controller.newWagon(4444, "normalwagon");
		controller.newWagon(5555, "bigwagon");
		controller.newWagon(6666, "bigwagon");
	}
	
	ActionListener actionListn1 = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// Drawing display
			
				JFrame f = new JFrame();
				JPanel p = new JPanel();
				DrawningDisplay dp = new DrawningDisplay(p);
				f.add(dp);
				f.setSize(1000, 315);
				f.show();
				f.setResizable(false);

		}
	};
	
	ActionListener actionlistn2 = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// log display
			
			JFrame f = new JFrame();
			JPanel p = new JPanel();
			LogDisplay dp = new LogDisplay(p);
			f.add(dp);
			f.setSize(440, 300);
			f.show();
			f.setResizable(false);
		}
	};
	
	ActionListener actionlistn3 = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// text display
			
			JFrame f = new JFrame();
			JPanel p = new JPanel();
			TextDisplay tp = new TextDisplay(p);
			f.add(tp);
			f.setSize(440, 300);
			f.show();
			f.setResizable(false);

		}
	};

}

