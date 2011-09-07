package nl.hu.richrail.trains;

import java.util.*;

import nl.hu.richrail.trains.PoolChangeEvent.Types;

public class Train extends RollingStock
{
	private static final String RR_TYPE = "train";
	
	private Engine locomotive;
	private LinkedList<RollingStock> rollingStockItems = new LinkedList<RollingStock>();

	
	public Train(String id)
	{
		super(id);
		locomotive = new Engine(id);
	}
	public RollingStock[] getRollingStockItems()
	{
		return rollingStockItems.toArray(new RollingStock[]{});
	}
	public RollingStock getRollingStockItem(String id)
	{
		for(RollingStock rs : rollingStockItems)
			if(rs.getId().equals(id)) return rs;
		return null;
	}
	
	public Engine getEngine()
	{
		return locomotive;
	}
	

	@Override
	public int getSeatCount() {
		// TODO Auto-generated method stub
		int sum = 0;
		for(RollingStock rs : rollingStockItems)
			sum += rs.getSeatCount();
		
		return sum;
	}

	protected void attachRollingStock(RollingStock rs)
	{
		rollingStockItems.add(rs);
		setChanged();
		notifyObservers(new PoolChangeEvent(Types.add,rs));
	}
	protected void detachRollingStock(RollingStock rs)
	{
		rollingStockItems.remove(rs);
		setChanged();
		notifyObservers(new PoolChangeEvent(Types.removed,rs));
	}
	
}
