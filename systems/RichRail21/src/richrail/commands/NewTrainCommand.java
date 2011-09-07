package richrail.commands;

import richrail.domain.RollingStockManager;
import richrail.exceptions.InvalidIDException;
import richrail.exceptions.TrainExistsException;
import richrail.exceptions.TrainNotFoundException;

/**
 * Commando voor het aanmaken van een nieuwe trein.
 */
public class NewTrainCommand extends Command {

	 /**
	  * Trein identificatie
	  */
	 private String ID;

	 /**
	  * Initialiseren van de NewTrainCommand.
	  * @param ID Trein identificatie.
	  */
	 public NewTrainCommand(String ID) {
			this.ID = ID;
	 }

	 /**
	  * Haalt identificatie op
	  * @return
	  */
	 public String getID() {
			return ID;
	 }

	 /**
	  * Het uitvoeren van de NewTrainCommand.
	  * @see richrail.commands.Command#execute()
	  */
	 @Override
	 protected void todo() {
			try {
				 RollingStockManager.getInstance().newTrain(ID);
			} catch (InvalidIDException iie) {
				 errorCommand = new ErrorCommand("Invalid ID");
			} catch (TrainExistsException tee) {
				 errorCommand = new ErrorCommand("Train already exists");
			}

	 }
}
