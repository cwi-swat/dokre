package helpers;

import models.Storage;

public class TrainCommand extends ObjectCommand {
    @Override
    public boolean decode(String[] commands){
    	if(commands[0].equalsIgnoreCase("new")){
    		if(commands.length==3){
    			Storage.get().newTrain(commands[2]);
    			return true;
    		}
    	}else if(commands[0].equalsIgnoreCase("delete")){
    		if(commands.length==3){
    			Storage.get().deleteTrain(commands[2]);
    			return true;
    		}
    	}else if(commands[0].equalsIgnoreCase("getnumseats")){
    		if(commands.length==3){
    			Storage.get().getTrainSeats(commands[2]);
    			return true;
    		}
    	}
    	return false;
    }
}
