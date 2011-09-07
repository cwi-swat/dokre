
public class CommandReader {
	private RichRailController controller;
	
	public CommandReader(RichRailController c){
		controller = c;
	}
	
	public String execute(String cmd){
		String output = "command not correct";
		String[] command;
		command = cmd.split(" ");
		if(command.length >5){
			return output;
		}
		
		if(command[0].equals("new")){
			if(command.length == 3){
				if(command[1].equals("train")){
					if(controller.newTrain(command[2])){					
						output = "train " + command[2] + " created";
					} 
					else {
						output = "train already exists";
					}
				}
				else if(command[1].equals("wagon")){
					if(controller.newWagon(command[2])){
						output = "wagon " + command[2] + " created";
					}	
					else {
						output = "wagon already exists";
					}
				} 
				else {
					return output;
				}
			}
			else if(command.length == 5){
				if(command[3].equals("numseats")){
					String temp = command[4];
					if(isParsableToInt(temp)){
						int numseats = Integer.parseInt(command[4]);
						if(controller.newWagon(command[2],numseats)){
							output = "wagon " + command[2] + " created with " + command[4] + " seats";
						}
						else {
							output = "cannot create wagon";
						}
					} 
					else {
						output = "command not correct";
					}
				} 
				else {
					return output;
				}		
			} 
			else {
				return output;
			}	
							
		} 
		else if(command[0].equals("add")){
			if(command.length==4 && command[2].equals("to")){				
				if(controller.addWagonToTrain(command[1], command[3])){
					output = "wagon " + command[1] + " added to train " + command[3];
				} 
				else {
					return "cannot add wagon to train";
				}
			} 
			else {
				return output;
			}
			
		} 
		else if(command[0].equals("getnumseats")){
			if(command.length==3){
				if(command[1].equals("train")){
					output = "number of seats in train " + command[2] + ": " + Integer.toString(controller.getTrainSeats(command[2]));
				}
				else if(command[1].equals("wagon")){
					output = "number of seats in wagon " + command[2] + ": " + Integer.toString(controller.getWagonSeats(command[2]));
				} 
				else {
					output = "cannot retrieve seats";
				}				
			} 
			else{
				return output;
			}
			
		} 
		else if(command[0].equals("delete")){
			if(command.length==3){
				if(command[1].equals("train")){
					if(controller.DeleteTrain(command[2])){
						output = "train " + command[2] + " deleted";
					} 
					else {
						output = "train " + command[2] + " does not exist";
					}
				} 
				else if(command[1].equals("wagon")){
					if(controller.DeleteWagon(command[2])){
						output = "wagon " + command[2] + " deleted";
					} 
					else {
						output = "wagon " + command[2] + " does not exist";
					}
				} 
				else{
					return output;
				}
			} 
			else {
				return output;
			}
		} 
		else if(command[0].equals("remove")){
				if(command.length==4 && command[2].equals("from")){
					if(controller.removeWagonFromTrain(command[1],command[3])){
						output = "wagon " + command[1] + " removed from train " + command[3];
					} 
					else {
						output = "cannot remove wagon " + command[1] + " from train " + command[3];
					}
				} 
				else {
					return output;
				}
		} 
		else {
			return output;
		}
		return output;
	}
	
	public boolean isParsableToInt(String s){
		try{
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException error){
			return false;
		}
	}
}
