package richrail.presentation;

import richrail.domain.Train;
import richrail.domain.Wagon;

public class OldSchoolFactory extends RollingStockUIFactory {

	 private static OldSchoolFactory instance = null;

	 private OldSchoolFactory() {

	 }

	 /**
		* Singleton
		* @return
		*/
	 public static OldSchoolFactory getInstance() {
			if (instance == null) {
				 return new OldSchoolFactory();
			} else {
				 return instance;
			}
	 }

	 /**
	  * Creates a WagonUI object from a Wagon and Train object
	  * @param wagon
	  * @param train
	  * @return wagonUI
	  */
	 @Override
	 public WagonUI createWagon(Wagon wagon, Train train) {
			OldSchoolWagon wagonUI = new OldSchoolWagon();
			wagonUI.setUp(wagon, train);
			return wagonUI;
	 }

	 /**
	  * Creates a LocomotiveUI object from a Train object
	  * @param train
	  * @return locomotiveUI
	  */
	 @Override
	 public LocomotiveUI createLocomotive(Train train) {
			OldSchoolLocomotive locomotiveUI = new OldSchoolLocomotive();
			locomotiveUI.setUp(train);
			return locomotiveUI;
	 }
}
