package richrail.presentation;

import richrail.domain.Train;
import richrail.domain.Wagon;

public abstract class RollingStockUIFactory {

	 /**
		*
		* @param wagon
		* @param train
		* @return
		*/
	 public abstract WagonUI createWagon(Wagon wagon, Train train);

	/**
	 *
	 * @param train
	 * @return
	 */
	public abstract LocomotiveUI createLocomotive(Train train);
}