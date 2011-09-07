package model;

public class Wagon {

	private String id;
	private int numseats = 0;

	/**
	 * Constructor voor het aanmaken van een nieuwe wagon.
	 * 
	 * @param newId Wagon ID.
	 * @param newNumseats Het aantal zitplaatsen.
	 */
	public Wagon(String newId, int newNumseats) {
		setId(newId);
		setNumSeats(newNumseats);
	}

	/**
	 * Zet het aantal zitplaatsen voor deze wagon.
	 * 
	 * @param newNumseats Het aantal zitplaatsen.
	 */
	private void setNumSeats(int newNumseats) {
		numseats = newNumseats;
	}

	/**
	 * Geef het aantal zitplaatsen.
	 * 
	 * @return Het aantal zitplaatsen.
	 */
	public int getNumSeats() {
		return numseats;
	}

	/**
	 * Geef de ID van dit wagon.
	 * 
	 * @return De wagon ID.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Zet de wagon ID.
	 * 
	 * @param newId ID van de wagon.
	 */
	private void setId(String newId) {
		id = newId;
	}

}
