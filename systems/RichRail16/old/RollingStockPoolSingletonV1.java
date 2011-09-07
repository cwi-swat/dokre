package com.ns.richrail.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RollingStockPoolSingleton implements RollingStockPoolObservable
{
	private static RollingStockPoolSingleton instance;
	private ArrayList<RollingStock> pool = new ArrayList<RollingStock>();
	private Map<String, Train> allTrains = new HashMap<String, Train>();
	private Map<String, Wagon> allDecoupledWagons = new HashMap<String, Wagon>();
	private ArrayList<RollingStockPoolObserver> poolObservers = new ArrayList<RollingStockPoolObserver>();

	public static RollingStockPoolSingleton getInstance()
	{
		if (instance == null)
			instance = new RollingStockPoolSingleton();
		return instance;
	}

	@Override
	public void attach(RollingStockPoolObserver observer)
	{
		poolObservers.add(observer);
	}

	@Override
	public void detach(RollingStockPoolObserver observer)
	{
		poolObservers.remove(observer);
	}

	private void notifyRollingStockPoolObservers()
	{
		for (RollingStockPoolObserver observer : poolObservers)
		{
			observer.update();
		}
	}

	public ArrayList<Train> getAllTrains()
	{
		ArrayList<Train> trains = new ArrayList<Train>();
		for (RollingStock rollingStockItem : pool)
		{
			if (rollingStockItem instanceof Train)
			{
				trains.add((Train) rollingStockItem);
			}
		}
		return trains;
	}

	public ArrayList<Wagon> getAllDecoupledWagons()
	{
		ArrayList<Wagon> wagons = new ArrayList<Wagon>();
		for (RollingStock rollingStockItem : pool)
		{
			if (rollingStockItem instanceof Wagon)
			{
				Wagon wagon = (Wagon) rollingStockItem;
				if (!wagon.isCoupled())
				{
					wagons.add(wagon);
				}
			}
		}
		return wagons;
	}

	public Train getTrain(String id)
	{
		Train result = null;
		searchLoop: for (RollingStock rollingStockItem : pool)
		{
			if (rollingStockItem instanceof Train)
			{
				Train train = (Train) rollingStockItem;
				if (train.getId().equals(id))
				{
					result = train;
					break searchLoop;
				}
			}
		}
		return result;
	}

	public Wagon getTrainWagon(String id, String wagonId)
	{
		Wagon result = null;
		searchLoop: for (RollingStock rollingStockItem : pool)
		{
			if (rollingStockItem instanceof Train)
			{
				Train train = (Train) rollingStockItem;
				if (train.getId().equals(id))
				{
					for (Wagon wagon : train.getWagons())
					{
						if (wagon.getId().equals(wagonId))
						{
							result = wagon;
							break searchLoop;
						}
					}
				}
			}
		}
		return result;
	}

	public Wagon getDecoupledWagon(String id)
	{
		Wagon result = null;
		searchLoop: for (RollingStock rollingStockItem : pool)
		{
			if (rollingStockItem instanceof Wagon)
			{
				Wagon wagon = (Wagon) rollingStockItem;
				if (wagon.getId().equals(id) && !wagon.isCoupled())
				{
					result = wagon;
					break searchLoop;
				}
			}
		}
		return result;
	}

	public void addRollingStock(RollingStock rollingStockItem)
	{
		pool.add(rollingStockItem);
		notifyRollingStockPoolObservers();
	}

	public void removeRollingStock(RollingStock rollingStockItem)
	{
		pool.remove(rollingStockItem);
		notifyRollingStockPoolObservers();
	}
}
