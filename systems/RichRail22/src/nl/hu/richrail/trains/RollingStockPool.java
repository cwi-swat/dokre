package nl.hu.richrail.trains;

import java.util.HashMap;

import java.util.Observable;


import nl.hu.richrail.rrdsl.TrainYardContext;
import nl.hu.richrail.trains.PoolChangeEvent.Types;

public class RollingStockPool extends Observable implements TrainYardContext
{
	private static RollingStockPool instance = null;
	
	
	private HashMap<String, RollingStock> rollingStockItems = new HashMap<String, RollingStock>();
	private HashMap<String, Train> trains = new HashMap<String, Train>();
	
	private RollingStockPool()
	{
	}
	
	public static RollingStockPool getInstance()
	{
		if(instance == null){
			instance = new RollingStockPool();
			System.out.println("Rolling Stock Pool Instance created!");
		}
		return instance;
	}
	
	public RollingStock[] getRollingStockItems()
	{
		return rollingStockItems.values().toArray(new RollingStock[]{});
	}
	public Train[] getTrains()
	{
		return trains.values().toArray(new Train[]{});
	}
	
	public void addRollingStock(RollingStock rollingStock)
	{
		rollingStockItems.put(rollingStock.getId(), rollingStock);
		super.setChanged();
		notifyObservers(new PoolChangeEvent(Types.add,rollingStock));
	}
	public void addRollingStock(Train rollingStock)
	{
		trains.put(rollingStock.getId(), (Train) rollingStock);
		super.setChanged();
		notifyObservers(new PoolChangeEvent(Types.add,rollingStock));
	}
	
	public RollingStock removeRollingStock(RollingStock rollingStock)
	{
	   RollingStock r = rollingStockItems.remove(rollingStock.getId());
		super.setChanged();
		notifyObservers(new PoolChangeEvent(Types.removed,rollingStock));
		return r;
	}
	public RollingStock removeRollingStock(Train train)
	{
		RollingStock r = trains.remove(train.getId());
		super.setChanged();
		notifyObservers(new PoolChangeEvent(Types.removed,train));
		return r;
	}
	
	public RollingStock getRollingStockItem(String id)
	{
		return rollingStockItems.get(id);
	}
	
	
	public Train getTrain(String id)
	{
		return trains.get(id);
	}
	
	public void attachRollingStock(RollingStock rsItem, Train toTrain)
	{
		if(rsItem == null || toTrain == null)
			throw new NullPointerException();
		rollingStockItems.remove(rsItem.getId());
		trains.get(toTrain.getId()).attachRollingStock(rsItem);
		setChanged();
		notifyObservers(new PoolChangeEvent(Types.removed,rsItem));
	}
	public void detachRollingStock(RollingStock rsItem, Train fromTrain)
	{
		if(rsItem == null || fromTrain == null)
			throw new NullPointerException();
		trains.get(fromTrain.getId()).detachRollingStock(rsItem);
		rollingStockItems.put(rsItem.getId(), rsItem);
		setChanged();
		notifyObservers(new PoolChangeEvent(Types.add,rsItem));
	}

}
