package domain;

import java.util.ArrayList;

import gui.Observable;
import gui.Observer;

public class Type implements Observable{
	private String id;
	private int numberOfSeats = 20;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	public Type(String id){
		this.id = id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	
	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer) observers.get(i);
			observer.refreshData();
		}
	}
	@Override
	public void register(Observer obs) {
		observers.add(obs);

	}

	@Override
	public void unRegister(Observer obs) {
		observers.remove(obs);	
	}
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	
}
