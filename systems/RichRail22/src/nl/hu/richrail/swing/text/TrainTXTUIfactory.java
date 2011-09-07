package nl.hu.richrail.swing.text;


import nl.hu.richrail.swing.JTrainComponent;
import nl.hu.richrail.swing.RsPoolView;
import nl.hu.richrail.swing.JTrain;
import nl.hu.richrail.swing.JTrainSegment;
import nl.hu.richrail.swing.TrainUIFactory;
import nl.hu.richrail.trains.Car;
import nl.hu.richrail.trains.RollingStock;
import nl.hu.richrail.trains.Train;

public class TrainTXTUIfactory extends TrainUIFactory
{

	@Override
	public RsPoolView buildView() {
		// TODO Auto-generated method stub
		return new RsPoolTXTView();
	}
	@Override
	public JTrainComponent buildJTrainComponent(RollingStock rollingStock) {
		if(rollingStock ==null)
			throw new NullPointerException();
		if(rollingStock instanceof Train)
		{
			
			Train t =(Train) rollingStock;
			TrainTXT tgfx = new TrainTXT(t.getFullId());
			tgfx.add(new Engine(t.getEngine()));
			for(RollingStock rs : t.getRollingStockItems())
				tgfx.add(new Waggon(rs));
			return tgfx;
		}
		else if(rollingStock instanceof Car)
		{
			return new Waggon(rollingStock);
		}
		else if(rollingStock instanceof nl.hu.richrail.trains.Engine)
		{
			return (JTrainComponent) new Engine(rollingStock);
		}
		return null;
	}

}
