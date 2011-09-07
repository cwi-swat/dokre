package richrail.domain;

/**
 *
 */
public class Locomotive extends RollingStock {

	 /**
		*
		* @return
		*/
	 @Override
	 public int getNumberOfSeats() {
			return 0;
	 }

	 @Override
	 public String toString() {
			return "Locomotive " + getID() + "\n";
	 }
}
