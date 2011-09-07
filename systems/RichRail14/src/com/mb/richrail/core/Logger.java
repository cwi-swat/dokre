package com.mb.richrail.core;

import java.util.ArrayList;
import java.util.Observable;

public class Logger extends Observable {
    
    private ArrayList<String> messages;
    
    public Logger() {
        this.messages = new ArrayList<String>();
    }
    
    public void log(String message) {
        messages.add(message);
        setChanged();
        notifyObservers(message);
    }
    
    public ArrayList<String> getMessages() {
        return messages;
    }
}
