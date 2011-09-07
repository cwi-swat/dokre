package richrail.commands;

import richrail.domain.RollingStockManager;
import richrail.domain.Train;
import richrail.exceptions.TrainNotFoundException;

/**
 * 'getnumseats train' beschreven voor de DSL
 */
public class TrainSeatsCommand extends Command {

	 private String ID;
	 private int numberOfSeats;

	 /**
	  * Initialiseert getNumberOfSeatsWagonCommand.
	  * @param ID Trein identificatie
	  */
	 public TrainSeatsCommand(String ID) {
			this.ID = ID;
	 }

	 /**
	  * Haalt identificatie op.
	  * @return string
	  */
	 public String getID() {
			return ID;
	 }

	 /**
	  * Haalt aantal zitplaatsen op.
	  * @return int
	  */
	 public int getNumberOfSeats() {
			return numberOfSeats;
	 }

	 /**
	  * Het uitvoeren van de 'getnumseats train' commando.
	  * @see richrail.commands.Command#execute()
	  */
	 @Override
	 protected void todo() {
			try {
				 Train train = RollingStockManager.getInstance().getTrain(ID);
				 numberOfSeats = train.getNumberOfSeats();
			} catch (TrainNotFoundException tnfe) {
				 errorCommand = new ErrorCommand("Train not found");
			}
	 }
}
