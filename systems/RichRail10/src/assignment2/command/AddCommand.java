package assignment2.command;

import assignment2.TrainYard;

public class AddCommand implements Command {

	public AddCommand()
	{
	}

	public void execute(String inputCommand)
	{
		String[] result = inputCommand.split(" ");
		String addId = result[1];
		String toId = result[3];

		TrainYard trainyard = TrainYard.getInstance();
		trainyard.addWagonToTrain(addId, toId);
	}

}
