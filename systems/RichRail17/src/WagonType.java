
public class WagonType {
	int numSeats;
	private String type;
	
	public WagonType(String Type, int NumSeats)
	{
		type = Type;
		numSeats = NumSeats;
	}
	
	public String getType()
	{
		return type;
	}
	
	public int getNumSeats()
	{
		return numSeats;
	}
}
