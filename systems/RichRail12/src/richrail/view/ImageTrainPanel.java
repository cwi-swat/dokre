package richrail.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observable;

import richrail.domain.ObserverableTrainDepot;
import richrail.domain.TrainComposite;

public class ImageTrainPanel extends AbstractDepotObserverPanel {
	private static final long serialVersionUID = 1L;
	private Image trainImage;
	private Image wagonImage;
	
	public ImageTrainPanel(Observable observable) {
		super(observable);
		
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(800,200));
		
		trainImage = Toolkit.getDefaultToolkit().getImage("img/train.png");
		wagonImage = Toolkit.getDefaultToolkit().getImage("img/wagon.png");
		
	}

	@Override
	public void update(ObserverableTrainDepot depot) {	
		removeAll();
		
		if(depot != null) {
			for(TrainComposite trainComposite : depot.getUnits()) {
				if(trainComposite.hasType("train"))
					add(new TrainCompositeImagePanel(trainImage, trainComposite.getId(), 70, 100));
				for(TrainComposite sTrainComposite : trainComposite.getUnits())
					add(new TrainCompositeImagePanel(wagonImage, sTrainComposite.getId(), 70, 100));
			}
		}
		revalidate();
	}

}
