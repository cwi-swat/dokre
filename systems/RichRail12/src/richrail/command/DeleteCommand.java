package richrail.command;

import richrail.domain.ObserverableTrainDepot;
import richrail.domain.TrainComposite;

public class DeleteCommand extends AbstractTrainDepotCommand {
	
	private TrainComposite deletedUnit;
	
	public DeleteCommand(ObserverableTrainDepot trainDepot) {
		super(trainDepot);
		this.setCommandFormat(DELETE+WHITESPACE+TYPE+WHITESPACE+ID);
	}

	@Override
	public void execute() {
		this.deletedUnit = this.getTrainDepot().delete(
				this.getCommandPieces()[1],//Type
				this.getCommandPieces()[2]);//Id
	}

	@Override
	protected void undo() {
		this.getTrainDepot().addUnit(this.deletedUnit);	
	}

}
