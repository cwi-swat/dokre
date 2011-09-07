package Facade;

public abstract class TrainObservable {
	
	public TrainObservable(){}
	public abstract void attachObserver(TrainObserver to);
	public abstract void detachObserver(TrainObserver to);
	public abstract void notifyObservers();


}
