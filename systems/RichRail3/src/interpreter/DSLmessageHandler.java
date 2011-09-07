package interpreter;
import java.util.ArrayList;

import datahandler.DataHandler;

public class DSLmessageHandler {
	private ArrayList<DSLOperator> operators;
	private DataHandler dataHandler;

	public DSLmessageHandler(DataHandler theHandle){
		dataHandler = theHandle;
		operators = new ArrayList<DSLOperator>();
	}

	public void parseInput(String theInput){
		boolean succes = false;
		String message = "";
		for(int i = 0; i < operators.size(); i++){
			operators.get(i).clearError();
			succes = operators.get(i).parseInput(dataHandler, theInput);
			message = operators.get(i).getMessage();
			if(succes){    			
				dataHandler.setMessage(message);
				return;
			}
			if(!succes && message.length() != 0){
				dataHandler.setMessage(message);
			}
		}
		if (message.length() == 0){
			dataHandler.setMessage("Invalid command");
		}
	}

	public void addOperator(DSLOperator operator) {
		this.operators.add(operator);
	}
}