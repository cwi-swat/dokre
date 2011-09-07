package richrail.view;

import richrail.control.TrainDataProvider;
import richrail.domain.Seatable;
import richrail.domain.Train;
import richrail.domain.Wagon;

import java.awt.*;
import java.util.List;

class TrainDrawer {

	public static final int SEATABLE_XOFFSET = 100;
	public static final int SEATABLE_YOFFSET = 140;

	private final TrainDataProvider provider;

	public TrainDrawer(TrainDataProvider provider) {
		this.provider = provider;
	}

	public void drawAllObjects(Graphics g) {
		List<Train> trains = provider.getTrains();
		int xOffset = 0;
		int yOffset = 0;

		for (Train train : trains) {
			drawTrain(g, train, xOffset, yOffset);
			for (Seatable seatable : train.getSeatables()) {
				xOffset += SEATABLE_XOFFSET;

				if (seatable instanceof Train) {
					drawTrain(g, seatable, xOffset, yOffset);
				} else if (seatable instanceof Wagon) {
					drawWagon(g, seatable, xOffset, yOffset);
				}
			}
			xOffset = 0;
			yOffset += SEATABLE_YOFFSET;
		}
	}

	private void drawTrain(Graphics g, Seatable seatable, int xOffset, int yOffset) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30 + xOffset, 80 + yOffset, 80, 40);
		g.fillRect(80 + xOffset, 60 + yOffset, 30, 30);
		g.drawRoundRect(85 + xOffset, 40 + yOffset, 20, 20, 20, 20);
		g.drawRoundRect(85 + xOffset, yOffset, 40, 40, 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35 + xOffset, 120 + yOffset, 20, 20, 20, 20);
		g.fillRoundRect(80 + xOffset, 120 + yOffset, 20, 20, 20, 20);
		g.drawString(seatable.getName(), 40 + xOffset, 105 + yOffset);
	}

	private void drawWagon(Graphics g, Seatable seatable, int xOffset, int yOffset) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30 + xOffset, 80 + yOffset, 80, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35 + xOffset, 120 + yOffset, 20, 20, 20, 20);
		g.fillRoundRect(80 + xOffset, 120 + yOffset, 20, 20, 20, 20);
		g.drawString(seatable.getName(), 40 + xOffset, 105 + yOffset);
	}


}
