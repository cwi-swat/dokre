package richrail.view;

import richrail.control.Observer;
import richrail.control.TrainDataProvider;
import richrail.domain.Train;


import javax.swing.*;
import java.awt.*;
import java.util.List;


class TrainPanel extends JPanel implements Observer {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 400;

	private final TrainDrawer drawer;
	private final TrainDataProvider provider;

	public TrainPanel(TrainDataProvider provider) {
		this.provider = provider;
		drawer = new TrainDrawer(provider);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void notifyChange() {
		List<Train> trains= provider.getTrains();
		setPreferredSize(new Dimension(WIDTH, TrainDrawer.SEATABLE_YOFFSET * trains.size()));
		repaint();
		invalidate();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawer.drawAllObjects(g);
	}

}
