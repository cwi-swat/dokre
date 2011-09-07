package richrail.commands;

import richrail.application.RichRailController;
import richrail.domain.RollingStockManager;
import richrail.exceptions.TrainNotFoundException;

/**
 * Remove commando beschreven voor de DSL
 */
public class RemCommand extends Command {

	 /**
	  * Identificatie
	  */
	 private String wagonID, trainID;

	 /**
	  * Initialiseren van het remove commando.
	  * @param wagonID Wagon identificatie
	  * @param trainID Trein identificatie
	  */
	 public RemCommand(String wagonID, String trainID) {
			this.wagonID = wagonID;
			this.trainID = trainID;
	 }

	 /**
	  * Haalt trein identificatie op.
	  * @return string
	  */
	 public String getTrainID() {
			return trainID;
	 }

	 /**
	  * Haalt wagon identificatie op.
	  * @return string
	  */
	 public String getWagonID() {
			return wagonID;
	 }

	 /**
	  * Het uitvoeren van de remove commando.
	  * @see richrail.commands.Command#execute()
	  */
	 @Override
	 protected void todo() {
			try {
				 RollingStockManager.getInstance().removeWagonFromTrain(this.wagonID, this.trainID);
			} catch (TrainNotFoundException tnfe) {
				 errorCommand = new ErrorCommand("Train not found");
			}
	 }
}
