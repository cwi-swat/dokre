package model;

public class Wagon
{
	private String id;
	private int numSeats;
	
	public Wagon(String id)
	{
		this.id = id;
		this.numSeats = 20;
	}
	
	public Wagon(String id, int numSeats)
	{
		this.id = id;
		this.numSeats = numSeats;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public int getNumSeats()
	{
		return this.numSeats;
	}
	
	public String toString()
	{
		String s = "";
		s += "("+  id + ":" + numSeats + ")";
		return s;
	}
	
}
