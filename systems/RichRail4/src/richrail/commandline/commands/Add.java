package richrail.commandline.commands;

import richrail.commandline.Command;
import richrail.commandline.Interpreter;
import richrail.commandline.RichRailCommand;
import richrail.domain.Depot;
import richrail.domain.Train;
import richrail.domain.Wagon;

public class Add extends RichRailCommand{
	
	public Add(Depot depot, Command cmd, Interpreter interpreter) {
		super(depot, cmd, interpreter);	
	}

	@Override
	public void execute() {
		if(command.getParamsLength() == 3 && command.getParamAt(1).equals("to")){
			
			Wagon wg = depot.getWagonByName(command.getParamAt(0));
			Train tr = depot.getTrainByName(command.getParamAt(2));
			
			if(wg == null) interpreter.message("wagon does not exist");
			if(tr == null) interpreter.message("train does not exist");
			
			if(wg == null || tr == null) return;
			
			depot.addWagonToTrain(tr, wg);
			
		} else {
			interpreter.message("please use: 'add <wagonname> to <trainname>'");
		}
		
	}
	
	

}
