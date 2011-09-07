/*
package GUI;

import interpreter.DSLOperator_addWagon;
import interpreter.DSLOperator_delete;
import interpreter.DSLOperator_getSeats;
import interpreter.DSLOperator_newTrain;
import interpreter.DSLOperator_newWagon;
import interpreter.DSLOperator_removeWagon;
import interpreter.DSLmessageHandler;

import java.awt.GridLayout;

import javax.swing.JFrame;

import GUI.input.GUIfileInput;
import GUI.input.GUItextInput;
import GUI.output.GUITextOverview;
import GUI.output.GUIeventLog;
import GUI.output.GUIfileOutput;
import GUI.output.GUIimageOutput;
import datahandler.DataHandler;
import datahandler.Memento;

public class Frame {
	public Frame(){		
		createMainFrame();
	}
	
	@SuppressWarnings("unused")
	private void createMainFrame() {
		Memento memento = new Memento();
		
		DataHandler datahandler = new DataHandler(memento);
		DSLmessageHandler messageHandler = new DSLmessageHandler(datahandler);
		messageHandler.addOperator(new DSLOperator_addWagon());
		messageHandler.addOperator(new DSLOperator_delete());
		messageHandler.addOperator(new DSLOperator_getSeats());
		messageHandler.addOperator(new DSLOperator_newTrain());
		messageHandler.addOperator(new DSLOperator_newWagon());
		messageHandler.addOperator(new DSLOperator_removeWagon());		
		
		JFrame mainFrame = new JFrame("Patterns and Frameworks Assignment 2");
		GridLayout grid = new GridLayout(0,2);
		mainFrame.setLayout(grid);
		mainFrame.setSize(800,700);
		mainFrame.setVisible(true);		 
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GUITextOverview overview = new GUITextOverview();
		mainFrame.getContentPane().add(overview);
		
		GUIeventLog log = new GUIeventLog();
		mainFrame.getContentPane().add(log);
				
		GUItextInput input = new GUItextInput(messageHandler);		
		mainFrame.getContentPane().add(input);
		
		GUIimageOutput image = new GUIimageOutput();
		mainFrame.getContentPane().add(image);
		
		GUIfileOutput fileOutput = new GUIfileOutput();
		
		datahandler.addOutput(log);
		datahandler.addOutput(fileOutput);
		datahandler.addOutput(overview);
		datahandler.addOutput(image);
		
		//Uncomment this for an extra view to demonstrate the observer pattern
//		JFrame extraFrame1 = new JFrame();
//		extraFrame1.setLayout(new FlowLayout());
//		GUITextOverview extraTextOverview = new GUITextOverview();
//		extraFrame1.add(extraTextOverview.getPanel());
//		extraFrame1.setSize(150,150);
//		extraFrame1.setVisible(true);
//		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
//		datahandler.addOutput(extraTextOverview);
		
		GUIfileInput fileInput = new GUIfileInput(messageHandler);	
		datahandler.notifyOutputs();
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Frame mainFrame = new Frame();

	}
}
*/
package GUI;

import interpreter.DSLOperator_addWagon;
import interpreter.DSLOperator_delete;
import interpreter.DSLOperator_getSeats;
import interpreter.DSLOperator_newTrain;
import interpreter.DSLOperator_newWagon;
import interpreter.DSLOperator_removeWagon;
import interpreter.DSLmessageHandler;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import GUI.input.GUIfileInput;
import GUI.input.GUItextInput;
import GUI.output.GUITextOverview;
import GUI.output.GUIeventLog;
import GUI.output.GUIfileOutput;
import GUI.output.GUIimageOutput;
import datahandler.DataHandler;

public class Frame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1172387443238878450L;

	private ArrayList<JFrame> frames;

	private JComboBox frameList;
	private JButton buttonTextInput, buttonEventLog, buttonImageOutput, buttonTextOverview;
	private JButton newFrameButton;
	private ButtonGroup locationCheckboxGroup;
	private JRadioButton radioTopLeft, radioTopRight, radioBottomLeft, radioBottomRight;

	private DSLmessageHandler messageHandler;
	private DataHandler datahandler;

	public Frame(){		
		createMainFrame();
	}
	
	@SuppressWarnings("unused")
	private void createMainFrame() {

		
		datahandler = new DataHandler();
		messageHandler = new DSLmessageHandler(datahandler);
		messageHandler.addOperator(new DSLOperator_addWagon());
		messageHandler.addOperator(new DSLOperator_delete());
		messageHandler.addOperator(new DSLOperator_getSeats());
		messageHandler.addOperator(new DSLOperator_newTrain());
		messageHandler.addOperator(new DSLOperator_newWagon());
		messageHandler.addOperator(new DSLOperator_removeWagon());		
		
		this.setTitle("Patterns and Frameworks Assignment 2");
		
		GUIfileOutput fileOutput = new GUIfileOutput();
		datahandler.addOutput(fileOutput);
		GUIfileInput fileInput = new GUIfileInput(messageHandler);

		frames = new ArrayList<JFrame>();
		this.setLayout(new FlowLayout());

		frameList = new JComboBox();
		frameList.addItem("Selecteer een frame:");

		newFrameButton = new JButton("Nieuwe Frame");
		newFrameButton.addActionListener(this);

		radioTopLeft = new JRadioButton("Top-left");
		radioTopRight = new JRadioButton("Top-right");
		radioBottomLeft = new JRadioButton("Bottom-left");
		radioBottomRight = new JRadioButton("Bottom-right");
		radioTopLeft.setSelected(true);

		locationCheckboxGroup = new ButtonGroup();
		locationCheckboxGroup.add(radioTopLeft);
		locationCheckboxGroup.add(radioTopRight);
		locationCheckboxGroup.add(radioBottomLeft);
		locationCheckboxGroup.add(radioBottomRight);

		buttonTextInput = new JButton("Text Input");
		buttonEventLog = new JButton("Event Log");
		buttonImageOutput = new JButton("Image Output");
		buttonTextOverview = new JButton("Text Overview");
		buttonTextInput.addActionListener(this);
		buttonEventLog.addActionListener(this);
		buttonImageOutput.addActionListener(this);
		buttonTextOverview.addActionListener(this);

		add(frameList);
		add(newFrameButton);

		Box leftRadio = Box.createVerticalBox();
		leftRadio.add(radioTopLeft);
		leftRadio.add(radioBottomLeft);
		Box rightRadio = Box.createVerticalBox();
		rightRadio.add(radioTopRight);
		rightRadio.add(radioBottomRight);

		add(leftRadio);
		add(rightRadio);

		add(buttonTextInput);
		add(buttonEventLog);
		add(buttonImageOutput);
		add(buttonTextOverview);

		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Frame mainFrame = new Frame();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	//private JButton buttonTextInput, buttonEventLog, buttonImageOutput, buttonTextOverview;
	//private JButton newFrameButton;
		if(e.getSource() == buttonTextInput){
			GUItextInput input = new GUItextInput(messageHandler);
			input.setSize(400,300);
			addJPanelToFrame(input);
		} else if(e.getSource() == buttonEventLog){
			GUIeventLog log = new GUIeventLog();
			log.setSize(400,300);
			log.notifyOutput(datahandler);
			addJPanelToFrame(log);
			datahandler.addOutput(log);
		} else if(e.getSource() == buttonImageOutput){
			GUIimageOutput op = new GUIimageOutput();
			op.setSize(400,300);
			op.notifyOutput(datahandler);
			addJPanelToFrame(op);
			datahandler.addOutput(op);
		} else if(e.getSource() == buttonTextOverview){
			GUITextOverview op = new GUITextOverview();
			op.setSize(400,300);
			op.notifyOutput(datahandler);
			addJPanelToFrame(op);
			datahandler.addOutput(op);
		} else if(e.getSource() == newFrameButton){
			String result = JOptionPane.showInputDialog("Vul een naam in voor de nieuwe frame");
			if(result != null){
				JFrame frame = new JFrame(result);
				frame.setLayout(null);
				frames.add(frame);
				frameList.addItem(frames.size() + ": " + result);
				frame.setVisible(true);
				frame.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent we){
						checkFrames();
					}
				});
				frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				frame.setSize(800,600);
				frameList.setSelectedIndex(frameList.getItemCount()-1);
		        frame.setResizable(false);
			}
		}
	}

	private void checkFrames(){
		for(int i = 0; i < frames.size(); i++){
			if(frames.get(i) == null || !frames.get(i).isVisible()){
				frames.remove(i);
			}
		}

		frameList.removeAll();
		frameList.addItem("Selecteer een frame: ");
		for(int i = 0; i < frames.size(); i++){
			frameList.addItem((i+1)+": " + frames.get(i).getTitle());
		}
	}

	private void addJPanelToFrame(JPanel panel){
		String frameName = (String)frameList.getSelectedItem();
		int i = 0;
		try{
			i = Integer.valueOf(frameName.substring(0, frameName.indexOf(':'))) - 1;
		} catch(Exception e){
			JOptionPane.showMessageDialog(this, "Selecteer AUB een frame");
			return;
		}

		if(i < 0 || i >= frames.size()){
			JOptionPane.showMessageDialog(this, "De frame is niet gevonden");
			return;
		}
		panel.setSize(400,300);

		if(radioTopLeft.isSelected()){
			panel.setLocation(0,0);
			frames.get(i).add(panel);
		} else if(radioTopRight.isSelected()){
			panel.setLocation(400,0);
			frames.get(i).add(panel);
		} else if(radioBottomLeft.isSelected()){
			panel.setLocation(0,300);
			frames.get(i).add(panel);
		} else if(radioBottomRight.isSelected()){
			panel.setLocation(400,300);
			frames.get(i).add(panel);
		}
		frames.get(i).repaint();
		frames.get(i).invalidate();
		Dimension size = frames.get(i).getSize();
		size.width++;
		frames.get(i).setSize(size);
		size.width--;
		frames.get(i).setSize(size);
	}
}