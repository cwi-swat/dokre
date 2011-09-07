package richrail.commandline.commands;

import richrail.commandline.Command;
import richrail.commandline.Interpreter;
import richrail.commandline.RichRailCommand;
import richrail.domain.Depot;

public class New extends RichRailCommand {
	
	public New(Depot depot, Command command, Interpreter interpreter) {
		super(depot, command, interpreter);	
	}

	public void execute() {
		
		if(command.getParamAt(0).equals("train") && command.getParamsLength() == 2){
			depot.createTrain(command.getParamAt(1));
		} else if(command.getParamAt(0).equals("wagon") && command.getParamsLength() == 2){
			depot.createWagon(command.getParamAt(1));
			
		} else if(command.getParamAt(0).equals("wagon") && command.getParamsLength() == 4){

			if(command.getParamAt(2).equals("numseats")) {
				depot.createWagon(command.getParamAt(1), Integer.parseInt(command.getParamAt(3)));
			} else {
				interpreter.message("please use: 'new <train|wagon> <name>' or 'new wagon <name> numseats <int>'");
			}
		} else {
			interpreter.message("please use: 'new <train|wagon> <name>' or 'new wagon <name> numseats <int>'");
		}
	}
}
