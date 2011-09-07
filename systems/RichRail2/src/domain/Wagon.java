package domain;

import java.util.ArrayList;
import gui.Observable;
import gui.Observer;

public class Wagon implements Observable {
	private String id;
	private Type type;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	public Wagon(String id, Type tp){
		this.id = id;
		this.type = tp;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public void notifyObservers() {
		// Send notify to all Observers
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
	
	
}
