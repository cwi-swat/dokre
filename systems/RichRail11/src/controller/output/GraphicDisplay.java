package controller.output;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Depot;
import model.Train;
import model.Wagon;

public class GraphicDisplay extends JPanel implements Observer {

	private static final long serialVersionUID = 1973301688261315362L;
	private int currentTrain;
	private int currentNumberOfWagons;
	private int TRAINLENGTH = 100;
	private int offset = 160;
	private HashSet<Train> trains;
	private HashSet<Wagon> wagons;

	public GraphicDisplay() {
		super();
		setBackground(Color.WHITE);
		setPreferredSize(new java.awt.Dimension(784, 220));
		BorderLayout jPanel6Layout = new BorderLayout();
		setLayout(jPanel6Layout);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("GraphicDisplay.update(" + o + ", " + arg + ")");

		Object[] args = (Object[]) arg;
		if (args[0] instanceof Depot) {
			trains = ((Depot) args[0]).getTrains();
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		removeAll();

		if (trains != null) {
			currentTrain = 0;
			for (Train train : trains) {
				// Draw the train
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(30, 80 + currentTrain * offset, 80, 40);
				g.fillRect(80, 60 + currentTrain * offset, 30, 30);
				g.drawRoundRect(85, 40 + currentTrain * offset, 20, 20, 20, 20);
				g.drawRoundRect(85, currentTrain * offset, 40, 40, 40, 40);
				g.setColor(Color.BLACK);
				g.fillRoundRect(35, 120 + currentTrain * offset, 20, 20, 20, 20);
				g.fillRoundRect(80, 120 + currentTrain * offset, 20, 20, 20, 20);
				g.drawString(train.getId(), 40, 105 + currentTrain * offset);

				// Draw the wagons for this train
				wagons = train.getWagons();
				if (wagons != null) {
					currentNumberOfWagons = 1;
					for (Wagon wagon : wagons) {
						g.setColor(Color.LIGHT_GRAY);
						g.fillRect(30 + currentNumberOfWagons * TRAINLENGTH, 80 + currentTrain * offset, 80, 40);
						g.setColor(Color.BLACK);
						g.fillRoundRect(35 + currentNumberOfWagons * TRAINLENGTH, 120 + currentTrain * offset, 20, 20, 20, 20);
						g.fillRoundRect(80 + currentNumberOfWagons * TRAINLENGTH, 120 + currentTrain * offset, 20, 20, 20, 20);
						g.drawString(wagon.getId(), 40 + currentNumberOfWagons * TRAINLENGTH, 105 + currentTrain * offset);

						currentNumberOfWagons++;
					}
				}
				currentTrain++;
			}
		}
	}
}
