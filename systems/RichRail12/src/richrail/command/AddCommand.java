package richrail.command;

import richrail.domain.ObserverableTrainDepot;

public class AddCommand extends AbstractTrainDepotCommand {
	
	public AddCommand(ObserverableTrainDepot trainDepot) {
		super(trainDepot);
		this.setCommandFormat(ADD+WHITESPACE+ID+WHITESPACE+TO+WHITESPACE+ID);
	}

	@Override
	public void execute() {
		this.getTrainDepot().addWagonToTrain(
				this.getCommandPieces()[1],//Type
				this.getCommandPieces()[3]);//Id
	}

	@Override
	protected void undo() {
		this.getTrainDepot().removeWagonFromTrain(
				this.getCommandPieces()[1],//Type
				this.getCommandPieces()[3]);//Id
		
	}

}
