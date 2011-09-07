package com.ns.richrail.gui.graphicpanel;

import java.awt.Color;
import java.awt.Graphics;

public class PaintableWagon implements Paintable
{
	private int track;
	private int slot;
	private String label;

	public PaintableWagon(int track, int slot, String label)
	{
		super();
		this.track = track;
		this.slot = slot;
		this.label = label;
	}

	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30 + (slot + 1) * PaintableTrain.TRAINWIDTH, 80 + track * PaintableTrain.TRAINHEIGHT, 80, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35 + (slot + 1) * PaintableTrain.TRAINWIDTH, 120 + track * PaintableTrain.TRAINHEIGHT, 20, 20, 20, 20);
		g.fillRoundRect(80 + (slot + 1) * PaintableTrain.TRAINWIDTH, 120 + track * PaintableTrain.TRAINHEIGHT, 20, 20, 20, 20);
		g.drawString(label, 40 + (slot + 1) * PaintableTrain.TRAINWIDTH, 105 + track * PaintableTrain.TRAINHEIGHT);
	}
}
