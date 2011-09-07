package richrail.command;

import richrail.tools.Log;

public class ClearLogCommand extends AbstractStringCommand {

	public ClearLogCommand() {
		super();
		this.setCommandFormat(CLEAR);
		this.setUndoable(false);
	}
	
	@Override
	protected void execute() {
		Log.clear();
	}

	@Override
	protected void undo() {}

}
