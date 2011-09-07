package Facade;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class CommandInput extends JPanel{
	private JTextField textField;
	protected JButton btnExecute;
	
	public CommandInput() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 120, 0};
		gridBagLayout.rowHeights = new int[]{34, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblCommand = new JLabel("Command");
		GridBagConstraints gbc_lblCommand = new GridBagConstraints();
		gbc_lblCommand.anchor = GridBagConstraints.EAST;
		gbc_lblCommand.insets = new Insets(0, 0, 0, 5);
		gbc_lblCommand.gridx = 0;
		gbc_lblCommand.gridy = 0;
		add(lblCommand, gbc_lblCommand);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		btnExecute = new JButton("Execute");
		GridBagConstraints gbc_btnExecute = new GridBagConstraints();
		gbc_btnExecute.gridx = 2;
		gbc_btnExecute.gridy = 0;
		add(btnExecute, gbc_btnExecute);
	}	
	public String getInput(){
		return textField.getText();
	}
	
}
