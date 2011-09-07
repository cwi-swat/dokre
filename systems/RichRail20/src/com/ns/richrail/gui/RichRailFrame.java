package com.ns.richrail.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.ns.richrail.gui.graphicpanel.RollingStockPoolGraphicPanel;

public class RichRailFrame extends JFrame
{
	private RichRailController controller;
	private RollingStockPoolGraphicPanel graphicPanel = new RollingStockPoolGraphicPanel();
	private RollingStockPoolTextPanel textPanel = new RollingStockPoolTextPanel();
	private CommandOutputPanel commandOutputPanel = new CommandOutputPanel();
	private CommandInputPanel commandInputPanel;

	public RichRailFrame(RichRailController controller)
	{
		super();
		this.controller = controller;
		commandInputPanel = new CommandInputPanel(controller);
	}

	public void initFrame()
	{
		setTitle("RichRail");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(controller);

		LayoutManager layout = new GridBagLayout();
		Container contentPane = getContentPane();
		contentPane.setLayout(layout);

		GridBagConstraints c;

		graphicPanel.initPanel();
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_START;
		contentPane.add(new JScrollPane(graphicPanel), c);

		textPanel.initPanel();
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_START;
		contentPane.add(textPanel, c);

		commandOutputPanel.initPanel();
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_START;
		contentPane.add(commandOutputPanel, c);

		commandInputPanel.initPanel();
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1.0;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.PAGE_START;
		contentPane.add(commandInputPanel, c);

		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public RollingStockPoolGraphicPanel getGraphicPanel()
	{
		return graphicPanel;
	}

	public RollingStockPoolTextPanel getTextPanel()
	{
		return textPanel;
	}

	public CommandInputPanel getCommandInputPanel()
	{
		return commandInputPanel;
	}

	public CommandOutputPanel getCommandOutputPanel()
	{
		return commandOutputPanel;
	}
}
