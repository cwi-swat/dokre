package richrail.presentation;

import java.awt.Color;
import java.awt.Graphics;

import richrail.domain.Locomotive;
import richrail.domain.Train;

public class OldSchoolLocomotive extends LocomotiveUI {

	 /**
		* Draw an oldschool locomotive
		* @param g
		* @param leftOffset
		* @param topOffset
		*/
	 @Override
	 public void display(Graphics g, int leftOffset, int topOffset) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(leftOffset, topOffset + 80, 80, 40); //Romp
			g.fillRect(leftOffset + 50, topOffset + 60, 30, 30); //Rookpijp
			g.drawRoundRect(leftOffset + 55, topOffset + 40, 20, 20, 20, 20); //Rook
			g.drawRoundRect(leftOffset + 55, topOffset, 40, 40, 40, 40); //Rook
			g.setColor(Color.BLACK);
			g.fillRoundRect(leftOffset + 5, topOffset + 120, 20, 20, 20, 20); //Wiel
			g.fillRoundRect(leftOffset + 50, topOffset + 120, 20, 20, 20, 20); //Wiel
			g.drawString(train.getID(), 10 + leftOffset, topOffset + 105); //ID
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
