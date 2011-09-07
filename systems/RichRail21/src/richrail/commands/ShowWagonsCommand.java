package richrail.commands;

import java.util.Hashtable;
import richrail.domain.RollingStockManager;
import richrail.domain.Wagon;

/**
 *
 */
public class ShowWagonsCommand extends Command {

	 private Hashtable<String, Wagon> wagons ;

	 /**
	  * Constructor
	  */
	 public ShowWagonsCommand() {
	 }

	 /**
		*
		* @return
		*/
	 public Hashtable<String, Wagon> getWagons() {
			return wagons;
	 }

	 /**
	  * Implementatie todo
	  */
	 @Override
	 protected void todo() {
			wagons = RollingStockManager.getInstance().getAvailableWagons();
	 }

}
