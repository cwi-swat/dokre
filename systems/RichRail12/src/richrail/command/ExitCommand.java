package richrail.command;

import richrail.tools.Log;

public class ExitCommand extends AbstractStringCommand {
	
	public ExitCommand() {
		super();
		this.setCommandFormat(EXIT);
		this.setUndoable(false);
	}

	@Override
	protected void execute() {
		Log.log(this,"application stopped",Log.INFO);
		System.exit(0);
	}

	@Override
	protected void undo() {}

}
