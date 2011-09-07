package nl.hu.richrail.swing.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import nl.hu.richrail.swing.JTrainSegment;
import nl.hu.richrail.trains.RollingStock;

public class Waggon extends JTrainSegment
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -858470896824421697L;
	
	public Waggon(RollingStock waggon) {
		super(waggon);
		setPreferredSize(new Dimension(100,140));
	}
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30,80,80,40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35, 120, 20, 20, 20, 20);
		g.fillRoundRect(80, 120, 20, 20, 20, 20);
		g.drawString(super.waggon.getId(),40,105);
	}
}
