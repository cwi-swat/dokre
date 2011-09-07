package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import logic.Controller;

public class CommandView extends View implements ActionListener {
	Controller controller;
	JPanel executepanel;
	JTextArea txtarea;
	JTextField input;
	JButton execute;
	
	public CommandView() {
		executepanel = new JPanel();
		txtarea = new JTextArea();
		input = new JTextField(20);
		
		txtarea.setText("command");
		txtarea.setEditable(false);
		
		execute = new JButton("execute");
		execute.addActionListener(this);
		
		executepanel.add(txtarea);
		executepanel.add(input);
		executepanel.add(execute);
		executepanel.setSize(350,250);
		executepanel.setVisible(true);
		
		this.add(executepanel);
		this.setSize(350,250);
	}
	
	public void setController(Controller controller)
	{
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		// en hier het gedeelte om via een command iets toe te voegen of te verwijderen etc.
		if(e.getSource() == execute)
		{
			if(controller != null)
			{
				controller.split(input.getText());
			}
		}
	}

	@Override
	public void update(Controller controller) {
		//TODO
	}
}
