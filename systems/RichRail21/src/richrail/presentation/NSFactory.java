package richrail.presentation;

import richrail.domain.Train;
import richrail.domain.Wagon;

/**
 * 
 */
public class NSFactory extends RollingStockUIFactory {

	/**
	 * 
	 * @param wagon
	 * @param train
	 * @return wagonUI
	 */
	@Override
	public WagonUI createWagon(Wagon wagon,Train train) {
		NSWagon wagonUI = new NSWagon();
		wagonUI.setUp(wagon, train);
		return wagonUI;
	}

	/**
	 * 
	 * @param train
	 * @return locomotiveUI
	 */
	@Override
	public LocomotiveUI createLocomotive(Train train) {
		NSLocomotive locomotiveUI = new NSLocomotive();
		locomotiveUI.setUp(train);
		return locomotiveUI;
	}
}