package richrail.domain;

/**
 *
 */
public class Wagon extends RollingStock {

	 /**
		*
		* @param ID
		*/
	 public Wagon(String ID) {
			this(ID, 20);
	 }

	 /**
		*
		* @param ID
		* @param seats
		*/
	 public Wagon(String ID, int seats) {
			setID(ID);
			setNumberOfSeats(seats);
	 }

	 @Override
	 public String toString() {
			return "wagon " + getID() + " with " + getNumberOfSeats() + " seats";
	 }
}
