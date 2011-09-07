package richrail.domain;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import richrail.exceptions.InvalidIDException;

/**
 * 
 */
public class Train extends RollingStock implements Observer {

	private Hashtable<String, Wagon> wagons;
	private Locomotive locomotive;
	private static int count = 0;
	private int number;

	/**
	 * 
	 * @param ID
	 * @throws InvalidIDException
	 */
	public Train(String ID) throws InvalidIDException {
		setID(ID);
		setNumber();
		wagons = new Hashtable<String, Wagon>();
		locomotive = new Locomotive();
	}

	/**
	 * 
	 * @param hash
	 * @return
	 */
	public Wagon getWagon(String hash) {
		return wagons.get(hash);
	}

	/**
	 * 
	 * @param wagon
	 * @throws NullPointerException
	 * @return Wagon
	 */
	public Wagon addWagon(Wagon wagon) throws NullPointerException {
		if (wagon == null) {
			throw new NullPointerException("wagon is null");
		}
		return wagons.put(wagon.getID(), wagon);
	}

	/**
	 * 
	 * @param ID
	 * @return
	 */
	public Wagon removeWagon(String ID) {
		return wagons.remove(ID);
	}

	/**
	 * 
	 * @return
	 */
	public Locomotive getLocomotive() {
		return locomotive;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public int getNumberOfSeats() {
		int numberOfSeats = 0;
		Enumeration<String> wagonNumbers = wagons.keys();
		while (wagonNumbers.hasMoreElements()) {
			numberOfSeats += wagons.get(wagonNumbers.nextElement())
					.getNumberOfSeats();
		}
		return numberOfSeats;
	}

	/**
	 * NumberOfSeats van een trein wordt berekend, dus deze kan niet gezet
	 * worden.
	 * 
	 * @param seats
	 */
	@Override
	public void setNumberOfSeats(int seats) {

	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("train: " + locomotive + "{\n");
		Enumeration<String> wagonNumbers = wagons.keys();
		while (wagonNumbers.hasMoreElements()) {
			str.append(wagons.get(wagonNumbers.nextElement()) + "\n");
		}
		str.append("}");
		return str.toString();
	}

	public void update(Observable o, Object arg) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 *
	 */
	public void setNumber() {
		this.number = Train.count;
		Train.count++;
	}

	/**
	 *
	 * @return
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * 
	 * @return
	 */
	public Hashtable<String, Wagon> getWagons(){
		return this.wagons;
	}
}
