package com.ns.richrail.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RollingStockPoolTextPanel extends JPanel
{
	private JTextArea outputArea;
	private Map<String, ArrayList<String>> trains = new HashMap<String, ArrayList<String>>();
	private ArrayList<String> wagons = new ArrayList<String>();

	public RollingStockPoolTextPanel()
	{
		super();
	}

	public void initPanel()
	{
		setBackground(Color.BLUE);
		LayoutManager layout = new GridBagLayout();
		setLayout(layout);

		GridBagConstraints c;

		outputArea = new JTextArea();
		outputArea.setLineWrap(true);
		outputArea.setEditable(false);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.CENTER;
		add(new JScrollPane(outputArea), c);
	}

	public void clear()
	{
		trains.clear();
		wagons.clear();
	}

	public void draw()
	{
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("trains");
		for (Map.Entry<String, ArrayList<String>> train : trains.entrySet())
		{
			pw.print(String.format("(%s)", train.getKey()));
			for (String wagon : train.getValue())
			{
				pw.print(String.format("-(%s)", wagon));
			}
			pw.println();
		}
		pw.println("wagons");
		for (String wagon : wagons)
		{
			pw.print(String.format("(%s) ", wagon));
		}
		pw.println();
		outputArea.setText(writer.toString());
	}

	public void addTrain(String label)
	{
		trains.put(label, new ArrayList<String>());
	}

	public void addCoupledWagon(String trainLabel, String label)
	{
		trains.get(trainLabel).add(label);
	}

	public void addWagon(String label, int amountOfSeats)
	{
		wagons.add(String.format("%s:%s", label, amountOfSeats));
	}
}
