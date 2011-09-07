package richrail.commandline;

import richrail.domain.Depot;

public abstract class RichRailCommand {
	
	public Depot depot;
	public Command command;
	public Interpreter interpreter;
	
	protected RichRailCommand(Depot depot, Command command, Interpreter interpreter){
		this.depot = depot;
		this.command = command;
		this.interpreter = interpreter;
		execute();
	}
	
	public abstract void execute();
}
