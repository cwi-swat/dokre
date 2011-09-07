package richrail.presentation;

import richrail.domain.Train;
import richrail.domain.Wagon;

public abstract class WagonUI extends RollingStockUI {

	 public Wagon wagon;
	 public Train train;

	 /**
		* Adds the model classes to this presentation object
		*
		* @param wagon
		* @param train
		*/
	 public void setUp(Wagon wagon, Train train) {
			this.wagon = wagon;
			this.train = train;
	 }
}
