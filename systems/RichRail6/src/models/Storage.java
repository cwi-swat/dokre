package models;

import java.util.ArrayList;

import views.Display;

public class Storage {

    private static Storage instance;
    private ArrayList<Display> displays;
    private ArrayList<Display> logdisplays;
    private ArrayList<Log> logs;
    private ArrayList<WagonType> wagontypes;
    public ArrayList<Train> trains;

    public Storage() {
        displays = new ArrayList<Display>();
        logdisplays = new ArrayList<Display>();
        logs = new ArrayList<Log>();
        wagontypes = new ArrayList<WagonType>();
        trains = new ArrayList<Train>();
    }

    public static synchronized Storage get() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
    //Displays

    public void addDisplay(Display display) {
        this.displays.add(display);
    }

    public void addDisplayAndRefresh(Display d) {
        addDisplay(d);
        for (Train t : trains) {
            addObservers(t);
        }
        for (WagonType wt : wagontypes) {
            addObservers(wt);
        }
    }

    public void addLogDisplay(Display display) {
        this.logdisplays.add(display);
    }

    public ArrayList<Display> getDisplays() {
        return this.displays;
    }

    public ArrayList<Display> getLogDisplays() {
        return this.logdisplays;
    }

    public void addObservers(Observable obj) {
        for (Display display : getDisplays()) {
            obj.register(display);
        }
        for (Display display : getLogDisplays()) {
            obj.register(display);
        }
    }

    //Connections related
    public ArrayList<Log> getLogs() {
        return this.logs;
    }

    public void addLog(String logcontent) {
        Log log = new Log(logcontent);
        logs.add(log);
        notifyLogs(log);
    }
    public void notifyLogs(Log log) {
        addObservers(log);
        log.notifyObservers();
    }

    public ArrayList<Train> getTrains() {
        return this.trains;
    }

    public ArrayList<WagonType> getWagonTypes() {
        return this.wagontypes;
    }

    public ArrayList<Wagon> getWagons() {
        ArrayList<Wagon> wagons = new ArrayList<Wagon>();
        for (WagonType wt : this.getWagonTypes()) {
            for (Wagon w : wt.getWagons()) {
                wagons.add(w);
            }
        }
        return wagons;
    }

    //Actions
    //Wagons
    public void getWagonSeats(String w) {
        if (wagonTypeExists(w)) {
            WagonType wagon = wagonTypeFind(w);
            addLog("wagontype " + w + " has " + wagon.getSeats() + " seats");
        } else {
            addLog("wagontype " + w + " does not exist");
        }
    }

    public void addWagon(String t, String w) {
        if (trainExists(t)) {
            Train train = trainFind(t);
            if (wagonTypeExists(w)) {
                addLog("Wagon " + w + " added to train " + train.getName());
                WagonType wt = wagonTypeFind(w);
                Wagon wagon = wt.new_wagon();
                train.add_wagon(wagon);
            } else {
                addLog("Wagon " + w + " does not exist");
            }
        } else {
            addLog("Train " + t + " does not exist");
        }
    }

    public void removeWagon(String t, String w) {
        if (trainExists(t)) {
            Train train = trainFind(t);
            if (train.removeWagon(w)) {
                addLog("Wagon " + w + " is removed from train " + t);
            } else {
                addLog("Wagon " + w + " is not attached to train " + t);
            }
        } else {
            addLog("Train " + t + " does not exist");
        }
    }

    public void newWagonType(String id, int seats) {
        if (!wagonTypeExists(id)) {
            WagonType wt = new WagonType(id, seats);
            wagontypes.add(wt);
            addObservers(wt);
            addLog("wagontype " + id + " created with " + wt.getSeats() + " seats");
            wt.notifyObservers();
        } else {
            addLog("wagontype " + id + " already exists");
        }
    }

    public void newWagonType(String id) {
        if (!wagonTypeExists(id)) {
            WagonType wagontype = new WagonType(id);
            wagontypes.add(wagontype);
            addObservers(wagontype);
            addLog("wagontype " + id + " created with " + wagontype.getSeats() + " seats");
            wagontype.notifyObservers();
        } else {
            addLog("wagontype " + id + " already exists");
        }
    }

    public void deleteWagon(String w) {
        if (wagonTypeExists(w)) {
            WagonType wagontype = wagonTypeFind(w);
            wagontypes.remove(wagontype);
            wagontype.notifyObservers();
            for (Train t : trains) {
                deleteWagonsFromTrain(t, wagontype.getName());
            }
            addLog("Wagontype " + w + " deleted");
        } else {
            addLog("Wagontype " + w + " does not exist");
        }
    }

    public void deleteWagonsFromTrain(Train t, String w) {
        for (int i = t.wagons.size(); i == t.wagons.size() && i > 0; i--) {
            if (t.removeWagon(w));
        }
    }

    private boolean wagonTypeExists(String name) {
        if (!wagontypes.isEmpty()) {
            WagonType wagontype = wagonTypeFind(name);
            if (wagontype != null) {
                return true;
            }
        }
        return false;
    }

    private WagonType wagonTypeFind(String name) {
        WagonType wagontype = null;
        if (!wagontypes.isEmpty()) {
            for (WagonType w : wagontypes) {
                if (w.getName().equals(name)) {
                    return w;
                }
            }
        }
        return wagontype;
    }

    //Trains
    public void getTrainSeats(String name) {
        if (trainExists(name)) {
            Train train = trainFind(name);
            int seats = 0;
            for (Wagon w : train.wagons) {
                seats += w.getWagon_type().getSeats();
            }
            addLog("Number of seats in train " + name + ": " + seats);
        } else {
            addLog("train " + name + " does not exists");
        }
    }

    public void newTrain(String name) {
        if (!trainExists(name)) {
            Train train = new Train(name);
            addLog("train " + train.getName() + " created");
            addObservers(train);
            trains.add(train);
            train.notifyObservers();
        } else {
            addLog("train " + name + " already exists");
        }
    }

    public void deleteTrain(String name) {
        if (trainExists(name)) {
            Train train = trainFind(name);
            trains.remove(train);
            addLog("train " + name + " deleted");
            train.notifyObservers();
        } else {
            addLog("train " + name + " does not exists");
        }
    }

    private boolean trainExists(String name) {
        if (!trains.isEmpty()) {
            Train t = trainFind(name);
            if (t != null) {
                return true;
            }
        }
        return false;
    }

    private Train trainFind(String name) {
        Train train = null;
        if (!trains.isEmpty()) {
            for (Train t : trains) {
                if (t.getName().equals(name)) {
                    return t;
                }
            }
        }
        return train;
    }
}
