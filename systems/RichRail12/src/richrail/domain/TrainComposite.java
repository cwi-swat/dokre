package richrail.domain;

import java.util.LinkedList;

/**
 * A composite unit
 * 
 * @author Jan Michiel ter Reehorst
 *
 */
public class TrainComposite {

	private int numSeats;
	
	public static final int DEFAULT_NUM_SEATS = 20;
	public static final String TRAIN = "train";
	public static final String WAGON = "wagon";

	private String id, type;
	private LinkedList<TrainComposite> children;
	
	/**
	 * Constructor
	 * 
	 * @param type
	 * @param id
	 */
	protected TrainComposite(String type, String id) {
		this.setId(id);
		this.setType(type);
		this.children = new LinkedList<TrainComposite>();
		if(type.equals(TRAIN)) {
			this.numSeats = 0;
		} else {
			this.numSeats = DEFAULT_NUM_SEATS;
		}
	}
	
	/**
	 * Constructor
	 * 
	 * @param type
	 * @param id
	 * @param numSeats
	 */
	protected TrainComposite(String type, String id, int numSeats) {
		this(type, id);
		
		if(!type.equals(TRAIN)) {
			this.numSeats = numSeats;
		}
	}

	/**
	 * Set the id
	 * 
	 * @param id
	 */
	private void setId(String id) {
		this.id = id;
	}

	/**
	 * Set the type
	 * 
	 * @param type
	 */
	private void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @param type
	 * @return A boolean weather this <code>type</code> equals 
	 * the given <code>type</code>
	 */
	public boolean hasType(String type) {
		return this.type.equals(type);
	}

	/**
	 * 
	 * @return The <code>id</code> of this unit
	 */
	public String getId() {
		return id;
	}
	
	public String getType() {
		return this.type;
	}
	
	/**
	 * 
	 * @return The total number of seats in this unit and in all of its children
	 */
	public int getNumSeats() {
		int total = numSeats;
		for(TrainComposite child : this.children) {
			total += child.getNumSeats();
		}
		return total;
	}

	/**
	 * 
	 * @return A copy of all the children 
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<TrainComposite> getUnits() {
		return (LinkedList<TrainComposite>) children.clone();
	}

	/**
	 * Add a child
	 * 
	 * @param unit
	 * @return
	 */
	public boolean addUnit(TrainComposite unit) {
		return this.children.add(unit);
	}

	/**
	 * Remove a child
	 * 
	 * @param unit
	 * @return
	 */
	public boolean removeUnit(TrainComposite unit) {
		return this.children.remove(unit);
	}
	
	/**
	 * (<code>id:numseats</code>)
	 * -<code>child1.toString()</code>-<code>child2.toString()</code>
	 */
	public String toString() {
		String ret = "("+this.getId()+":"+this.getNumSeats()+")";
		for(TrainComposite child : children) {
			ret += "-"+child.toString();
		}
		return ret;
	}
}
