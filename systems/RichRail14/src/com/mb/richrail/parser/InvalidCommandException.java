package com.mb.richrail.parser;

public class InvalidCommandException extends Exception {
    
    public InvalidCommandException(String message) {
        super(message);
    }
    
    public String getMessage() {
        return super.getMessage() + " is not a valid command!";
    }
}
