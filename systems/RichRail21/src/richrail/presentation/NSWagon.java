package richrail.presentation;

import java.awt.Graphics;

import richrail.domain.Train;
import richrail.domain.Wagon;

/**
 *
 */
public class NSWagon extends WagonUI {

	 /**
		*
		*/
	 @Override
	 public void display(Graphics g) {
			throw new UnsupportedOperationException("Not supported yet.");
	 }

	 /**
		*
		* @param wagon
		* @param train
		*/
	 @Override
	public void setUp(Wagon wagon, Train train) {
		this.wagon = wagon;
		this.train = train;
	}

	 @Override
	 public void display(Graphics g, int leftOffset, int topOffset) {
			throw new UnsupportedOperationException("Not supported yet.");
	 }

	 @Override
	 public void display(Graphics g, int leftOffset) {
			throw new UnsupportedOperationException("Not supported yet.");
	 }
}