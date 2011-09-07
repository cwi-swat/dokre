package com.ns.richrail.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CommandInputPanel extends JPanel
{
	private JTextField commandField;
	private ActionListener executeListener;

	public CommandInputPanel(ActionListener executeListener)
	{
		super();
		this.executeListener = executeListener;
	}

	public CommandInputPanel()
	{
		super();
	}

	public void initPanel()
	{
		LayoutManager layout = new GridBagLayout();
		setLayout(layout);

		GridBagConstraints c;

		JLabel commandLabel = new JLabel("command");
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.weightx = 0.0;
		add(commandLabel, c);

		commandField = new JTextField();
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.weightx = 1.0;
		add(commandField, c);

		JButton executeButton = new JButton("execute");
		executeButton.addActionListener(executeListener);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.weightx = 0.0;
		add(executeButton, c);
	}

	public String getCommand()
	{
		return commandField.getText();
	}
}
