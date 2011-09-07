package richrail.commands;

import richrail.domain.RollingStockManager;
import richrail.exceptions.TrainNotFoundException;
import richrail.exceptions.WagonInUseException;
import richrail.exceptions.WagonNotFoundException;

/**
 * Add commando beschreven voor de DSL. Voor het koppelen van wagons aan treinen
 */
public class AddCommand extends Command {

	/**
	 * Identificatie velden
	 */
	 private String wagonID, trainID;

	 /**
	  * Initialiseert de add commando om wagons aan treinen te koppelen.
	  * @param wagonID Wagon identificatie
	  * @param trainID Trein identificatie
	  */
	 public AddCommand(String wagonID, String trainID) {
			this.wagonID = wagonID;
			this.trainID = trainID;
	 }

	 /**
	  * Haalt trein identificatie op
	  * @return string
	  */
	 public String getTrainID() {
			return trainID;
	 }

	 /**
	  * Haalt wagon identificatie op
	  * @return string
	  */
	 public String getWagonID() {
			return wagonID;
	 }

	 /**
	  * Voert add command uit.
	  * @see richrail.commands.Command#execute()
	  */
	 @Override
	 protected void todo() {
			try {
				 RollingStockManager.getInstance().addWagonToTrain(this.wagonID, this.trainID);
			} catch (WagonNotFoundException ex) {
				 errorCommand = new ErrorCommand("Wagon not found");
			} catch (TrainNotFoundException ex) {
				 errorCommand = new ErrorCommand("Train does not exist");
			} catch (WagonInUseException ex) {
				 errorCommand = new ErrorCommand("Wagon in use");
			}
	 }
}
