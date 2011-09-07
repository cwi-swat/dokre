package Domain;

import java.util.ArrayList;

public class Train {
	private String Name;
	private ArrayList<Wagon> Wagons;
	
	public Train(){
		
	}
	public Train(String nm){
		Name = nm;
		Wagons = new ArrayList<Wagon>();
	}
	
	public String getName()
	{
		return Name;
	}

	public void setName(String nm)
	{
		this.Name = nm;
	}
	public ArrayList<Wagon> getWagons(){
		return Wagons;
	}
	public void addWagon(Wagon w){
		Wagons.add(w);
	}
	public void removeWagon(Wagon w){
		Wagons.remove(w);
	}
}