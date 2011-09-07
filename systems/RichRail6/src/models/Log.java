package models;

import java.util.ArrayList;
import java.util.Iterator;

import views.Observer;

public class Log implements Observable {
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private String performedAction;
	public Log(String perf){
		this.performedAction = perf;
	}
	public String toString(){
		return this.performedAction;
	}
	@Override
	public void notifyObservers() {
		Iterator<Observer> i = observers.iterator();
		while (i.hasNext()) {
			Observer o = (Observer) i.next();
			o.refresh();
		}
	}
	@Override
	public void register(Observer obs) {
		this.observers.add(obs);
	}
	@Override
	public void unRegister(Observer obs) {
		this.observers.remove(obs);
	}
}
