package richrail.control;

import richrail.domain.Train;
import richrail.domain.Wagon;

import java.util.List;
import java.util.Set;

public interface TrainDataProvider extends Observable {
	List<Train> getTrains();

	Set<Wagon> getWagons();

	void addTrain(String trainName);

	boolean deleteTrain(String trainName);

	void addWagon(String wagonName, int numberOfSeats);

	boolean deleteWagon(String wagonName);

	boolean addWagonToTrain(String wagonName, String trainName);

	boolean addTrainToTrain(String trainName, String toTrainName);

	int getNumberOfSeatsOfWagon(String wagonName);

	int getNumberOfSeatsOfTrain(String trainName);

	boolean removeWagonFromTrain(String wagonName, String trainName);

	boolean removeTrainFromTrain(String trainName, String fromTrainName);

    Logger getLogger();
}
