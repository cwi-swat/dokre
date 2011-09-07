package nl.hu.richrail.swing;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class JTrain extends JPanel implements JTrainComponent
{
	private String ID;
	
	public JTrain(String id)
	{
		this.ID = id;
	}
	
	public String getID()
	{
		return ID;
	}
	
	public abstract void add(JComponent trainSegment);
	public abstract void remove(String id);
}
