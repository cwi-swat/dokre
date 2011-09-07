package model;

import java.util.HashSet;

public class Train {
	private String id;
	private HashSet<Wagon> wagons = new HashSet<Wagon>();

	/**
	 * Constructor for this class.
	 * 
	 * @param id Train ID.
	 */
	public Train(String newId) {
		setId(newId);
	}

	/**
	 * Get the number of seats in this train. This methods loops trough all available wagons.
	 * 
	 * 
	 * @return The number of seats.
	 */
	public int getNumSeats() {
		int seats = 0;
		for (Wagon wagon : wagons) {
			seats += wagon.getNumSeats();
		}
		return seats;
	}

	/**
	 * Add a wagon to this train.
	 * 
	 * @param newWagon The new wagon to be added.
	 */
	public void addWagon(Wagon newWagon) {
		wagons.add(newWagon);
	}

	/**
	 * Get the wagon with the same given wagon id.
	 * 
	 * @param id ID of the wagon.
	 * @return Null if no wagon is found or the wagon itself.
	 */
	public Wagon getWagon(String id) {
		for (Wagon wagon : wagons) {
			if (wagon.getId().equals(id)) {
				return wagon;
			}
		}
		return null;
	}

	/**
	 * Get all wagons in this train.
	 * 
	 * @return HashSet<Wagon> All the wagons.
	 */
	public HashSet<Wagon> getWagons() {
		return wagons;
	}

	/**
	 * Delte the given wagon from the train.
	 * 
	 * @param id ID of the wagon.
	 */
	public void deleteWagon(Wagon wagon) {
		wagons.remove(wagon);
	}

	/**
	 * Set the wagon id.
	 * 
	 * @param newId The id of the wagon
	 */
	private void setId(String newId) {
		id = newId;
	}

	/**
	 * Get the train id..
	 * 
	 * @return The id of the train.
	 */
	public String getId() {
		return id;
	}

}
