package richrail.view;

import richrail.control.Observer;
import richrail.control.TrainDataProvider;
import richrail.domain.Seatable;
import richrail.domain.Train;
import richrail.domain.Wagon;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

class TextTrainView extends JTextArea implements Observer {

	private final TrainDataProvider provider;

	public TextTrainView(TrainDataProvider provider) {
		this.provider = provider;
	}

	public void notifyChange() {
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		reload();
	}

	private void reload() {
		StringBuilder builder = new StringBuilder();
		appendWagons(builder);
		appendTrains(builder);
		setText(builder.toString());
	}

	private void appendTrains(StringBuilder builder) {
		List<Train> trains = provider.getTrains();
		builder.append("\nTrains:");
		for (Train train : trains) {
			builder.append("\n(").append(train.getName()).append(')');
			for (Seatable seatable : train.getSeatables()) {

				if (seatable instanceof Wagon) {
					builder.append(String.format("-(%s)", seatable.getName()));
				} else {
					builder.append(String.format("-(Train:%s)", seatable.getName()));
				}
			}
		}
	}

	private void appendWagons(StringBuilder builder) {
		Set<Wagon> wagons = provider.getWagons();
		builder.append("Wagons:\n");
		for (Wagon wagon : wagons) {
			builder.append(String.format("(%s:%s)", wagon.getName(), wagon.getNumberOfSeats()));
		}
	}
}
