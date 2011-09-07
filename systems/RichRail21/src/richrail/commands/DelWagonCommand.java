package richrail.commands;

import richrail.domain.RollingStockManager;
import richrail.exceptions.WagonInUseException;
import richrail.exceptions.WagonNotFoundException;

/**
 * Delete train commando beschreven voor de DSL
 */
public class DelWagonCommand extends Command {

	 /**
	  * Wagon identificatie
	  */
	 private String ID;

	 /**
	  * Initialiseren van delete wagon commando.
	  *
	  * @param ID
	  */
	 public DelWagonCommand(String ID) {
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
	  * Voor het uitvoeren van de delete wagon commando.
	  * @see richrail.commands.Command#execute()
	  */
	 @Override
	 protected void todo() {

			try {
				 RollingStockManager.getInstance().getWagon(this.ID);
				 RollingStockManager.getInstance().removeWagon(this.ID);
			} catch (WagonNotFoundException ex) {
				 errorCommand = new ErrorCommand("Wagon not found");
			} catch (WagonInUseException ex) {
				 errorCommand = new ErrorCommand("Wagon in use");
			}
	 }
}
