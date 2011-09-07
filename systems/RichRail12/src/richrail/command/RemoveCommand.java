package richrail.command;

import richrail.domain.ObserverableTrainDepot;

public class RemoveCommand extends AbstractTrainDepotCommand {
	
	public RemoveCommand(ObserverableTrainDepot trainDepot) {
		super(trainDepot);
		this.setCommandFormat(REMOVE+WHITESPACE+ID+WHITESPACE+FROM+WHITESPACE+ID);
	}

	@Override
	public void execute() {
		this.getTrainDepot().removeWagonFromTrain(
				this.getCommandPieces()[1],//WagonId
				this.getCommandPieces()[3]);//TrainId
	}

	@Override
	protected void undo() {
		this.getTrainDepot().addWagonToTrain(
				this.getCommandPieces()[1],//WagonId
				this.getCommandPieces()[3]);//TrainId		
	}
}
