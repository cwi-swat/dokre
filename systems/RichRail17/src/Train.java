import java.util.ArrayList;

public class Train {

	protected static Train thomas = new Train("ThomasTrain");
	
	String name;
	ArrayList<Wagon> wagons;
	
	public Train(String Name)
	{
		name = Name;
		wagons = new ArrayList<Wagon>();
	}
	
	public void addWagon(Wagon w)
	{
		wagons.add(w);
	}
	
	public void removeWagon(Wagon w)
	{
		if (wagons.contains(w))
			wagons.remove(w);
	}
	
	public int wagonCount()
	{
		return wagons.size();
	}
	
	public ArrayList<Wagon> getWagons()
	{
		return wagons;
	}
	
	public String getTrainName()
	{
		return name;
	}
	
	public Wagon findWagon(String wagon)
	{
		for (Wagon w : thomas.getWagons())
		{
			if (w.getWagonName().equals(wagon))
			{
				return w;
			}
		}
		
		return null;
	}
	
	public String toString()
	{
		String s = "Trein";
		
		for (Wagon w : wagons)
		{
			s += " " + w.toString();
		}
		
		return s;
	}
	
	@Override
	public boolean equals(Object o)
	{
		return (o instanceof Train) && ((Train)o).getTrainName().equals(this.getTrainName());
	}

	public static boolean newWagon(String name) {
		if (!thomas.getWagons().contains(new Wagon(name)))
		{
			thomas.addWagon(new Wagon(name)); return true;
		}
		else
		{	
			return false;
		}
	}

	public static boolean newWagon(String name, int numseats) {
		if (!thomas.getWagons().contains(new Wagon(name)))
		{
			thomas.addWagon(new Wagon(name, numseats)); return true;
		}
		else
		{	
			return false;
		}
	}
	
	public static Train comeHereThomas()
	{
		return thomas;
	}
}
