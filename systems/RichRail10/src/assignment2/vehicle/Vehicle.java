package assignment2.vehicle;

import java.awt.Graphics;

public abstract class Vehicle
{
	protected final int OFFSET = 100;
	protected final int LENGTH = 100;

	private String id;
	private String type = "vehicle";
	private int numberOfSeats = 0;

	protected Vehicle()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String val)
	{
		this.id = val;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String val)
	{
		this.type = val;
	}

	public int getNumberOfSeats()
	{
		return numberOfSeats;
	}

	public void setNumberOfSeats(int val)
	{
		this.numberOfSeats = val;
	}

	public abstract void draw(Graphics g, int currentTrain, int curNumOfWagons);
}
