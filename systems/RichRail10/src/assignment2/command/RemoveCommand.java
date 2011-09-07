package assignment2.command;

import assignment2.TrainYard;

public class RemoveCommand implements Command {

	public RemoveCommand()
	{
	}

	public void execute(String inputCommand)
	{
		String[] result = inputCommand.split(" ");
		String removeId = result[1];
		String fromId = result[3];

		TrainYard trainyard = TrainYard.getInstance();
		trainyard.removeVehicle(removeId, fromId);
	}

}
