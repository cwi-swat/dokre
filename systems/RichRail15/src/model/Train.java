package model;

import java.util.ArrayList;

public class Train
{
	private String id;
	private ArrayList<Wagon> wagons = new ArrayList<Wagon>();
	
	public Train(String id)
	{
		this.id = id;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void addWagon(Wagon wagon)
	{
		this.wagons.add(wagon);
	}
	
	public int getNumSeats()
	{
		int num = 0;
		for(Wagon wagon : this.wagons)
		{
			num += wagon.getNumSeats();
		}
		return num;
	}
	
	public Wagon searchWagon(String id)
	{
		for(Wagon wagon : this.wagons)
		{
			if(wagon.getId().equals(id))
			{
				return wagon;
			}
		}
		
		return null;
	}
	
	public void removeWagon(Wagon wagon)
	{
		this.wagons.remove(wagon);
	}
	
	
	public String toString(){
		String s = "";
			s += "("+ id +")";
			
			if (wagons.isEmpty() == true){
				
			}
			
			else
			{
			for(Wagon wagon :  wagons)
			{ 	
				s+=  "-("+ wagon.getId() +")"; 
			}
			}
 
		return s;
			
	
	}
}
