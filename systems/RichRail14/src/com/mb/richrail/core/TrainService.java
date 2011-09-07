package com.mb.richrail.core;

import com.mb.richrail.data.Train;
import com.mb.richrail.data.Wagon;
import com.mb.richrail.data.WagonType;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class TrainService extends Observable {
    
    private static final int DEFAULT_SEATS = 20;

    private Logger logger;
    private ArrayList<Train> trains;
    private ArrayList<WagonType> wagonTypes;
    
    public TrainService() {
        logger = new Logger();
        trains = new ArrayList<Train>();
        wagonTypes = new ArrayList<WagonType>();
    }
    
    public void log(String message) {
        logger.log(message);
    }
    
    public void addLogObserver(Observer observer) {
        logger.addObserver(observer);
    }
    
    public void addTrain(String name) {
        if (findTrain(name) == null) {
            trains.add(new Train(name));
            setChanged();
            notifyObservers(trains);
            logger.log("Train " + name + " created");
        } else {
            logger.log("Train " + name + " already exists");
        }
    }
    
    public void deleteTrain(String name) {
        Train train = findTrain(name);
        if (train != null) {
            trains.remove(train);
            setChanged();
            notifyObservers(trains);
            logger.log("Train " + name + " deleted");
        } else {
            logger.log("Train " + name + " does not exist");
        }
    }
    
    private Train findTrain(String name) {
        for (Train train : trains) {
            if (train.getName().equalsIgnoreCase(name)) {
                return train;
            }
        }
        return null;
    }
    
    public void printNumOfSeatsTrain(String name) {
        Train train = findTrain(name);
        if (train != null) {
            logger.log("Number of seats in train " + train.getName() + ": " + train.getNumOfSeats());
        } else {
            logger.log("Train " + name + " does not exist");
        }
    }

    public int getNumOfSeatsTrain(String name) {
        Train train = findTrain(name);
        if (train != null) {
            return train.getNumOfSeats();
        }
        return 0;
    }
    
    public void addWagonType(String name) {
        addWagonType(name, DEFAULT_SEATS);
    }
    
    public void addWagonType(String name, int numOfSeats) {
        if (findWagonType(name) == null) {
            wagonTypes.add(new WagonType(name, numOfSeats));
            setChanged();
            notifyObservers(trains);
            logger.log("Wagon " + name + " created with " + numOfSeats + " seats");
        } else {
            logger.log("Wagon " + name + " already exists");
        }
    }
    
    public void deleteWagonType(String name) {
        WagonType wagonType = findWagonType(name);
        if (wagonType != null) {
            for (Train train : trains) {
                while (train.removeWagon(wagonType)) {}
            }
            logger.log("Wagon " + wagonType.getName() + " deleted");
            wagonTypes.remove(wagonType);
            setChanged();
            notifyObservers(trains);
        } else {
            logger.log("Wagon " + name + " does not exist");
        }
    }
    
    private WagonType findWagonType(String name) {
        for (WagonType wagonType : wagonTypes) {
            if (wagonType.getName().equalsIgnoreCase(name)) {
                return wagonType;
            }
        }
        return null;
    }
    
    public void printNumOfSeatsWagonType(String name) {
        WagonType wagonType = findWagonType(name);
        if (wagonType != null) {
            logger.log("Number of seats in wagon " + wagonType.getName() + ": " + wagonType.getNumOfSeats());
        } else {
            logger.log("Wagon " + name + " does not exist");
        }
    }

    public int getNumOfSeatsWagonType(String name) {
        WagonType wagonType = findWagonType(name);
        if (wagonType != null) {
            return wagonType.getNumOfSeats();
        }
        return 0;
    }
    
    public void addWagonToTrain(String trainName, String wagonTypeName) {
        Train train = findTrain(trainName);
        WagonType wagonType = findWagonType(wagonTypeName);
        if (train == null) {
            logger.log("Train " + trainName + " does not exist");
        } else if (wagonType == null) {
            logger.log("Wagon " + wagonTypeName + " does not exist");
        } else {
            Wagon wagon = new Wagon(wagonType);
            train.addWagon(wagon);
            setChanged();
            notifyObservers(trains);
            logger.log("Wagon " + wagonType.getName() + " added to train " + train.getName());
        }
    }
    
    public void removeWagonFromTrain(String trainName, String wagonTypeName) {
        Train train = findTrain(trainName);
        WagonType wagonType = findWagonType(wagonTypeName);
        if (train == null) {
            logger.log("Train " + trainName + " does not exist");
        } else if (wagonType == null) {
            logger.log("Wagon " + wagonTypeName + " does not exist");
        } else {
            if (train.removeWagon(wagonType)) {
                logger.log("Wagon " + wagonType.getName() + " removed from train " + train.getName());
                setChanged();
                notifyObservers(trains);
            } else {
                logger.log("Train " + train.getName() + " does not have any wagon " + wagonType.getName());
            }
        }
    }
    
    public ArrayList<String> getTrains() {
        ArrayList<String> trainStrings = new ArrayList<String>();
        for (Train train : trains) {
            trainStrings.add(train.getName());
        }
        return trainStrings;
    }
    
    public ArrayList<String> getWagonTypes() {
        ArrayList<String> wagonTypeStrings = new ArrayList<String>();
        for (WagonType wagonType : wagonTypes) {
            wagonTypeStrings.add(wagonType.getName());
        }
        return wagonTypeStrings;
    }
    
    public Observable getLogger() {
        return logger;
    }

    public ArrayList<String> getWagons(String trainName) {
        Train train = findTrain(trainName);
        ArrayList<String> wagons = new ArrayList<String>();
        if (train != null) {
            ArrayList<Wagon> trainWagons = train.getWagons();
            for(Wagon wagon : trainWagons) {
                wagons.add(wagon.getType().getName());
            }
        }
        return wagons;
    }
}
