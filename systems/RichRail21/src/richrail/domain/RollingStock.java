package richrail.domain;

import java.util.regex.Pattern;
import richrail.exceptions.InvalidIDException;

/**
 * 
 */
public abstract class RollingStock {

	private int numberOfSeats = 0;
	private String ID;// This parameter is private, because it must be validated
						// by setID

	/**
	 * 
	 * @return
	 */
	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	/**
	 * 
	 * @param seats
	 */
	public void setNumberOfSeats(int seats) {
		if (seats < 0) {
			throw new IllegalArgumentException();
		} else {
			this.numberOfSeats = seats;
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getID() {
		return ID;
	}

	/**
	 * 
	 * @param ID
	 * @throws InvalidIDException
	 */
	public void setID(String ID) throws InvalidIDException {
		if (Pattern.matches("[a-z][a-z|0-9]*", ID)) {
			this.ID = ID;
		} else {
			throw new InvalidIDException();
		}
	}

}
