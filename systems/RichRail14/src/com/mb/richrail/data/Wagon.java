package com.mb.richrail.data;

public class Wagon {

    private WagonType wagonType;
    
    public Wagon(WagonType wagonType) {
        this.wagonType = wagonType;
    }
    
    public WagonType getType() {
        return wagonType;
    }
}
