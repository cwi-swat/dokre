package richrail.presentation;

import java.awt.Color;
import java.awt.Graphics;

import richrail.domain.Train;
import richrail.domain.Wagon;

public class OldSchoolWagon extends WagonUI {

	 /**
		*
		* @param g
		* @param leftOffset
		* @param topOffset
		*/
	 @Override
	 public void display(Graphics g, int leftOffset, int topOffset) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(leftOffset, 80 + topOffset, 80, 40);
			g.setColor(Color.BLACK);
			g.fillRoundRect(5 + leftOffset, 120 + topOffset, 20, 20, 20, 20);
			g.fillRoundRect(55 + leftOffset, 120 + topOffset, 20, 20, 20, 20);
			g.drawString(wagon.getID(), 10 + leftOffset, 105 + topOffset);
	 }

	 /**
		*
		* @param g
		* @param leftOffset
		*/
	 @Override
	 public void display(Graphics g, int leftOffset) {
			display(g, leftOffset, 0);
	 }

	 /**
	  *
	  * @param g
	  */
	 @Override
	 public void display(Graphics g) {
			display(g, 0, 0);
	 }
}
