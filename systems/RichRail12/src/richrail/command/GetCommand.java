package richrail.command;

import richrail.domain.ObserverableTrainDepot;

public class GetCommand extends AbstractTrainDepotCommand {
	
	public GetCommand(ObserverableTrainDepot trainDepot) {
		super(trainDepot);
		this.setCommandFormat(GET+NUMSEATS+WHITESPACE+TYPE+WHITESPACE+ID);
		this.setUndoable(false);
	}

	@Override
	public void execute() {
		this.getTrainDepot().getNumSeats(
				this.getCommandPieces()[1],//Type
				this.getCommandPieces()[2]);//Id
	}

	@Override
	protected void undo() {}

}
