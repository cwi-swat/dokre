package com.ns.richrail.core;

import java.util.Collection;

public interface RollingStockPoolFacade extends RollingStockPoolObservable
{
	public Collection<Train> getAllTrains();

	public Collection<Wagon> getAllDecoupledWagons();

	public void newTrain(String id) throws PoolException;

	public void newWagon(String id, Integer numseats) throws PoolException;

	public void coupleWagon(String wagonId, String trainId) throws PoolException;

	public void decoupleWagon(String wagonId) throws PoolException;

	public void deleteTrain(String id) throws PoolException;

	public void deleteWagon(String id) throws PoolException;

	public int countNumSeatsTrain(String id) throws PoolException;

	public int countNumSeatsWagon(String id) throws PoolException;
}
