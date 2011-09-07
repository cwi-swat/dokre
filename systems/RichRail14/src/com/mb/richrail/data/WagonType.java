package com.mb.richrail.data;

public class WagonType {

    private String name;
    private int seats;
    
    public WagonType(String name, int numOfSeats) {
        this.name = name;
        this.seats = numOfSeats;
    }
    
    public String getName() {
        return name;
    }
    
    public int getNumOfSeats() {
        return seats;
    }
}
