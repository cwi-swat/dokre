package richrail.control;

import richrail.domain.Seatable;
import richrail.domain.Train;
import richrail.domain.Wagon;

import java.util.*;

public class TrainController implements TrainDataProvider {

	private final List<Observer> observers = new ArrayList<Observer>();
	private final Map<String, Train> trains = new LinkedHashMap<String, Train>();
	private final Map<String, Wagon> wagons = new LinkedHashMap<String, Wagon>();
	private final Logger logger = new ListLogger();


	@Override
	public void addTrain(String trainName) {
		trains.put(trainName, new Train(trainName));
		logger.addLogEntry("train " + trainName + " created");
		notifyObservers();
	}

	@Override
	public void addWagon(String wagonName, int numberOfSeats) {
		logger.addLogEntry("wagon " + wagonName + " created with " + numberOfSeats + " seats");
		wagons.put(wagonName, new Wagon(wagonName, numberOfSeats));
		notifyObservers();
	}

	@Override
	public boolean addTrainToTrain(String trainName, String toTrainName) {
		Train train = trains.get(trainName);
		Train toTrain = trains.get(toTrainName);

		if (train != null && toTrain != null) {
			toTrain.addSeatable(train);

			logger.addLogEntry("Train: " + trainName + " added to train: " + toTrainName + "!");
			return true;
		}

		logger.addLogEntry("The train: " + toTrainName + " does not exist!");
		notifyObservers();
		return false;
	}

	@Override
	public boolean addWagonToTrain(String wagonName, String trainName) {
		Train train = trains.get(trainName);
		Wagon wagon = wagons.get(wagonName);

		if (train != null && wagon != null) {
			train.addSeatable(wagon);

			logger.addLogEntry("wagon " + wagonName + " added to train " + trainName);
			notifyObservers();
			return true;
		} else {
			if (train == null) {
				logger.addLogEntry("Unknown train: '" + trainName + "'");
			} else {
				logger.addLogEntry("Unknown wagon: '" + wagonName + "'");
			}
			return false;
		}
	}

	private void notifyObservers() {
		for (Observer observer : observers) {
			observer.notifyChange();
		}
	}

	@Override
	public boolean deleteTrain(String trainName) {
		boolean isRemoved = trains.remove(trainName) == null;

		if (isRemoved) {
			logger.addLogEntry("train " + trainName + " does not exist");
		} else {
			logger.addLogEntry("train " + trainName + " deleted");
			notifyObservers();
		}

		notifyObservers();
		return isRemoved;
	}

	@Override
	public boolean deleteWagon(String wagonName) {
		boolean isRemoved = wagons.remove(wagonName) == null;

		if (isRemoved) {
			logger.addLogEntry("wagon " + wagonName + " does not exist");
		} else {
			logger.addLogEntry("wagon " + wagonName + " deleted");
			notifyObservers();
		}
		return isRemoved;
	}

	@Override
	public int getNumberOfSeatsOfWagon(String wagonName) {
		return getNumberOfSeatsOfSeatable(wagons.get(wagonName), "wagon", wagonName);
	}

	@Override
	public int getNumberOfSeatsOfTrain(String trainName) {
		return getNumberOfSeatsOfSeatable(trains.get(trainName), "train", trainName);
	}

	private int getNumberOfSeatsOfSeatable(Seatable seatable, String type, String name) {
		if (seatable != null) {
			logger.addLogEntry("number of seats in " + type + " " + seatable.getName() + ":" + seatable.getNumberOfSeats());
			return seatable.getNumberOfSeats();
		} else {
			logger.addLogEntry("Unknown " + type + " '" + name + "'");
		}
		return -1;
	}


	@Override
	public boolean removeWagonFromTrain(String wagonName, String trainName) {
		Train train = trains.get(trainName);
		Wagon wagon = wagons.get(wagonName);

		if (train != null && wagon != null && train.removeSeatable(wagon)) {
			logger.addLogEntry("Wagon: " + wagonName + " removed from train: " + trainName + "!");
			return true;
		}

		logger.addLogEntry("Couldn't remove: " + wagonName + " from train: " + trainName + "!");
		notifyObservers();
		return false;
	}

	@Override
	public boolean removeTrainFromTrain(String trainName, String fromTrainName) {
		Train train = trains.get(trainName);
		Train fromTrain = trains.get(fromTrainName);

		if (fromTrain != null && train != null && fromTrain.removeSeatable(train)) {
			logger.addLogEntry("Train: " + trainName + " removed from train: " + fromTrainName + "!");
			return true;
		}

		logger.addLogEntry("Couldn't remove train: " + trainName + " from train: " + fromTrainName + "!");
		notifyObservers();
		return false;
	}

	@Override
	public List<Train> getTrains() {
		return new ArrayList<Train>(trains.values());
	}

	@Override
	public Set<Wagon> getWagons() {
		return new HashSet<Wagon>(wagons.values());
	}


	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public Logger getLogger() {
		return logger;
	}
}
