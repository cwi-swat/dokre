package Facade;

import java.util.ArrayList;

import Domain.Train;
import Domain.Wagon;



public class TrainFacadeSingleton extends TrainObservable{
	
	private ArrayList<Train> trains = new ArrayList<Train>();
	private ArrayList<Wagon> wagons = new ArrayList<Wagon>();
	private ArrayList<TrainObserver> observers = new ArrayList<TrainObserver>();
	private ArrayList<String> logs = new ArrayList<String>();
	private ArrayList<String> commandoutputs = new ArrayList<String>();
	
	public ArrayList<String> getCommandoutputs() {
		return commandoutputs;
	}
	static TrainFacadeSingleton tfs = new TrainFacadeSingleton();
	
	private TrainFacadeSingleton() {}

	public ArrayList<Train> getTrains() {
		return trains;
	}

	public void setTrains(ArrayList<Train> trains) {
		this.trains = trains;
	}

	public ArrayList<Wagon> getWagons() {
		return wagons;
	}

	public void setWagons(ArrayList<Wagon> wagons) {
		this.wagons = wagons;
	}

	public static TrainFacadeSingleton getTfs() {
		return tfs;
	}
	@Override
	public void detachObserver(TrainObserver to) {
		observers.remove(to);
	}
	public void notifyObservers() {
		for(TrainObserver to : observers) {
			to.update();
		}
	}
	public void attachObserver(TrainObserver to) {
		observers.add(to);
	}
	public void addTrain(Train t) {
		trains.add(t);
		notifyObservers();
	}
	public void addWagon(Wagon w) {
		wagons.add(w);
		notifyObservers();
	}

	public ArrayList<String> getLogs() {
		return logs;
	}
	public void addLog(String s) {
		logs.add(s);
		notifyObservers();
	}

	public void setLogs(ArrayList<String> logs) {
		this.logs = logs;
	}
	public void addCommandOutput(String s) {
		commandoutputs.add(s);
		notifyObservers();
	}

	public void removeTrain(Train t) {
		trains.remove(t);
		notifyObservers();
	}
	
	public void removeWagonFromTrain(Train t, Wagon w){
		t.RemoveWagon(w);
		notifyObservers();
	}

	public void deleteWagon(Wagon w) {
		wagons.remove(w);
		notifyObservers();
		for(Train t: getTrains()) {
			for(Wagon wg1: t.getWagons()) {
				if(w.getNaam().equals(wg1.getNaam())){
					t.RemoveWagon(wg1);
					notifyObservers();
					return;
				}
			}
		}
	}

	
	
}
