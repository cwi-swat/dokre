package richrail.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Observable;

import richrail.tools.Log;

/**
 * A garage that keeps all trains and wagons
 * 
 * @author Jan Michiel ter Reehorst
 *
 */
public class ObserverableTrainDepot extends Observable {
	
	/**
	 * Train parts are saved in this map with key <code>type:id</code>
	 */
	private LinkedHashMap<String, TrainComposite> units;
	
	/**
	 * This is used to "glue" the units HashMap key parts together
	 */
	private static final String GLUE = ":";
	
	/**
	 * These are strings that format output
	 */
	private static final String
		CREATED 			= "%s %s created",
		CREATEDWITHSEATS 	= "%s %s created with %s seats",
		ALREADY_EXISTS		= "%s %s already exists",
		DELETED				= "%s %s deleted",
		WAGONTOTRAIN		= "wagon %s added to train %s",
		WAGONFROMTRAIN		= "wagon %s removed from train %s",
		DOESNOTEXIST		= "%s %s does not exist",
		NUMSEATS			= "number of seats in %s %s: %s";
	
	/**
	 * Constructor that initializes the units HashMap
	 */
	public ObserverableTrainDepot() {
		this.units = new LinkedHashMap<String, TrainComposite>();
	}
	
	/**
	 * Add a new unit with <code>type</code>, <code>id</code> 
	 * and <code>numSeats</code>
	 * 
	 * @param type
	 * @param id
	 * @param numSeats
	 * @return The newly created unit
	 */
	public TrainComposite newUnit(String type, String id, Integer numSeats) {
		// Check if the unit already exists with that type and id
		if(!this.units.containsKey(type+GLUE+id)) {
			TrainComposite unit;
			
			if(numSeats==null) {
				// Without seats
				unit = this.units.put(type+GLUE+id, new TrainComposite(type,id));
				this.logAndUpdate(CREATED, type, id);
			} else {	
				// With seats
				unit = this.units.put(type+GLUE+id, new TrainComposite(type,id,numSeats));
				this.logAndUpdate(CREATEDWITHSEATS, type, id, numSeats);				
			}
			return unit;
		} 
		// If the unit already exists with that name
		else {
			Log.log(this, String.format(ALREADY_EXISTS, type, id),Log.ERROR);
			return this.getUnit(type, id);
		} 
	}
	
	/**
	 * Add a new unit with <code>type</code> and <code>id</code> if there is not a
	 * unit with that type and id already
	 *
	 * @param type
	 * @param id
	 * @return The newly created unit or the existing unit with that type and id
	 */
	public TrainComposite newUnit(String type, String id) {
		return this.newUnit(type, id, null);
	}

	/**
	 * Add the given unit to the TrainDepot. This used for adding deleted units.
	 * 
	 * @param unit
	 */
	public void addUnit(TrainComposite unit) {
		if(!this.units.containsKey(unit.getType()+GLUE+unit.getId())) {
			this.units.put(unit.getType()+GLUE+unit.getId(), unit);
			this.logAndUpdate(CREATED, unit.getType(), unit.getId());
		} 
		// If the unit already exists with that name
		else {
			Log.log(this, String.format(ALREADY_EXISTS, unit.getType(), unit.getId()),Log.ERROR);
		} 		
	}
	
	/**
	 * Completely delete a unit from the <tt>Garage</tt>
	 * 
	 * @param type
	 * @param id
	 * @return The deleted unit 
	 */
	public TrainComposite delete(String type, String id) {
		if(getUnit(type,id) != null) {
			TrainComposite unit = this.units.remove(type+GLUE+id);
			this.logAndUpdate(DELETED, type, id);
			return unit;
		}
		return null;
	}
	
	/**
	 * Add a wagon to a train using their id's
	 * 
	 * @param wagonId
	 * @param trainId
	 * @return Return <code>true</code> if successful, <code>false</code> otherwise 
	 */
	public boolean addWagonToTrain(String wagonId, String trainId) {
		// Find the wagon and train
		TrainComposite wagon = this.getUnit(TrainComposite.WAGON, wagonId);
		TrainComposite train = this.getUnit(TrainComposite.TRAIN, trainId);
		
		// Try to add the wagon to the train
		if(train != null && wagon != null && train.addUnit(wagon)) {
			this.logAndUpdate(WAGONTOTRAIN, wagonId, trainId);
			return true;
		}
		return false;
	}
	
	/**
	 * Remove a wagon from a train using their id's
	 * 
	 * @param wagonId
	 * @param trainId
	 * @return Return <code>true</code> if successful, <code>false</code> otherwise 
	 */
	public boolean removeWagonFromTrain(String wagonId, String trainId) {
		// Find the wagon and train
		TrainComposite wagon = this.getUnit(TrainComposite.WAGON, wagonId);
		TrainComposite train = this.getUnit(TrainComposite.TRAIN, trainId);
		
		// Try to remove the wagon from the train
		if(train != null && wagon != null && train.removeUnit(wagon)) {
			this.logAndUpdate(WAGONFROMTRAIN, wagonId, trainId);
			return true;
		}
		return false;
	}
	
	/**
	 * Get a <tt>TrainUnit</tt> from the garage
	 * 
	 * @param type
	 * @param id
	 * @return The unit with type <code>type</code> and id <code>id</code> 
	 * or <code>null</code> if the unit does not exist
	 */
	private TrainComposite getUnit(String type, String id) {
		TrainComposite unit = this.units.get(type+GLUE+id);
		if(unit != null && unit.hasType(type)) {
			return unit;
		} else {
			Log.log(this,String.format(DOESNOTEXIST, type, id), Log.ERROR);
			return null;
		}
	}
	
	/**
	 * 
	 * @return All units in the <tt>Garage</tt>
	 */
	public Collection<TrainComposite> getUnits() {
		return this.units.values();
	}
	
	/**
	 * Retrieve the number of seats by type and id
	 * 
	 * @param type
	 * @param id
	 * @return Number of seats
	 */
	public int getNumSeats(String type, String id) {
		TrainComposite unit = this.getUnit(type, id);
		
		if(unit != null) {
			int seats = unit.getNumSeats();
			this.logAndUpdate(NUMSEATS,type,id,seats);
			return seats;
		} else {
			return 0;
		}
	}
	
	/**
	 * Logs a change using String.format(). Then update all observers
	 * 
	 * @param format
	 * @param args
	 */
	private void logAndUpdate(String format, Object ... args) {
		Log.output(this,String.format(format, args));
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * String format of the <tt>Garage</tt><br/>
	 * Wagons:<br/>
	 * <tt>wagon1.toString()-wagon2.toString()</tt><br/>
	 * Trains:<br/>
	 * <tt>train1.toString()-wagon.toString()-wagon.toString()</tt><br/>
	 * <tt>train2.toString()-wagon.toString()</tt><br/>
	 * 
	 * @see richrail.domain.TrainComposite#toString()
	 */
	public String toString() {
		String wagonString = "Wagons:\n";
		String trainString = "Trains:\n";
		for(TrainComposite unit : this.getUnits()) {
			if(unit.hasType(TrainComposite.TRAIN)) {
				trainString += unit.toString()+"\n";
			} else if(unit.hasType(TrainComposite.WAGON)) {
				wagonString += unit.toString();
			}
		}		
		return wagonString+"\n"+trainString;
	}
}
