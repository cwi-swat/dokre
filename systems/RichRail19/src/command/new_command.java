package command;

public class new_command extends Command {
	
	public Boolean Action(){
		//Command example
		//new train tr1
		boolean found = true;
		
		//System.out.println(tokens[2]);
		
		//Controlleer op token 1. Train of Wagon
		if(tokens[1].equals("train") || tokens[1].equals("wagon") || tokens[2].isEmpty()){
			//Controlleer of param (ID) goed is.
			//Boolean check_expr = super.checkExpression("[a-zA-Z0-9]*", tokens[2]);
			Boolean check_expr = true;
			
			if(check_expr.booleanValue() == true){
				//Welke methode action moeten uitvoeren.
				if(tokens[1].equals("train")){
					if(!model.checkTrainExist(tokens[2])){
						super.model.newCommand(tokens[1], tokens[2], "");
					} else {
						super.setMessage("New train allready exsists.");
						
						found = false;
					}
				} else {
					//Controleer of er mee gegeven is dat er numseat is.
					if(tokens.length > 3){
						//Controleer of de numseats en aantal
						if(tokens[3].equals("numseats") || tokens[4].isEmpty()){
							if(!model.checkWagonExist(tokens[2])){
								super.model.newCommand(tokens[1], tokens[2], tokens[4]);
							} else {
								super.setMessage("New wagon allready exsists.");
								found = false;
							}
						} 
					} else {
						//Er is geen numseats opgegeven, dus laat lekker leegg.
						super.model.newCommand(tokens[1], tokens[2], "");
					}
				}
			} else {
				super.setMessage("1: Parameters zijn niet correct ingevoerd.");
				found = false;
			}
		} else {
			super.setMessage("2: De parameters kloppen niet.");
			found = false;
		}
	
		return found;
	}	
}
