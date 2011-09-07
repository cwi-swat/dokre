package controller.command;

import java.util.HashSet;

import model.Depot;
import model.Train;
import model.Wagon;

public class Getcommand implements CommandInterface {

	private CommandResult cresult = new CommandResult();
	private Depot dm;

	public Getcommand(Depot dm) {
		this.dm = dm;
	}

	@Override
	public CommandResult execute(String[] cmd) throws Exception {
		if (cmd[1].equals("train")) {
			getTrainSeats(cmd);
		} else if (cmd[1].equals("wagon")) {
			getWagonSeats(cmd);
		} else {
			throw new Exception("unknown type given");
		}
		return cresult;
	}

	private void getTrainSeats(String[] cmd) throws Exception {
		System.out.println("Getcommand.getTrainSeats(" + cmd + ")");

		if (cmd[2].trim().equals("")) {
			throw new Exception("no train ID given!");
		} else {
			Train train = dm.getTrain(cmd[2]);
			if (train == null) {
				throw new Exception("train " + cmd[2] + "\" does not exist");
			} else {
				cresult.setMessage("number of seats in train \"" + cmd[2] + "\": " + train.getNumSeats());
			}
		}

	}

	private void getWagonSeats(String[] cmd) throws Exception {
		System.out.println("Getcommand.getWagonSeats(" + cmd + ")");

		if (cmd[2].trim().equals("")) {
			throw new Exception("no wagon ID given!");
		} else {
			boolean wagonFound = false;

			// First check in the Depot if the wagon is in here. This loop should be must smaller than the train loop
			HashSet<Wagon> wagons = dm.getWagons();
			if (wagons != null) {
				for (Wagon wagon : wagons) {
					if (wagon.getId().equals(cmd[2])) {
						cresult.setMessage("number of seats in wagon \"" + cmd[2] + "\": " + wagon.getNumSeats());
						wagonFound = true;
					}
				}
			}

			if (!wagonFound) {
				HashSet<Train> trains = dm.getTrains();
				if (trains != null) {
					for (Train train : trains) {
						Wagon wagon = train.getWagon(cmd[2]);
						if (wagon != null) {
							cresult.setMessage("number of seats in wagon \"" + cmd[2] + "\": " + wagon.getNumSeats());
							wagonFound = true;
						}
					}
				}
			}

			if (!wagonFound) {
				throw new Exception("wagon \"" + cmd[2] + "\" does not exist");
			}
		}
	}

}
