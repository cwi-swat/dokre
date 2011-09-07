package datahandler;
import java.util.ArrayList;

import domain.Train;
import domain.WagonType;

import GUI.output.GUIoutput;

public class DataHandler {
	private String message;
	
	private ArrayList<Train> trains;
	private ArrayList<WagonType> wagonTypes;
	private ArrayList<GUIoutput> output;
	
	public DataHandler(){
		message = "";
		trains = new ArrayList<Train>();
		wagonTypes = new ArrayList<WagonType>();
		output = new ArrayList<GUIoutput>();   
	}
	
	public void addOutput(GUIoutput o){
		output.add(o);
	}
	public boolean removeOutput(GUIoutput o){
		return output.remove(o);
	}

	public ArrayList<WagonType> getAllWagonTypes(){
		return wagonTypes;
	}
	
	public void addWagonType(WagonType type){
		wagonTypes.add(type);
		notifyOutputs();
	}
	
	//This method updates every wagon type
	public void updateWagonType(WagonType type){
		//for-loop and if-statement: it looks at the name of every wagon type
		//from the constructor and compares it to the parameter of this method
		//if this is the case, then set both parameters into the arraylist and notify output
		for(int i = 0; i < wagonTypes.size(); i++){
			if(wagonTypes.get(i).getName().equals(type.getName())){
				wagonTypes.set(i, type);
				notifyOutputs();				
			}
		}
	}
	
	//This method removes a wagon type from its train
	public void removeWagonType(WagonType type){
		// for every train, i = every train
		for(int i = 0; i < trains.size(); i++){

			// for every wagon, j = every wagon
			for(int j = 0; j < trains.get(i).getWagons().size(); j++){

				//if a train (i) with a wagon (j) equals to the wagon type, remove it
				if(trains.get(i).getWagons().get(j).getType().getName().equalsIgnoreCase(type.getName())){
					trains.get(i).getWagons().remove(j);
				}
			}
		}
		//finaly, if all the wagons are removed, remove the whole wagon type
		wagonTypes.remove(type);
		notifyOutputs();
	}	
	
	/**
	 * 
	 * this method returns a wagon type by giving a name
	 * 
	 * @param name Name or ID of the wagon
	 * @return Wagon or if wagon already exists <code>null</code>
	 */
	public WagonType getWagonTypeByName(String name){
		//for every wagon type, i = every wagon type
		for(int i = 0; i < wagonTypes.size(); i++){
			//if the wagon type (i) equals to the name, return the wagon type
			if(wagonTypes.get(i).getName().equalsIgnoreCase(name)) return wagonTypes.get(i);
		}
		return null;
	}
	
	public ArrayList<Train> getAllTrains(){
		return trains;
	}
	
	//this method simply adds a train with updating the output
	public void addTrain(Train t){
		trains.add(t);
		notifyOutputs();	
	}
	
	//This method updates every train
	public void updateTrain(Train t){
		//for-loop and if-statement: it looks at the name of every train
		//from the constructor and compares it to the parameter of this method
		//if this is the case, then set both parameters into the arraylist and update output
		for(int i = 0; i < trains.size(); i++){
			if(trains.get(i).getName().equals(t.getName())){
				trains.set(i, t);    			
			}
		}
		this.notifyOutputs(); 
	}
	
	//removes a whole train
	public void removeTrain(Train train){
		trains.remove(train);
		notifyOutputs();
	}	
	
	/**
	 * this method returns a train by giving a name
	 * 
	 * @param name Name or ID of the train.
	 * @return <code>Train</code> or <code>null</code> if train exists.
	 */
	public Train getTrainByName(String name){		
		//for every train, i = every train
		for(int i = 0;i < trains.size(); i++){		
			//if the train (i) equals to the name, return the train
			if(trains.get(i).getName().equalsIgnoreCase(name)){
				return trains.get(i);
			}
		}
		return null;
	}
	
	public void setMessage(String message){
		this.message = message;
		notifyOutputs();
	}
	
	public String getMessage(){
		return message;
	}
	
	//this method updates the output to the GUIOutput
	public void notifyOutputs(){
		//there is more than one output, so loop this
		for(int i = 0; i < output.size(); i++){
			//send the data to the abstract notifyOutput method
			output.get(i).notifyOutput(this);
//			output.get(i).invalidate();
		}
		// now set all numseats to false if needed
		// this to make sure that numseats only is showed once
		for(int i = 0; i < trains.size(); i++){
			trains.get(i).showNumSeats = false;
		}
		for(int i = 0; i < wagonTypes.size(); i++){
			wagonTypes.get(i).setShowNumSeats(false);
		}	
	}
}