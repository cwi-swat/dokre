package models;

import java.util.ArrayList;
import java.util.Iterator;

import views.Observer;

public class Train implements Observable {

    private String name;
    public ArrayList<Wagon> wagons = new ArrayList<Wagon>();
    private ArrayList<Observer> observers = new ArrayList<Observer>();

    public void add_wagon(Wagon w) {
        if (!wagons.contains(w)) {
            wagons.add(w);
            notifyObservers();
        } else {
        }
    }

    public boolean removeWagon(String w) {
        Wagon wagon = findWagon(w);
        if (wagon != null) {
            wagons.remove(wagon);
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    public Train(String name) {
        this.name = name;
    }

    public Number get_seats() {
        int seats = 0;
        for (Wagon w : wagons) {
            WagonType wt = w.getWagon_type();
            seats = seats + wt.getSeats();
        }
        notifyObservers();
        return seats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Wagon> getWagons() {
        return this.wagons;
    }

    public String toString() {
        return "(" + this.getName() + ")";
    }

    public Wagon findWagon(String name) {
        Wagon wagon = null;
        if (!wagons.isEmpty()) {
            for (Wagon w : wagons) {
                if (w.getWagon_type().getName().equals(name)) {
                    return w;
                }
            }
        }
        return wagon;
    }

    // Observable methods
    public void notifyObservers() {
        Iterator<Observer> i = observers.iterator();
        while(i.hasNext()) {
            Observer o = (Observer) i.next();
            o.refresh();
        }
    }

    public void register(Observer obs) {
        observers.add(obs);
        notifyObservers();
    }

    public void unRegister(Observer obs) {
        observers.remove(obs);
    }    
}
