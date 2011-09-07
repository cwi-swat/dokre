package com.ns.richrail.core;

public class Wagon extends RollingStock
{
	public static final int DEFAULT_SEATS = 20;
	private Train train;
	private int amountOfSeats;

	protected Wagon(String id, int amountOfSeats)
	{
		super(id);
		this.amountOfSeats = amountOfSeats;
	}

	protected void setTrain(Train train)
	{
		this.train = train;
	}

	// public API
	public int getAmountOfSeats()
	{
		return amountOfSeats;
	}

	public boolean isCoupled()
	{
		return this.train != null;
	}

	public Train getTrain()
	{
		if (isCoupled())
		{
			return train;
		}
		else
		{
			throw new IllegalStateException("wagon is not coupled");
		}
	}
}
