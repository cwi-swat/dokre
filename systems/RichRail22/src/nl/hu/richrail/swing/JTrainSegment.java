package nl.hu.richrail.swing;
import nl.hu.richrail.trains.Engine;
import nl.hu.richrail.trains.Car;
import nl.hu.richrail.trains.RollingStock;
import javax.swing.*;

public abstract class JTrainSegment extends JComponent implements JTrainComponent
{
	protected RollingStock waggon;
	
	public JTrainSegment(RollingStock waggon)
	{
		this.waggon = waggon;
	}
	
	public String getID()
	{
		return waggon.getFullId();
	}
	
	
}
