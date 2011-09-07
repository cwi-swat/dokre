package domain;

import java.awt.Graphics;
import java.util.ArrayList;

import logic.Observer;
import logic.Subject;

public abstract class Wagon implements Subject {
	protected String name;
	protected int seats;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	public Wagon(){
		this.name = "";
		this.seats = 20;
	}
	
	public Wagon(String name)
	{
		this();
		this.name = name;
	}
	
	public Wagon(String name, int seats)
	{
		this(name);
		this.seats = seats;
	}

	public String getName() {
		return name;
	}

	public int getSeats() {
		return seats;
	}

	public void setName(String name) {
		this.name = name;
		notifyObservers();
	}

	public void setSeats(int seats) {
		this.seats = seats;
		notifyObservers();
	}
	
	public abstract void draw(Graphics g, int x, int y);
	
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
		return "Wagon [name=" + name + ", seats=" + seats + "]";
	}
}
