package assignment2.command;

import assignment2.TrainYard;
import assignment2.vehicle.Train;

public class NewTrainCommand implements Command
{
	public NewTrainCommand()
	{
	}

	public void execute(String inputCommand)
	{
		String[] result = inputCommand.split(" ");
		String id = result[2];
		
		TrainYard trainyard = TrainYard.getInstance(); 
		trainyard.newVehicle(id, new Train(id));
	}
}
