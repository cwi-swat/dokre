package com.ns.richrail.core;

import java.util.ArrayList;

public class Train extends RollingStock
{
	private ArrayList<Wagon> wagons = new ArrayList<Wagon>();

	protected Train(String id)
	{
		super(id);
	}

	protected void addWagon(Wagon wagon)
	{
		wagons.add(wagon);
	}

	protected void removeWagon(Wagon wagon)
	{
		wagons.remove(wagon);
	}

	// public API
	public ArrayList<Wagon> getWagons()
	{
		return wagons;
	}

	/*
	 * public Wagon getWagon(String id) { Wagon result = null; searchLoop : for
	 * (Wagon wagon : wagons) { if (wagon.getId().equals(id)) { result = wagon;
	 * break searchLoop; } } return result; }
	 */
}
