package richrail.commandline.commands;

import richrail.commandline.Command;
import richrail.commandline.Interpreter;
import richrail.commandline.RichRailCommand;
import richrail.domain.Depot;
import richrail.domain.Train;
import richrail.domain.Wagon;

public class Delete extends RichRailCommand{
	
	public Delete(Depot depot, Command command, Interpreter interpreter) {
		super(depot, command, interpreter);
	}

	public void execute() {
		if(command.getParamAt(0).equals("train")){
			
			Train tr = depot.getTrainByName(command.getParamAt(1));
			if(tr == null){
				interpreter.message("train does not exist");
				return;
			} else {
				depot.removeTrain(tr);
			}
	
		} else if(command.getParamAt(0).equals("wagon")){
			
			Wagon wg = depot.getWagonByName(command.getParamAt(1));
			if(wg == null){
				interpreter.message("wagon does not exist");
				return;
			} else {
				depot.removeWagon(wg);
			}
			
		} else {
			interpreter.message("please use: 'delete <train|wagon> <name>'");
		}
	}
}
