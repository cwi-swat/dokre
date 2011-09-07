package richrail.commands;

import richrail.application.RichRailController;
import richrail.domain.RollingStock;
import richrail.domain.RollingStockManager;
import richrail.domain.Wagon;
import richrail.exceptions.WagonNotFoundException;

/**
 * 'getnumseats wagon' beschreven voor de DSL.
 */
public class WagonSeatCommand extends Command {

	 private String ID;
	 private int numberOfSeats = 0;

	 /**
	  * Initialiseert WagonSeatCommand.
	  * @param ID Wagon identificatie
	  */
	 public WagonSeatCommand(String ID) {
			this.ID = ID;
	 }

	 /**
	  * Haalt identificatie op
	  * @return string
	  */
	 public String getID() {
			return ID;
	 }

	 /**
	  * Haalt aantal zitplaatsen op
	  * @return int
	  */
	 public int getNumberOfSeats() {
			return numberOfSeats;
	 }

	 /**
	  * Het uitvoeren van de 'getnumseats wagon' commando.
	  * @see richrail.commands.Command#execute()
	  */
	 @Override
	 protected void todo() {
			try {
			Wagon wagon = RollingStockManager.getInstance().getWagon(ID);
			numberOfSeats = wagon.getNumberOfSeats();
			} catch (WagonNotFoundException wnfe) {
				 errorCommand = new ErrorCommand("Wagon not found");
			}
	 }
}
