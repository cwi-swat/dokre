package models;

import java.util.ArrayList;
import java.util.Iterator;

import views.Observer;

public class WagonType implements Observable {
	private static int wagonID = 0;
	private int seats;
	private String name;
	public ArrayList<Wagon> wagons = new ArrayList<Wagon>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();

	public WagonType(String name, int seats) {
		this.name = name;
		this.seats = seats;
	}

	public WagonType(String name) {
		this.name = name;
		this.seats = 20;
	}

	//Getters & setters
	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Wagon> getWagons(){
		return this.wagons;
	}

	//Create new Wagon & Put it in a list
	public Wagon new_wagon() {
		wagonID++;
		if (!contains(wagonID)){
			Wagon wagon = new Wagon(wagonID, this);
			wagons.add(wagon);
			notifyObservers();
			return wagon;
		} else{
			notifyObservers();
		}
		return null;
	}

	public boolean contains(int wagon_id){
		if (wagons.isEmpty()){
			return false;
		}
		else{
			for (Wagon w : wagons) {
				if ((w.getWagon_id() == wagon_id)){
					return true;
				}
			}
			return false;
		}
	}	

	public String toString(){
		return "("+this.getName()+":"+this.getSeats()+")";
	}
	// Observable methods

	public void notifyObservers() {
		// loop through and notify each observer
		Iterator<Observer> i = observers.iterator();
		while (i.hasNext()) {
			Observer o = (Observer) i.next();
			o.refresh();
		}
	}

	public void register(Observer obs) {
		observers.add(obs);	
	}

	public void unRegister(Observer obs) {
		observers.remove(obs);	
	}
}
