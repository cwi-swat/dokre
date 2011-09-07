package nl.hu.richrail.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import java.util.Hashtable;

import nl.hu.richrail.swing.JTrainComponent;
import nl.hu.richrail.swing.JTrainSegment;
import nl.hu.richrail.swing.RsPoolView;
import nl.hu.richrail.swing.JTrain;
import nl.hu.richrail.swing.TrainUIFactory;
import nl.hu.richrail.trains.Engine;
import nl.hu.richrail.trains.PoolChangeEvent;
import nl.hu.richrail.trains.RollingStock;
import nl.hu.richrail.trains.RollingStockPool;
import nl.hu.richrail.trains.Train;
import nl.hu.richrail.trains.TrainsBS;

public class TrainManagementController implements Controller
{

	public final RsPoolView view;
	private TrainUIFactory uiFactory;
	private RollingStockPool domain;
	private PoolListener poolListener = new PoolListener();
	
	private Hashtable<String, TrainController> trains = new Hashtable<String, TrainController>();
	
	public TrainManagementController(TrainUIFactory uiFactory)
	{
		this.uiFactory =uiFactory;
		this.view = uiFactory.buildView();
		domain = RollingStockPool.getInstance();
		domain.addObserver(poolListener);
		
		initGui();
		view.show();
	}
	
	private void initGui()
	{
		for(RollingStock rs : domain.getRollingStockItems())
		{
			view.add(uiFactory.buildJTrainComponent(rs));
		}
		
		for(Train rs : domain.getTrains())
		{
			TrainController tc = new TrainController(rs,uiFactory);
			trains.put(rs.getId(), tc);
			view.add((JTrainComponent)tc.view);
		}
	}
	

	@Override
	public void close() {
		domain.deleteObserver(poolListener);
		for(TrainController tc : trains.values())
			tc.close();
		view.dispose();
	}

	@Override
	public void show() {
		view.show();
		
	}
	
	class PoolListener implements Observer{

		@Override
		public void update(Observable o, Object arg) 
		{
			
			RollingStockPool rs = (RollingStockPool)o;
			PoolChangeEvent event = (PoolChangeEvent)arg;
			switch (event.type) {
			case add:
				JTrainComponent jt = uiFactory.buildJTrainComponent(event.rollingStock);
			
				if(event.rollingStock instanceof Train)
				{
					TrainController tc = new TrainController((Train) event.rollingStock,uiFactory);
					trains.put(jt.getID(),tc);
					jt = tc.view;
				}
				
				view.add(jt);
				break;
			case removed:
				if(event.rollingStock instanceof Train)
					trains.remove(event.rollingStock.getFullId()).close();
				
				view.remove(event.rollingStock.getFullId());
				break;
			}
	
		}
		
	}
}
