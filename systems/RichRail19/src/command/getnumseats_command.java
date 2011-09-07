package command;

public class getnumseats_command extends Command {

	public Boolean Action(){
		boolean found = true;
		
		if(tokens[1].equals("train") || tokens[1].equals("wagon") || tokens[2].isEmpty()){
			super.model.getSeat(tokens[1], tokens[2]);
		} else {
			found = false;
			super.setMessage("Er is iets mis gegaan.");
			
		}
		
		return found;
	}
	
}
