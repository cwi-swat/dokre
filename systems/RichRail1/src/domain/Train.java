package domain;

import java.util.ArrayList;

import logic.Observer;
import logic.Subject;

public abstract class Train implements Subject {
	protected String name;
	private ArrayList<Wagon> wagons = new ArrayList<Wagon>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	public Train()
	{
		this.name = "";
		this.wagons = new ArrayList<Wagon>();
	}
	
	public Train(String name)
	{
		this();
		this.name = name;
		this.wagons = new ArrayList<Wagon>();
	}
	
	public Train(String name, ArrayList<Wagon> wagons)
	{
		this(name);
		this.wagons = wagons;
	}
	
	public String getName()
	{
		return name;
	}

	public ArrayList<Wagon> getWagons()
	{
		return wagons;
	}
	
	public void setName(String name)
	{
		this.name = name;
		notifyObservers();
	}
	
	public void setWagons(ArrayList<Wagon> wagons)
	{
		this.wagons = wagons;
		notifyObservers();
	}
	
	public void addWagon(Wagon wagon)
	{
		this.wagons.add(wagon);
		notifyObservers();
	}
	
	public void removeWagon(Wagon wagon)
	{
		if(wagons.contains(wagon))
		{
			wagons.remove(wagon);
			notifyObservers();
		}
	}
	
	public void addObserver(Observer observer)
	{
		observers.add(observer);
	}
	
	public void removeObserver(Observer observer)
	{
		observers.remove(observer);
	}
	
	public void notifyObservers()
	{
		for(Observer observer : observers)
		{
			observer.update();
		}
	}

	@Override
	public String toString() {
		return "Train [name=" + name + ", wagons="
				+ wagons + "]";
	}
	
	
}
