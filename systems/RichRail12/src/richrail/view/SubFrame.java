package richrail.view;

import javax.swing.JFrame;

import richrail.domain.ObserverableTrainDepot;

public class SubFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public SubFrame(ObserverableTrainDepot trainDepot, AbstractDepotObserverPanel panel) {
		
		getContentPane().add(panel);
		panel.update(trainDepot);
		pack();
		setVisible(true);
	
	}

	public SubFrame(ObserverableTrainDepot trainDepot, LogObserverPanel panel) {
		
		getContentPane().add(panel);
		panel.update(trainDepot, null);
		pack();
		setVisible(true);
		
	}
	
	
	
}
