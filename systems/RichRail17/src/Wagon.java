
public class Wagon {
	
	private String name;
	private int numSeats;
	
	public Wagon(String Name)
	{
		name = Name;
		numSeats = 20;
	}
	
	public Wagon(String Name, int numseats)
	{
		name = Name;
		numSeats = numseats;
	}
	
	public String getWagonName() {
		return name;
	}
	
	public String toString()
	{
		return name;
	}
	
	@Override
	public boolean equals(Object o)
	{
		return (o instanceof Wagon) && (this.name.equals(((Wagon)o).getWagonName())); 
	}

	public int getNumSeats() {
		return numSeats;
	}
}
