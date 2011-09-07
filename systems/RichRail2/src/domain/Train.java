package domain;
import java.util.ArrayList;
import gui.Observable;
import gui.Observer;


public class Train implements Observable {
	private ArrayList<Wagon> wagons = new ArrayList<Wagon>();
	private String id;
	private ArrayList<Observer> observers = new ArrayList<Observer>();

	public Train(String id){
		this.id = id;
		this.notifyObservers();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void addWagon(Wagon wagon) {
		this.wagons.add(wagon);
		notifyObservers();
	}

	public ArrayList<Wagon> getWagons() {
		return wagons;
	}

        public Boolean checkWagons(String id){
            for (Wagon w : wagons){
                if(w.getId().equals(id)){
                        return true;
                }
            }
            return false;
        }
	@Override
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
	
	public void removeWagon(Wagon wagon) {
		this.wagons.remove(wagon);
	}


}
