package helpers;

import models.Storage;


public class Command {
    public void parse_command(String commands) {
    	if(commands.isEmpty()){
    		Storage.get().addLog("Empty Command");
    	}else{
	        ObjectCommand command = null;
	        if(commands.lastIndexOf(';')==-1){
	        	Storage.get().addLog("Command wasn't closed properly with ';'");
	        }else{
	        	String[] firstcmds = commands.split(";");
		        String[] cmds = firstcmds[0].split(" ");
		        if ((cmds.length < 2) == false) {
		            if (cmds[1].equalsIgnoreCase("train")) {
		                command = new TrainCommand();
		            } else if (cmds[0].equalsIgnoreCase("add") || cmds[0].equalsIgnoreCase("remove") || cmds[1].equalsIgnoreCase("wagon")) {
		                command = new WagonCommand();
		            }
		        }
		        if (command != null) {
		            if (!command.decode(cmds)) {
		            	Storage.get().addLog("Command unknown (" + commands + ")");
		            }
		        } else {
		        	Storage.get().addLog("Command unknown (" + commands + ")");
		        }
	        }
    	}
    }
}
