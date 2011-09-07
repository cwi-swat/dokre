package com.ns.richrail.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

final class TextAreaWriter extends Writer
{
	private final JTextArea textArea;

	public TextAreaWriter(final JTextArea textArea)
	{
		this.textArea = textArea;
	}

	@Override
	public void flush()
	{
	}

	@Override
	public void close()
	{
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException
	{
		textArea.append(new String(cbuf, off, len));
	}
}

public class CommandOutputPanel extends JPanel
{
	private PrintWriter printWriter;
	private JTextArea outputArea;

	public CommandOutputPanel()
	{
		super();
		outputArea = new JTextArea();
		printWriter = new PrintWriter(new TextAreaWriter(outputArea));
	}

	public void initPanel()
	{
		LayoutManager layout = new GridBagLayout();
		setLayout(layout);

		GridBagConstraints c;

		JLabel outputLabel = new JLabel("output");
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.anchor = GridBagConstraints.PAGE_START;
		add(outputLabel, c);

		outputArea.setLineWrap(true);
		outputArea.setEditable(false);
		outputArea.setBackground(Color.BLACK);
		outputArea.setForeground(Color.WHITE);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_START;
		add(new JScrollPane(outputArea), c);
	}

	public PrintWriter getPrintWriter()
	{
		return printWriter;
	}
}
