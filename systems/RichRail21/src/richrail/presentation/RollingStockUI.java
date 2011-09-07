package richrail.presentation;

import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * 
 */
public abstract class RollingStockUI {

	 /**
	  *
	  *
	  * @param g
	  * @param leftOffset
	  * @param topOffset
	  */
	 public abstract void display(Graphics g, int leftOffset, int topOffset);

	 /**
	  *
	  * @param g
	  * @param leftOffset
	  */
	 public abstract void display(Graphics g, int leftOffset);

	 /**
	  *
	  * @param g
	  */
	 public abstract void display(Graphics g);
}
