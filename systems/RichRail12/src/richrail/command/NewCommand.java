package richrail.command;

import richrail.domain.ObserverableTrainDepot;

public class NewCommand extends AbstractTrainDepotCommand {
	
	public NewCommand(ObserverableTrainDepot trainDepot) {
		super(trainDepot);
		String possibleSeats = "("+WHITESPACE+NUMSEATS+WHITESPACE+NUMBER+")*";
		this.setCommandFormat(NEW+WHITESPACE+TYPE+WHITESPACE+ID+possibleSeats);
	}

	@Override
	public void execute() {
		// Without seats
		if(this.getCommandPieces().length <= 3) {
			this.getTrainDepot().newUnit(
					this.getCommandPieces()[1],//Type
					this.getCommandPieces()[2]);//Id
		// With seats
		} else {
			int number = Integer.parseInt(this.getCommandPieces()[4]);
			this.getTrainDepot().newUnit(
					this.getCommandPieces()[1],//Type
					this.getCommandPieces()[2],//Id
					number);//SEATS
		}
	}

	@Override
	protected void undo() {
		this.getTrainDepot().delete(
				this.getCommandPieces()[1],//Type
				this.getCommandPieces()[2]);//Id		
	}

}
