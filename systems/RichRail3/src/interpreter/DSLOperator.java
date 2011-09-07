package interpreter;

import datahandler.DataHandler;

public abstract class DSLOperator {
	private String message;
	public void clearError(){ message = ""; }
	public String getMessage(){ return message; }
	protected void setMessage(String msg){ message = msg; }
    public abstract boolean parseInput(DataHandler dataHandle, String input);
}
