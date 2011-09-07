package richrail.command;

import richrail.domain.ObserverableTrainDepot;

public abstract class AbstractTrainDepotCommand extends AbstractStringCommand {
	
	private ObserverableTrainDepot trainDepot;
	
	public AbstractTrainDepotCommand(ObserverableTrainDepot trainDepot) {
		super();
		this.trainDepot = trainDepot;
	}
	
	protected ObserverableTrainDepot getTrainDepot() {
		return this.trainDepot;
	}

	protected abstract void execute();
}
