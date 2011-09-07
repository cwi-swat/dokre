package richrail.view;

import java.awt.Dimension;
import java.util.Observable;

import javax.swing.JTextArea;

import richrail.domain.ObserverableTrainDepot;

public class TextualDepotPanel extends AbstractDepotObserverPanel {

	private static final long serialVersionUID = 1L;
	
	JTextArea text;

	public TextualDepotPanel(Observable observable) {
		super(observable);
		
		this.text = new JTextArea();
		this.add(text);
		text.setEditable(false);
		text.setPreferredSize(new Dimension(350,200));
	}

	@Override
	public void update(ObserverableTrainDepot trainDepot) {
		text.setText(trainDepot.toString());
	}
}
