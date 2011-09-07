package assignment2.command;

import assignment2.TrainYard;

public class DeleteCommand implements Command {

	public DeleteCommand()
	{
	}

	public void execute(String inputCommand)
	{
		String[] result = inputCommand.split(" ");
		String id = result[2];
		String type = result[1];

		TrainYard trainyard = TrainYard.getInstance();
		trainyard.deleteVehicle(id, type);
	}

}
