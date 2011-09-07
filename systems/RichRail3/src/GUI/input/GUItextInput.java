package GUI.input;

import interpreter.DSLmessageHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUItextInput extends GUIinput implements ActionListener {
	private static final long serialVersionUID = 7720529631862596551L;
	private JLabel commandLabel;
	private JTextField text;
	private JButton button;
	
	private DSLmessageHandler messageHandler;

	public GUItextInput(DSLmessageHandler messageHandler){
		commandLabel = new JLabel("Command");
		text = new JTextField(20); 
		button = new JButton("Execute");	 
		button.addActionListener(this);
		add(commandLabel);
		add(text);
		add(button);
		
		this.messageHandler = messageHandler;
	}	
	
	public void actionPerformed(ActionEvent e){
		messageHandler.parseInput(text.getText());
	}
}
