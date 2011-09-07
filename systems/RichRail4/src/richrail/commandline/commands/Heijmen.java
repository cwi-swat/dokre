package richrail.commandline.commands;

import richrail.commandline.Command;
import richrail.commandline.Interpreter;
import richrail.commandline.RichRailCommand;
import richrail.domain.Depot;

public class Heijmen extends RichRailCommand {

	public Heijmen(Depot depot, Command command, Interpreter interpreter) {
		super(depot, command, interpreter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		interpreter.message("hoi");
		depot.createTrain("heijmen");
	}

}
