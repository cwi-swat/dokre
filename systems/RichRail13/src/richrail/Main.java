package richrail;

import richrail.control.TrainController;
import richrail.view.TrainView;

import javax.swing.*;

public final class Main {
	private Main() {
	}

	public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {

		TrainController control = new TrainController();
		new TrainView(control);

		control.addTrain("train1");
		control.addTrain("train2");
		control.addTrain("train3");

		control.addWagon("wagon1", 20);
		control.addWagon("wagon2", 30);

		control.addWagonToTrain("wagon1", "train1");
		control.addWagonToTrain("wagon1", "train1");
		control.addWagonToTrain("wagon1", "train1");

		control.addWagonToTrain("wagon1", "train2");
		control.addWagonToTrain("wagon1", "train3");

		control.addWagonToTrain("wagon2", "train1");
		control.addWagonToTrain("wagon2", "train2");



	}
}
