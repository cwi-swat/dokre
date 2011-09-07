package richrail.presentation;

import java.awt.Graphics;
import richrail.domain.Train;

/**
 * 
 */
public class NSLocomotive extends LocomotiveUI {

	 /**
	  *
	  */
	 @Override
	 public void display(Graphics g) {
			throw new UnsupportedOperationException("Not supported yet.");
	 }

	 @Override
	 public void setUp(Train train) {
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
