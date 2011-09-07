package nl.hu.richrail.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import nl.hu.richrail.swing.JTrain;
import nl.hu.richrail.swing.JTrainComponent;
import nl.hu.richrail.swing.JTrainSegment;
import nl.hu.richrail.swing.TrainUIFactory;
import nl.hu.richrail.trains.PoolChangeEvent;
import nl.hu.richrail.trains.Train;

public class TrainController implements Observer, Controller
{
	
	public final JTrain view;
	private Observable model;
	private TrainUIFactory uiFactory;
	
	public TrainController(Train train, TrainUIFactory uiFactory)
	{
		model = (Observable) train;
		this.uiFactory =uiFactory;
		view = (JTrain) uiFactory.buildJTrainComponent(train);
		train.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {

		PoolChangeEvent event = (PoolChangeEvent)arg;	
		Train t = (Train)o;	
		
		switch (event.type) {
		case add:
			view.add((JComponent) uiFactory.buildJTrainComponent(event.rollingStock));
			break;
		case removed:
			view.remove(event.rollingStock.getId());
			break;
		}
		view.invalidate();
		view.validate();
		
	
	}

	@Override
	public void close() {
		model.deleteObserver(this);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
