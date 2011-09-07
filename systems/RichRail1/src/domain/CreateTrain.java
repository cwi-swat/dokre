package domain;
import logic.Controller;

public class CreateTrain extends Command {
	
	private String trainName;

	@Override
	public void execute(Controller controller) {
		NormalTrain train = new NormalTrain(trainName);
		Locomotive locomotive = new Locomotive("Locomotive " + trainName);
		controller.addLogcommand("train " + trainName + " created");
		train.addWagon(locomotive);
		controller.addWagon(locomotive);
		controller.addTrain(train);
	}

	public void setTrain(String train) {
		this.trainName = train;
	}

	public String getTrain() {
		return trainName;
	}

}
