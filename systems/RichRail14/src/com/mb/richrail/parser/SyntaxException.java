package com.mb.richrail.parser;

public class SyntaxException extends Exception {

    public SyntaxException(String message) {
        super(message);
    }
    
    public String getMessage() {
        return "Wrong Syntax! Useage: " + super.getMessage();
    }
}
