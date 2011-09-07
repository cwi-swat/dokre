package domain;

import logic.Controller;

public class DeleteTrain extends Command {

	private String trainName;

	@Override
	public void execute(Controller controller) {
		for (Train train : controller.getTrains()) {
			if (train.getName().equals(trainName)) {
				controller.addLogcommand("train " + trainName + " deleted");
				controller.removeTrain(train);
				controller.removeWagon(train.getWagons().get(0));
				break;
			}
			else if (controller.getTrains().indexOf(train) == (controller.getTrains().size() - 1)) {
				controller.addLogcommand("train " + trainName + " does not exist");
				controller.update();
			}
		}
	}

	public void setTrain(String train) {
		this.trainName = train;
	}

	public String getTrain() {
		return trainName;
	}

}
