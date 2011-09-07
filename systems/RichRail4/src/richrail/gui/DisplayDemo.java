package richrail.gui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import richrail.domain.Depot;

@SuppressWarnings("serial")
public class DisplayDemo extends JFrame{
	
	DisplayPanel displaypanel;
	
	public DisplayDemo(final Depot depot){
		
		setSize(795, 250);
		setTitle("RichRail DisplayDemo");
		displaypanel = new DisplayPanel();
		this.getContentPane().add(displaypanel);
		this.setVisible(true);
		
		depot.addObserver(new Observer() {
			
			@Override
			public void update(Observable o, Object arg) {
				displaypanel.updateTrains(depot.getTrains());
				
			}
		});
	}
}
