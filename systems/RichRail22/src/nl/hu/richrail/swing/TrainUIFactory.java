package nl.hu.richrail.swing;

import nl.hu.richrail.trains.RollingStock;

public abstract class TrainUIFactory 
{
	
	public abstract RsPoolView buildView();
	public abstract JTrainComponent buildJTrainComponent(RollingStock rollingStock);
}
