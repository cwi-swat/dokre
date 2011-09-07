package com.mb.richrail.data;

import java.util.ArrayList;

public class Train {
    
    private String name;
    private ArrayList<Wagon> wagons;
    
    public Train(String name) {
        this.name = name;
        this.wagons = new ArrayList<Wagon>();
    }
    
    public void addWagon(Wagon wagon) {
        wagons.add(wagon);
    }
    
    public boolean removeWagon(WagonType wagonType) {
        for (Wagon wagon : wagons) {
            if (wagon.getType().equals(wagonType)) {
                wagons.remove(wagon);
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }
    
    public int getNumOfSeats() {
        int i = 0;
        for (Wagon wagon : wagons) {
            i += wagon.getType().getNumOfSeats();
        }
        return i;
    }
    
    @Override
    public String toString() {
        String s = "(" + getName() + ")";
        for (Wagon wagon : wagons) {
            s += "-(" + wagon.getType().getName() + ")";
        }
        return s;
    }

    public ArrayList<Wagon> getWagons() {
        return wagons;
    }
}
