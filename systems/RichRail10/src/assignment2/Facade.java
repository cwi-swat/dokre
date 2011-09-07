package assignment2;

import assignment2.log.Log;
import assignment2.vehicle.Vehicle;
import java.util.*;

public class Facade
{
	private TrainYard mTrainYard = TrainYard.getInstance();
	private Log mLog = Log.getInstance();

	public Facade()
	{
	}

	public ArrayList<String> getLog()
	{
		return mLog.getLogLines();
	}

	public LinkedHashMap<String, Vehicle> getVehicles()
	{
		return mTrainYard.getVehicles();
	}
}
