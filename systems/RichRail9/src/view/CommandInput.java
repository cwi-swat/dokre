package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AppController;

public class CommandInput extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	
	private JLabel lDescription;
	private JTextField tfCommand;
	private JButton bExecute;
	
	private AppController controller;
	
	public CommandInput(AppController controller) {
		this.controller = controller;
		
		this.setLayout(new GridLayout(1,3));
		
		lDescription = new JLabel("Command");
		this.add(lDescription);
		
		tfCommand = new JTextField();
		tfCommand.addKeyListener(this);
		tfCommand.requestFocusInWindow();
		this.add(tfCommand);
		
		bExecute = new JButton("Execute");
		bExecute.addActionListener(this);
		this.add(bExecute);
	}
	
	public void setFocus() {
		tfCommand.requestFocusInWindow();
	}
	
	private void performAction(){
		if(tfCommand.getText().equals(""))
			return;
	
		controller.processCommand(tfCommand.getText());
		tfCommand.setText("");
		tfCommand.requestFocusInWindow();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		performAction();

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyChar() == (char)10)
			performAction();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
