package richrail.commands;

import richrail.domain.RollingStockManager;
import richrail.exceptions.TrainNotFoundException;
import richrail.exceptions.WagonInUseException;

/**
 * Delete train commando beschreven voor de DSL
 */
public class DelTrainCommand extends Command {

	 /**
	  * Trein identificatie
	  */
	 private String ID;

	 /**
	  * Initialiseren van delete train commando.
	  *
	  * @param ID
	  */
	 public DelTrainCommand(String ID) {
			this.ID = ID;
	 }

	 /**
	  * Haalt het ID op.
	  * @return
	  */
	 public String getID() {
			return ID;
	 }

	 /**
	  * Voor het uitvoeren van de delete train commando.
	  * @see richrail.commands.Command#execute()
	  */
	 @Override
	 protected void todo() {

			try {
				 RollingStockManager.getInstance().getTrain(this.ID);
				 RollingStockManager.getInstance().removeTrain(this.ID);

			} catch (TrainNotFoundException ex) {
				 errorCommand = new ErrorCommand("Train does not exist");
			}

	 }
}
