package controller.command;

import model.Depot;
import model.Train;
import model.Wagon;

public class Remcommand implements CommandInterface {

	private CommandResult cresult = new CommandResult();
	private Depot dm;

	public Remcommand(Depot dm) {
		this.dm = dm;
	}

	@Override
	public CommandResult execute(String[] cmd) throws Exception {
		removeIdfromId(cmd);
		return cresult;
	}

	private void removeIdfromId(String[] cmd) throws Exception {
		System.out.println("Remcommand.removeIdfromId(" + cmd + ")");

		if (cmd[1].trim().equals("")) {
			throw new Exception("no wagon ID given!");
		} else if (!cmd[2].trim().equals("from")) {
			throw new Exception("invallid command!");
		} else if (cmd[3].trim().equals("")) {
			throw new Exception("no train ID given!");
		} else {
			Train t = dm.getTrain(cmd[3]);

			if (t == null) {
				throw new Exception("train \"" + cmd[3] + "\" does not exist!");
			} else {
				Wagon w = t.getWagon(cmd[1]);

				if (w == null) {
					throw new Exception("wagon \"" + cmd[1] + "\" does not exists in train");
				} else {
					t.deleteWagon(w);
					dm.addWagon(w);

					cresult.setMessage("wagon \"" + cmd[1] + "\" removed from train \"" + cmd[3] + "\"");
				}
			}
		}
	}

}
