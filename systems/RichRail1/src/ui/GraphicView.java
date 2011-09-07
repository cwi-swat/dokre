package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import logic.Controller;

import domain.Train;
import domain.Wagon;

public class GraphicView extends View {
	ArrayList<Train> trains = new ArrayList<Train>();

	public GraphicView() {
		this.setLayout(new GridLayout(1,1));
		this.setBackground(Color.WHITE);
		this.setSize(500, 500);
		this.setVisible(true);
	}

	public void paint(Graphics g) throws ConcurrentModificationException{
		if (trains.size() > 0) {
			int x = 5;
			int y = 5;
			int width = 0;
			for (Train t : trains) {
				if (!t.getWagons().isEmpty()) {
					for (Wagon w : t.getWagons()) {
						w.draw(g, x, y);
						x = x + 120;
					}
					if (width < x) {
						width = x;
					}
				}
				y = y + 120;
				x = 5;
			}
			this.setSize((width + 20), (y + 20));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Controller controller) {
		this.trains = controller.getTrains();
		paint(this.getGraphics());
	}
}