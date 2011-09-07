package controller.command;

import java.util.HashSet;

import model.Depot;
import model.Train;
import model.Wagon;

public class Newcommand implements CommandInterface {

	private CommandResult cresult = new CommandResult();
	private Depot dm;

	public Newcommand(Depot dm) {
		this.dm = dm;
	}

	@Override
	public CommandResult execute(String[] cmd) throws Exception {
		if (cmd[1].equals("train")) {
			// New train
			newTrain(cmd);
		} else if (cmd[1].equals("wagon")) {
			newWagon(cmd);
		} else {
			throw new Exception("unknown type given");
		}
		return cresult;
	}

	private void newTrain(String[] cmd) throws Exception {
		System.out.println("Newcommand.newTrain(" + cmd + ")");
		if (cmd[2].trim().equals("")) {
			throw new Exception("no train ID given!");
		} else {
			if (dm.getTrain(cmd[2]) != null) {
				throw new Exception("train ID \"" + cmd[2] + "\" already exists");
			} else {
				Train train = new Train(cmd[2]);

				cresult.setMessage("train \"" + cmd[2] + "\" created");
				cresult.setObject(train);
			}
		}
	}

	private void newWagon(String[] cmd) throws Exception {
		// Check if there is an parameter for numseats
		if (cmd[2].trim().equals("")) {
			throw new Exception("no wagon ID given!");
		} else {
			if (dm.getWagon(cmd[2]) != null) {
				throw new Exception("wagon ID \"" + cmd[2] + "\" already exists");
			} else {
				HashSet<Train> trains = dm.getTrains();
				if (trains != null) {
					for (Train train : trains) {
						if (train.getWagon(cmd[2]) != null) {
							throw new Exception("wagon ID \"" + cmd[2] + "\" already exists");
						}
					}
				}

				if (!cmd[3].equals("numseats")) { // Create standard wagon with 20 seats
					Wagon wagon = new Wagon(cmd[2], 20);
					cresult.setMessage("wagon \"" + cmd[2] + "\" created with 20 seats");
					cresult.setObject(wagon);
				} else {
					if (cmd[3].equals("numseats") && !cmd[4].trim().equals("")) {
						Wagon wagon = new Wagon(cmd[2], Integer.parseInt(cmd[4]));
						cresult.setMessage("wagon \"" + cmd[2] + "\" created with " + cmd[4] + " seats");
						cresult.setObject(wagon);
					} else {
						throw new Exception("number of seats not given");
					}
				}
			}
		}
	}
}
