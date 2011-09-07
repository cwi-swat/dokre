package nl.hu.richrail.trains;

public class PoolChangeEvent
{
	public enum Types{
		add,removed;
	}
	
	public final Types type;
	public final RollingStock rollingStock;
	
	public PoolChangeEvent(PoolChangeEvent.Types type, RollingStock rollingStock)
	{
		this.type = type;
		this.rollingStock = rollingStock;
		
	}
}
