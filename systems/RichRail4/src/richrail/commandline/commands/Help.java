package richrail.commandline.commands;

import richrail.commandline.Command;
import richrail.commandline.Interpreter;
import richrail.commandline.RichRailCommand;
import richrail.domain.Depot;

public class Help extends RichRailCommand{
	
	public Help(Depot depot, Command command, Interpreter interpreter) {
		super(depot, command, interpreter);
	}

	public void execute() {
		interpreter.message("Available commands:");
		interpreter.message("	new train <name>");
		interpreter.message("	new wagon <name>");
		interpreter.message("	new wagon <name> numseats <int>");
		interpreter.message("	add <wagonname> to <trainname>");
		interpreter.message("	getnumseats train <name>");
		interpreter.message("	getnumseats wagon <name>");
		interpreter.message("	delete train <name>");
		interpreter.message("	delete wagon <name>");
		interpreter.message("	remove <wagonname> from <trainname>");
	}
	
}
