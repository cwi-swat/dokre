package Displays;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Commands.Command;
import TaskSpecific.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DataEntry extends JPanel implements ActionListener{
	private Converter converter = new Converter();
	private JPanel entryPanel;
	private JTextField inputTextField;
	private JButton sendButton;
	private JButton helpButton;
	private JLabel commandLabel;
	
	public DataEntry(JPanel p){
		
		setLayout(new FlowLayout());
		entryPanel = new JPanel();
		entryPanel.setBackground(Color.CYAN); 
		entryPanel.setPreferredSize(new Dimension(400, 90));
		add(entryPanel);
		commandLabel = new JLabel("Command: ");
		inputTextField = new JTextField(20);
		sendButton = new JButton("Execute");
		helpButton = new JButton("Command help");
		entryPanel.add(commandLabel);
		entryPanel.add(inputTextField);
		entryPanel.add(sendButton);
		entryPanel.add(helpButton);
		setSize(400, 150);
		setVisible(true);
		p.add(entryPanel);
		sendButton.addActionListener(actionlistn1);
		helpButton.addActionListener(actionlistn2);
	}
	public DataEntry(){
	}
	
	public String getLine(){
		return inputTextField.getText();
	}
	
	
	ActionListener actionlistn1 = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			converter.convertCommand(getLine());
			inputTextField.setText("");
		}
	};

	ActionListener actionlistn2 = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Commands: (left side the action, right side the command)\n" +
					"\n" +
					"Add new train ==> new train <train name>; \n" +
					"Add new wagontype ==> new wagontype <wagontype name>;\n" +
					"Add new wagon ==> new wagon <wagon id> <wagontype name>;\n" +
					"\n" +
					"Delete train ==> delete train <train name>;\n" +
					"Delete wagontype ==> delete wagontype <wagontype name>;\n" +
					"Delete wagon ==> delete wagon <wagon id>;\n" +
					"\n" +
					"Add wagon to train ==> add <wagon id> to <train name>;\n" +
					"\n" +
					"Remove wagon from train ==> remove <wagon id> from <train name>;\n" +
					"\n" +
					"Get number of seats of a train ==> getnumseats <train name>;\n" +
					"Get number of seats of a wagon ==> getnumseats <wagon id>;\n" +
					"\n" +
					"====>IMPORTANT: COMMANDS ARE CASE SENSITIVE<===="
					);
		}
	};
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		converter.convertCommand(getLine());
		inputTextField.setText("");
	}
		
}