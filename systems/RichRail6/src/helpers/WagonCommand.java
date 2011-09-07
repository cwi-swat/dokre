package helpers;

import models.Storage;

public class WagonCommand extends ObjectCommand {
    public boolean decode(String[] commands){
    	if(commands[0].equalsIgnoreCase("new")){
            if(commands.length<4){
            	Storage.get().newWagonType(commands[2]);
            }else{
                Storage.get().newWagonType(commands[2],Integer.parseInt(commands[4]));
            }
    		return true;
    	}else if(commands[0].equalsIgnoreCase("add")){
    		if(commands.length==4){
    			Storage.get().addWagon(commands[3],commands[1]);
    			return true;
    		}
    	}else if(commands[0].equalsIgnoreCase("delete")){
    		if(commands.length==3){
    			Storage.get().deleteWagon(commands[2]);
    			return true;
    		}
    	}else if(commands[0].equalsIgnoreCase("remove")){
    		if(commands.length==4){
    			Storage.get().removeWagon(commands[3],commands[1]);
    			return true;
    		}
    	}else if(commands[0].equalsIgnoreCase("getnumseats")){
    		if(commands.length==3){
    			Storage.get().getWagonSeats(commands[2]);
    			return true;
    		}
    	}
    	return false;
    }
}
