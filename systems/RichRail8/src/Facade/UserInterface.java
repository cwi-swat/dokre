package Facade;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Commands.Command;
import Commands.CommandFactory;


public class UserInterface extends JFrame {
	
	private CommandInput ci = new CommandInput();
	private TrainFacadeSingleton tsf = TrainFacadeSingleton.getTfs();
	private CommandOutput co = new CommandOutput();
	private CommandFactory cf = new CommandFactory();
	private TrainTextViewer ttv = new TrainTextViewer();
	private GraphicViewer gv = new GraphicViewer();
	public UserInterface() {
		tsf.attachObserver(co);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNew = new JMenu("New");
		menuBar.add(mnNew);
		
		JMenuItem mntmSecondUi = new JMenuItem("Second UI");
		mnNew.add(mntmSecondUi);
		
		JMenuItem mntmLog = new JMenuItem("Log");
		mnNew.add(mntmLog);
		mntmLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LogView lv = new LogView();
				tsf.attachObserver(lv);
				lv.setVisible(true);
			}
		});
		
		mntmSecondUi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserInterface UI = new UserInterface();
				UI.setVisible(true);
			}
		});
		
		tsf.attachObserver(ttv);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{263, 267, 0};
		gridBagLayout.rowHeights = new int[]{280, 224, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel graphpanel = new JPanel();
		GridBagConstraints gbc_graphpanel = new GridBagConstraints();
		gbc_graphpanel.gridwidth = 2;
		gbc_graphpanel.insets = new Insets(0, 0, 5, 5);
		gbc_graphpanel.fill = GridBagConstraints.BOTH;
		gbc_graphpanel.gridx = 0;
		gbc_graphpanel.gridy = 0;
		getContentPane().add(graphpanel, gbc_graphpanel);
		graphpanel.setLayout(new BorderLayout(0, 0));
		graphpanel.add(gv);
		tsf.attachObserver(gv);
		
		JPanel TextPanel = new JPanel();
		GridBagConstraints gbc_TextPanel = new GridBagConstraints();
		gbc_TextPanel.insets = new Insets(0, 0, 5, 5);
		gbc_TextPanel.fill = GridBagConstraints.BOTH;
		gbc_TextPanel.gridx = 0;
		gbc_TextPanel.gridy = 1;
		getContentPane().add(TextPanel, gbc_TextPanel);
		TextPanel.setLayout(new BorderLayout(0, 0));
		TextPanel.add(ttv);
		
		JPanel CommandOutputPanel = new JPanel();
		GridBagConstraints gbc_CommandOutputPanel = new GridBagConstraints();
		gbc_CommandOutputPanel.insets = new Insets(0, 0, 5, 0);
		gbc_CommandOutputPanel.fill = GridBagConstraints.BOTH;
		gbc_CommandOutputPanel.gridx = 1;
		gbc_CommandOutputPanel.gridy = 1;
		getContentPane().add(CommandOutputPanel, gbc_CommandOutputPanel);
		CommandOutputPanel.setLayout(new BorderLayout(0, 0));
		CommandOutputPanel.add(co);
		JPanel CommandInputPanel = new JPanel();
		GridBagConstraints gbc_CommandInputPanel = new GridBagConstraints();
		gbc_CommandInputPanel.insets = new Insets(0, 0, 5, 5);
		gbc_CommandInputPanel.fill = GridBagConstraints.BOTH;
		gbc_CommandInputPanel.gridx = 0;
		gbc_CommandInputPanel.gridy = 2;
		getContentPane().add(CommandInputPanel, gbc_CommandInputPanel);
		CommandInputPanel.add(ci);
		ci.btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Command c = cf.CommandMethod(ci.getInput());
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				if(!(c==null)){
				//co.addText(c.getOutput());
				tsf.addCommandOutput(c.getOutput());
				
				tsf.addLog(""+sdf.format(cal.getTime())+ ": " + ci.getInput() +": "+ c.getOutput());
				}
				else {
					//co.addText("Command not correct(type help for a list of commands");
					tsf.addLog(""+sdf.format(cal.getTime())+ ": " + ci.getInput() + ": Uncorrect Command");
					tsf.addCommandOutput("Command not correct(Type help for a list of command)");
				}
			}
		});
		setSize(543,625);
	}

}
