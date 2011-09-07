package com.ns.richrail.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RollingStockPool implements RollingStockPoolFacade
{
	private static RollingStockPoolFacade instance;
	private Map<String, Train> allTrains = new HashMap<String, Train>();
	private Map<String, Wagon> allCoupledWagons = new HashMap<String, Wagon>();
	private Map<String, Wagon> allDecoupledWagons = new HashMap<String, Wagon>();
	private ArrayList<RollingStockPoolObserver> poolObservers = new ArrayList<RollingStockPoolObserver>();

	public static RollingStockPoolFacade getInstance()
	{
		if (instance == null)
			instance = new RollingStockPool();
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

	public Collection<Train> getAllTrains()
	{
		return allTrains.values();
	}

	public Collection<Wagon> getAllDecoupledWagons()
	{
		return allDecoupledWagons.values();
	}

	public void newTrain(String id) throws PoolException
	{
		if (allTrains.containsKey(id))
		{
			throw new PoolException("train already exists");
		}
		Train train = new Train(id);
		allTrains.put(id, train);
		notifyRollingStockPoolObservers();
	}

	public void newWagon(String id, Integer numseats) throws PoolException
	{
		if (allDecoupledWagons.containsKey(id) || allCoupledWagons.containsKey(id))
		{
			throw new PoolException("wagon already exists");
		}
		Wagon wagon = numseats == null ? new Wagon(id, Wagon.DEFAULT_SEATS) : new Wagon(id, numseats);
		allDecoupledWagons.put(id, wagon);
		notifyRollingStockPoolObservers();
	}

	public void coupleWagon(String wagonId, String trainId) throws PoolException
	{
		if (allDecoupledWagons.containsKey(wagonId) && allTrains.containsKey(trainId))
		{
			Wagon wagon = allDecoupledWagons.get(wagonId);
			Train train = allTrains.get(trainId);
			wagon.setTrain(train);
			train.addWagon(wagon);
			allDecoupledWagons.remove(wagonId);
			allCoupledWagons.put(wagonId, wagon);
			notifyRollingStockPoolObservers();
		}
		else
		{
			throw new PoolException("train or wagon does not exist");
		}
	}

	public void decoupleWagon(String wagonId) throws PoolException
	{
		if (allCoupledWagons.containsKey(wagonId))
		{
			Wagon wagon = allCoupledWagons.get(wagonId);
			Train train = wagon.getTrain();
			wagon.setTrain(null);
			train.removeWagon(wagon);
			allCoupledWagons.remove(wagonId);
			allDecoupledWagons.put(wagonId, wagon);
			notifyRollingStockPoolObservers();
		}
		else
		{
			throw new PoolException("wagon does not exist or is not coupled");
		}
	}

	public void deleteTrain(String id) throws PoolException
	{
		if (allTrains.containsKey(id))
		{
			Train train = allTrains.get(id);
			for (Wagon wagon : train.getWagons())
			{
				wagon.setTrain(null);
				allCoupledWagons.remove(wagon.getId());
			}
			allTrains.remove(id);
			notifyRollingStockPoolObservers();
		}
		else
		{
			throw new PoolException("train does not exist");
		}
	}

	public void deleteWagon(String id) throws PoolException
	{
		if (allDecoupledWagons.containsKey(id))
		{
			allDecoupledWagons.remove(id);
			notifyRollingStockPoolObservers();
		}
		else
			if (allCoupledWagons.containsKey(id))
			{
				Wagon wagon = allCoupledWagons.get(id);
				Train train = wagon.getTrain();
				wagon.setTrain(null);
				train.removeWagon(wagon);
				allCoupledWagons.remove(id);
				notifyRollingStockPoolObservers();
			}
			else
			{
				throw new PoolException("wagon does not exist");
			}
	}

	public int countNumSeatsTrain(String id) throws PoolException
	{
		int result = 0;
		if (allTrains.containsKey(id))
		{
			Train train = allTrains.get(id);
			for (Wagon wagon : train.getWagons())
			{
				result += wagon.getAmountOfSeats();
			}
		}
		else
		{
			throw new PoolException("train does not exist");
		}
		return result;
	}

	public int countNumSeatsWagon(String id) throws PoolException
	{
		int result = 0;
		if (allCoupledWagons.containsKey(id))
		{
			Wagon wagon = allCoupledWagons.get(id);
			result = wagon.getAmountOfSeats();
		}
		else
			if (allDecoupledWagons.containsKey(id))
			{
				Wagon wagon = allDecoupledWagons.get(id);
				result = wagon.getAmountOfSeats();
			}
			else
			{
				throw new PoolException("wagon does not exist");
			}
		return result;
	}
}
