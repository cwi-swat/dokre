package richrail.presentation;

import richrail.domain.Train;

/**
 *
 */
public abstract class LocomotiveUI extends RollingStockUI {

	 /**
		*
		*/
	 protected Train train;

	 /**
	  *
	  * @param train
	  */
	 public void setUp(Train train) {
			this.train = train;
	 }
}
