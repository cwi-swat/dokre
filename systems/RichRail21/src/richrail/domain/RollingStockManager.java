package richrail.domain;

import richrail.exceptions.TrainExistsException;
import java.util.Enumeration;
import richrail.exceptions.WagonNotFoundException;
import java.util.Hashtable;
import java.util.Iterator;
import richrail.exceptions.InvalidIDException;
import richrail.exceptions.TrainNotFoundException;
import richrail.exceptions.WagonInUseException;

/**
 *
 */
public class RollingStockManager {

	 private Hashtable<String, Train> trains = new Hashtable<String, Train>();
	 private Hashtable<String, Wagon> wagons = new Hashtable<String, Wagon>();
	 private static RollingStockManager instance = null;

	 /**
	  *
	  */
	 protected RollingStockManager() {
			// Exists only to defeat instantiation.
	 }

	 /**
	  *
	  * @return
	  */
	 public static RollingStockManager getInstance() {
			if (instance == null) {
				 instance = new RollingStockManager();
			}
			return instance;
	 }

	 /**
	  *
	  * @param ID
	  * @return
	  * @throws WagonNotFoundException
	  */
	 public Wagon getWagon(String ID) throws WagonNotFoundException {
			Wagon wagon = wagons.get(ID);
			if (wagon == null) {
				 throw new WagonNotFoundException();
			}
			return wagon;
	 }

	 /**
	  *
	  * @param ID
	  * @return
	  * @throws TrainNotFoundException
	  */
	 public Train getTrain(String ID) throws TrainNotFoundException {
			Train train = trains.get(ID);
			if (train == null) {
				 throw new TrainNotFoundException();
			}
			return train;
	 }
	 /**
	  * @return Hashtable<String, Train>
	  */
	 public Hashtable<String, Train> getTrains(){
		 return this.trains;
	 }

	 /**
		*
		* @return
		*/
	 public Hashtable<String, Wagon> getAvailableWagons() {
			Enumeration<String> iterator = wagons.keys();
			Hashtable<String, Wagon> returnWagons = new Hashtable<String, Wagon>();
			while (iterator.hasMoreElements()) {
				 String wagonID = iterator.nextElement();
				 if (!wagonInUse(wagonID)) {
						returnWagons.put(wagonID, wagons.get(wagonID));
				 }
			}
			return returnWagons;
	 }

	 /**
	  *
	  * @param rollingstock
	  * @return
	  */
	 public int getNumberOfSeats(RollingStock rollingstock) {
			return rollingstock.getNumberOfSeats();
	 }

	 /**
	  *
	  * @param ID
		* @return
		* @throws InvalidIDException
		* @throws TrainExistsException 
	  */
	 public Train newTrain(String ID) throws InvalidIDException, TrainExistsException {
			if (trains.contains(ID)) {
				 throw new TrainExistsException();
			} else {
				 Train train = new Train(ID);
				 trains.put(train.getID(), train);
				 return train;
			}
	 }

	 /**
	  *
	  * @param ID
	  */
	 public void removeTrain(String ID) {
			trains.remove(ID);
	 }

	 /**
	  *
	  * @param ID
		* @return
		* @throws WagonNotFoundException
		* @throws InvalidIDException
	  */
	 public Wagon newWagon(String ID) throws WagonNotFoundException, InvalidIDException {
			Wagon wagon = null;
			if (wagons.contains(ID)) {
				 wagon = getWagon(ID);
			} else {
				 wagon = new Wagon(ID);
				 wagons.put(ID, wagon);
			}
			return wagon;
	 }

	 /**
	  *
	  * @param ID
	  * @param seats
		* @return
		* @throws WagonNotFoundException
		* @throws InvalidIDException
	  */
	 public Wagon newWagon(String ID, int seats) throws WagonNotFoundException, InvalidIDException {
			Wagon wagon = null;
			if (wagons.contains(ID)) {
				 wagon = getWagon(ID);
			} else {
				 wagon = new Wagon(ID, seats);
				 wagons.put(ID, wagon);
			}
			return wagon;
	 }

	 /**
	  *
	  * @param wagonID
		* @param trainID
		* @throws WagonNotFoundException
		* @throws TrainNotFoundException
		* @throws WagonInUseException
	  */
	 public void addWagonToTrain(String wagonID, String trainID) throws WagonNotFoundException, TrainNotFoundException, WagonInUseException {
			if (wagonInUse(wagonID)) {
				 throw new WagonInUseException();
			} else {
				 Train train = getTrain(trainID);
				 train.addWagon(getWagon(wagonID));
			}
	 }

	 /**
	  *
	  * @param wagonID
		* @param trainID
		* @throws TrainNotFoundException
	  */
	 public void removeWagonFromTrain(String wagonID, String trainID) throws TrainNotFoundException {
			Train train = getTrain(trainID);
			train.removeWagon(wagonID);
	 }

	 /**
	  *
	  * @param ID
		* @return
		* @throws WagonInUseException
	  */
	 public Wagon removeWagon(String ID) throws WagonInUseException {
			if (wagonInUse(ID)) {
				 return wagons.remove(ID);
			} else {
				 throw new WagonInUseException();
			}
	 }

	 /**
		*
		* @param ID
		* @return
		*/
	 public boolean wagonInUse(String ID) {
			Wagon champion = null;
			Enumeration<String> wagonNumbers = trains.keys();
			while (wagonNumbers.hasMoreElements() && champion == null) {
				 champion = getTrain(wagonNumbers.nextElement()).getWagon(ID);
			}
			return champion != null;
	 }
}
