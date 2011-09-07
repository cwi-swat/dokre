package com.ns.richrail.gui.graphicpanel;

import java.awt.Color;
import java.awt.Graphics;

public class PaintableTrain implements Paintable
{
	protected static final int TRAINHEIGHT = 150;
	protected static final int TRAINWIDTH = 100;
	private int track;
	private String label;

	public PaintableTrain(int track, String label)
	{
		super();
		this.track = track;
		this.label = label;
	}

	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30, 80 + track * TRAINHEIGHT, 80, 40);
		g.fillRect(80, 60 + track * TRAINHEIGHT, 30, 30);
		g.drawRoundRect(85, 40 + track * TRAINHEIGHT, 20, 20, 20, 20);
		g.drawRoundRect(85, track * TRAINHEIGHT, 40, 40, 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35, 120 + track * TRAINHEIGHT, 20, 20, 20, 20);
		g.fillRoundRect(80, 120 + track * TRAINHEIGHT, 20, 20, 20, 20);
		g.drawString(label, 40, 105 + track * TRAINHEIGHT);
	}
}
