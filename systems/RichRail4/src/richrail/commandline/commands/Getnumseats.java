package richrail.commandline.commands;

import richrail.commandline.Command;
import richrail.commandline.Interpreter;
import richrail.commandline.RichRailCommand;
import richrail.domain.Depot;
import richrail.domain.Train;
import richrail.domain.Wagon;

public class Getnumseats extends RichRailCommand{
	
	public Getnumseats(Depot depot, Command command, Interpreter interpreter) {
		super(depot, command, interpreter);
	}

	public void execute() {
		if(command.getParamAt(0).equals("train")){
			
			Train tr = depot.getTrainByName(command.getParamAt(1));
			if(tr == null){
				interpreter.message("train does not exist");
				return;
			} else {
				interpreter.message("number of seats in train "+tr.getName()+":"+String.valueOf(tr.getSeats()));
			}

		} else if(command.getParamAt(0).equals("wagon")){
			
			Wagon wg = depot.getWagonByName(command.getParamAt(1));
			if(wg == null){
				interpreter.message("wagon does not exist");
				return;
			} else {
				interpreter.message("number of seats in wagon "+wg.getName()+":"+String.valueOf(wg.getSeats()));
			}
			
		} else {
			interpreter.message("please use: 'getnumseats <train|wagon> <name>'");
		}
	}
}
