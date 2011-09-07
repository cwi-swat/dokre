package richrail.commands;

import richrail.domain.RollingStockManager;
import richrail.domain.Wagon;
import richrail.exceptions.InvalidIDException;
import richrail.exceptions.TrainNotFoundException;
import richrail.exceptions.WagonNotFoundException;

/**
 * Commando voor het aanmaken van een nieuwe wagon
 */
public class NewWagonCommand extends Command {

	 /**
	  * Wagon identificatie
	  */
	 private String wagonID;
	 /**
	  * Aantal zitplaatsen
	  */
	 private Integer seats = null;

	 /**
	  * Initialiseren van een wagon met standaard aantal stoelen.
	  * @param ID Wagon identificatie
	  */
	 public NewWagonCommand(String ID) {
			this.wagonID = ID;
	 }

	 /**
	  * Initialiseren van een wagon met een bepaald aantal stoelen.
	  * @param ID Wagon identificatie
	  * @param seats Aantal stoelen
	  */
	 public NewWagonCommand(String ID, int seats) {
			this.wagonID = ID;
			this.seats = seats;
	 }

	 /**
	  * Haalt aantal zitplaatsen op
	  * @return
	  */
	 public Integer getSeats() {
			return seats;
	 }

	 /**
	  * Haalt wagon identificatie op
	  * @return
	  */
	 public String getWagonID() {
			return wagonID;
	 }

	 /**
	  * Uitvoeren van de NewWagonCommand.
	  * @see richrail.commands.Command#execute()
	  */
	 @Override
	 protected void todo() {

			try {
				 if (this.seats == null) {
						RollingStockManager.getInstance().newWagon(this.wagonID);
				 } else {
						RollingStockManager.getInstance().newWagon(this.wagonID, this.seats);
				 }
			} catch (InvalidIDException iede) {
				 errorCommand = new ErrorCommand("Invalid ID");
			} catch (WagonNotFoundException wnfe) {
				 errorCommand = new ErrorCommand("Wagon already exists");
			}

	 }
}
