package com.ns.richrail.gui.graphicpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class RollingStockPoolGraphicPanel extends JPanel
{
	private ArrayList<Paintable> paintables = new ArrayList<Paintable>();

	public RollingStockPoolGraphicPanel()
	{
		super();
	}

	public void initPanel()
	{
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(-1, 2000));
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		synchronized (paintables)
		{
			for (Paintable paintable : paintables)
			{
				paintable.paint(g);
			}
		}
	}

	public void clear()
	{
		synchronized (paintables)
		{
			paintables.clear();
		}
	}

	public void addTrain(int track, String label)
	{
		synchronized (paintables)
		{
			paintables.add(new PaintableTrain(track, label));
		}
	}

	public void addWagon(int track, int slot, String label)
	{
		synchronized (paintables)
		{
			paintables.add(new PaintableWagon(track, slot, label));
		}
	}

	public void draw()
	{
		repaint();
	}
}
