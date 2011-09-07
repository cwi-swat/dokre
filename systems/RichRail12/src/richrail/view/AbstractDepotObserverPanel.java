package richrail.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import richrail.domain.ObserverableTrainDepot;

public abstract class AbstractDepotObserverPanel extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;

	public AbstractDepotObserverPanel(Observable observable){
		observable.addObserver(this);
	}
	
	public void update(Observable o, Object arg) {
		if(o instanceof ObserverableTrainDepot) {
			this.update((ObserverableTrainDepot) o);
			this.repaint();
		}
	}
	
	public abstract void update(ObserverableTrainDepot trainDepot);
}
