package command;

public class remove_command extends Command{

	public Boolean Action(){
		//Command example
		//new train tr1
		boolean found = true;
		
		System.out.println(tokens[2]);
		
		//Controlleer op token 1. Train of Wagon
		if(tokens[2].equals("from") || super.checkExpression("[a-z0-9]*", tokens[1]) || super.checkExpression("[a-z0-9]*", tokens[3])){
			model.removeCommand(tokens[1], tokens[3]);
		} else {
			super.setMessage("2: De parameters kloppen niet.");
			found = false;
		}
	
		return found;
	}
	
}
