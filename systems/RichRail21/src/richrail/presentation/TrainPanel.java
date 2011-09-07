package richrail.presentation;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

import richrail.domain.RollingStockManager;
import richrail.domain.Train;
import richrail.domain.Wagon;

public class TrainPanel extends JPanel implements Observer {

	 /**
		* 
		* @param o
		* @param arg
		*/
	 public void update(Observable o, Object arg) {
			paintComponent(getGraphics());

	 }

	 /**
		*
		* Displays the trains and the connected wagons
		* @param g
		*/
	 @Override
	 public void paintComponent(Graphics g) {
			super.paintComponent(g);//Removes the previous painted TrainUIParts
			OldSchoolFactory factory = OldSchoolFactory.getInstance();

			RollingStockManager pool = RollingStockManager.getInstance();
			Hashtable<String, Train> trains = pool.getTrains();
			Collection<Train> values = trains.values();
			Iterator<Train> trainIterator = values.iterator();

			int topOffset = 0;
			int leftOffset;

			while (trainIterator.hasNext()) {
				 leftOffset = 30;
				 Train train = trainIterator.next();
				 OldSchoolLocomotive locomotiveUI = (OldSchoolLocomotive) factory.createLocomotive(train);
				 locomotiveUI.display(g, leftOffset, topOffset);

				 Hashtable<String, Wagon> wagons = train.getWagons();
				 Collection<Wagon> wagonCollection = wagons.values();
				 Iterator<Wagon> wagonIterator = wagonCollection.iterator();

				 while (wagonIterator.hasNext()) {
						Wagon wagon = wagonIterator.next();
						leftOffset += 100;
						WagonUI wagonUI = factory.createWagon(wagon, train);
						wagonUI.display(g, leftOffset, topOffset);

				 }
				 topOffset += 120;
			}
	 }
}
