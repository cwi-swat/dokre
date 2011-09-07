package richrail.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class Depot extends Observable{
	
	List<Train> trains = new ArrayList<Train>();
	List<Wagon> wagons = new ArrayList<Wagon>();
	
	// Implement train service
	public void createTrain(String name){
		this.setChanged();
		Train tr = this.getTrainByName(name);
		if(tr == null){
			this.notifyObservers("Train " + name + "created");
			this.trains.add(new Train(name));
		} else {
			this.notifyObservers("Train " + name + " already exists");
		}
	}
	
	public void removeTrain(Train train){
		this.setChanged();
		if(trains.remove(train)){
			this.notifyObservers("Train "+train.getName()+" removed");
		} else {
			this.notifyObservers("Train "+train.getName()+" does not exist");
		}
	}
	
	public void addWagonToTrain(Train train, Wagon wagon){
		this.setChanged();
		train.addWagon(wagon);
		this.notifyObservers("Wagon "+wagon.getName()+" added to Train "+train.getName());
	}
	
	public void removeWagonFromTrain(Train train, Wagon wagon){
		this.setChanged();
		train.removeWagon(wagon);
		this.notifyObservers("Wagon "+wagon.getName()+" removed from Train "+train.getName());
	}
	
	public List<Train> getTrains(){
		return this.trains;
	}
	
	public Train getTrainByName(String name){
		for(Train tr : this.trains){
			if(tr.getName().equals(name)) return tr;
		}
		return null;
	}
	
	// Implement wagon service
	public void createWagon(String name, int seats){
		this.setChanged();
		Wagon wg = this.getWagonByName(name);
		if(wg == null){
			this.wagons.add(new Wagon(name, seats));
			this.notifyObservers("Wagon " + name + " created with " + seats + " seats");
		} else {
			this.notifyObservers("Wagon " + name + " already exists");
		}
	}
	
	public void createWagon(String name){
		createWagon(name, 20);
	}
	
	public void removeWagon(Wagon wagon){
		
		this.setChanged();
		if(wagons.remove(wagon)){
			this.notifyObservers("Wagon "+wagon.getName()+" removed");
		} else {
			this.notifyObservers("Wagon "+wagon.getName()+" does not exist");
		}
		
	}
	
	public Wagon getWagonByName(String name){
		for(Wagon wg : this.wagons){
			if(wg.getName().equals(name)) return wg;
		}
		return null;
	}
	
	public List<Wagon> getWagons(){
		return this.wagons;
	}
	
}
