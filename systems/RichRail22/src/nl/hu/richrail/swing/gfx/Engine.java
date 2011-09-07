package nl.hu.richrail.swing.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import nl.hu.richrail.swing.JTrain;
import nl.hu.richrail.swing.JTrainSegment;
import nl.hu.richrail.trains.RollingStock;

public class Engine extends JTrainSegment
{


	public Engine(RollingStock waggon) {
		super(waggon);
		setPreferredSize(new Dimension(126,140));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1521922053884520069L;

	protected void paintComponent()
	{
		
	}
	@Override
	public void paint(Graphics g)
	{
		//System.out.println("Engine[" + super.getID() +"].paint");
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30,80,80,40);
		g.fillRect(80,60,30,30);
		g.drawRoundRect(85,40, 20, 20, 20, 20);
		g.drawRoundRect(85, 0, 40, 40, 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35, 120, 20, 20, 20, 20);
		g.fillRoundRect(80, 120, 20, 20, 20, 20);
		g.drawString(super.waggon.getId(),40,105);
	}

}
