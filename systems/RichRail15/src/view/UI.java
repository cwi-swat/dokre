package view;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


import control.CommandOutput;
import control.ImageView;
import control.RichRailFacade;
import control.TextView;


public class UI extends JFrame implements CommandOutput
{
	JTextField commandTextField;
	JTextArea commandOutput;
	RichRailFacade richRailFacade;
	TextView textViewObserver;
	ImageView imageViewObserver;
	JTextArea textPanel1;
	JTextArea textPanel2;
 
	public JPanel drawingPanel;
 
	
	public UI()
	{
		super("RichRail");
		
		textPanel1 = new JTextArea();
		textPanel2 = new JTextArea();
 
		 drawingPanel = new JPanel();
		richRailFacade = new RichRailFacade(this);
		
		imageViewObserver = new ImageView(richRailFacade, drawingPanel);
		textViewObserver = new TextView(richRailFacade, textPanel1, textPanel2 );
		
		richRailFacade.addObserver(textViewObserver);
		richRailFacade.addObserver(imageViewObserver);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		//tekeninscherm titel
		JLabel drawingTitle = new JLabel("Graphical trains overview");
		layout.setConstraints(drawingTitle, c);
		this.add(drawingTitle);

		//tekeningscherm
		//JPanel drawingPanel = new JPanel();
		drawingPanel.setBackground(Color.WHITE);
		c.weightx = 1.0;
		c.weighty = 1.0;
		layout.setConstraints(drawingPanel, c);
		this.add(drawingPanel);
		

		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weighty = 0;
		//tekstscherm titel
		JLabel textTitle = new JLabel("Textual trains overview");
		layout.setConstraints(textTitle, c);
		this.add(textTitle);
		
		//command output title
		JLabel commandTitle = new JLabel("Command output");
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(commandTitle, c);
		this.add(commandTitle);
		
		
		//tekstscherm
	
		textPanel1.setBackground(Color.WHITE);
		//textPanel.enable(false);
		c.gridwidth = GridBagConstraints.SOUTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		layout.setConstraints(textPanel1, c); 
		textPanel1.append("Wagons \n");
 
		this.add(textPanel1);
		
		textPanel2.setBackground(Color.WHITE);
		//textPanel.enable(false);
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weightx = 0.5;
		c.weighty = 0.5;
		layout.setConstraints(textPanel2, c); 
		textPanel2.append("Trains \n");
		//textPanel.add(new Label("Trains"));
		this.add(textPanel2);
		
 
		
		
		//command output
		commandOutput = new JTextArea();
		commandOutput.setBackground(Color.BLACK);
		commandOutput.setForeground(Color.WHITE);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		layout.setConstraints(commandOutput, c);
		this.add(commandOutput);
		
		//command
		JPanel commandPanel = new JPanel();
		commandPanel.setLayout(new FlowLayout());
		commandTextField = new JTextField(30);
		commandPanel.add(commandTextField);
		JButton executeButton = new JButton("Execute");
		executeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				richRailFacade.executeCommand(commandTextField.getText());
				//System.out.println("println test"); 
				//richRailFacade.notifyObservers(textViewObserver);
				
			
				commandTextField.setText("");
			}
		});
		commandPanel.add(executeButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		commandPanel.add(exitButton);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		c.weighty = 0;
		layout.setConstraints(commandPanel, c);
		this.add(commandPanel);		
		
		
		//zet de cursor in het commandoveld
		this.addWindowListener( new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				commandTextField.requestFocusInWindow();
			}
		});
		
		this.setSize(600, 600);
		
		this.setVisible(true);
		
	}

	@Override
	public void addLine(String line)
	{
		commandOutput.append(line+"\n");	
	}
}
