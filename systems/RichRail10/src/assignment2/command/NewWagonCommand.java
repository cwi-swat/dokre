package assignment2.command;

import assignment2.TrainYard;
import assignment2.vehicle.PassengerWagon;

public class NewWagonCommand implements Command
{

	public NewWagonCommand()
	{
	}

	public void execute(String inputCommand)
	{
		String[] result = inputCommand.split(" ");
		TrainYard trainyard = TrainYard.getInstance();
		String id = "";
		int numseats = 0;

		if (result.length == 3)
		{
			id = result[2];
			trainyard.newVehicle(id, new PassengerWagon(id));
		}
		else if (result.length == 5)
		{
			id = result[2];
			numseats = Integer.parseInt(result[4]);
			trainyard.newVehicle(id, new PassengerWagon(id, numseats));
		}
	}
}
