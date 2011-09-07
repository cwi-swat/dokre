package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logging.Logger;

public class CommandOutput extends JPanel implements Observer
{

	private static final long serialVersionUID = 1L;
	private JTextArea tfOutput;

	public CommandOutput()
	{
		super();

		this.setLayout(new GridLayout(1, 1));

		tfOutput = new JTextArea();
		tfOutput.setEditable(false);
		tfOutput.setBackground(Color.BLACK);
		tfOutput.setForeground(Color.WHITE);
		tfOutput.setFont(new Font("Courier New", Font.PLAIN, 14));

		JScrollPane scrollPane = new JScrollPane(tfOutput);

		Logger.getInstance().addObserver(this);

		this.add(scrollPane);
		this.update(null, null);
	}

	public void appendText(String text)
	{
		tfOutput.append(text + System.getProperty("line.separator"));
	}

	@Override
	public void update(Observable o, Object arg)
	{
		ArrayList<String> logs = Logger.getInstance().getLogs();

		tfOutput.setText("");

		for (String s : logs)
		{
			this.appendText(s);
		}
	}

}
