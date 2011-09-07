package assignment2;

import assignment2.log.Log;
import java.util.*;
import assignment2.vehicle.*;

public class TrainYard
{
	private LinkedHashMap<String, Vehicle> vehicles = new LinkedHashMap<String, Vehicle>();
	private Log log = Log.getInstance();
	private static TrainYard trainYardInstance = null;

	public TrainYard()
	{
	}

	/**
	 * Gets the only trainyard instance
	 * @return
	 */
	public static TrainYard getInstance()
	{
		if (trainYardInstance == null)
		{
			trainYardInstance = new TrainYard();
		}
		return trainYardInstance;
	}

	/**
	 * Creats a new vehicle
	 * @param id Vehicle id
	 * @param newVehicle The vehicle object
	 */
	public void newVehicle(String id, Vehicle newVehicle)
	{
		if (vehicles.containsKey(id))
		{
			log.addLogLine("this id already exists");
		}
		else
		{
			vehicles.put(id, newVehicle);
			String vehicleType = newVehicle.getType();

			if (vehicleType.equals("train"))
			{
				log.addLogLine(vehicleType + " " + newVehicle.getId() + " created");
			}
			else if (vehicleType.equals("wagon"))
			{
				log.addLogLine(vehicleType + " " + newVehicle.getId() + " created with " + newVehicle.getNumberOfSeats() + " seats");
			}
		}
	}

	/**
	 * Adds a vehicle to another vehicle
	 * @param addId Wagon id
	 * @param toId Train id
	 */
	public void addWagonToTrain(String addId, String toId)
	{
		if (idExists(addId) && idExists(toId))
		{
			try
			{
				Train t = (Train)vehicles.get(toId);
				try
				{
					t.addWagon(addId, (Wagon)vehicles.get(addId));
					log.addLogLine("wagon " + addId + " added to train " + toId);
				}
				catch(Exception e)
				{
					log.addLogLine(addId + "isn't a wagon");
				}
			}
			catch(Exception e)
			{
				log.addLogLine(toId + " isn't a train");
			}
		}
	}

	/**
	 * Deletes the vehicle completly
	 * @param id Vehicle id
	 * @param type Vehicle type
	 */
	public void deleteVehicle(String id, String type)
	{
		if (idExists(id))
		{
			Vehicle v = vehicles.get(id);
			if (v.getType().equals(type))
			{
				if (v.getType().equals("wagon"))
				{
					for (String key : vehicles.keySet())
					{
						try
						{
							Train t = (Train)vehicles.get(key);
							removeVehicle(id, key);
						}
						catch(Exception e){}
					}
				}

				vehicles.remove(id);
				log.addLogLine(type + " " + id + " deleted");
			}
			else
			{
				log.addLogLine(id + " not of type: " + type);
			}
		}
	}

	/**
	 * Get the number of seats in a train of wagon
	 * @param id Vehicle id
	 * @param type Vehicle type
	 */
	public void getNumberOfSeats(String id, String type)
	{
		if (idExists(id))
		{
			Vehicle v = vehicles.get(id);
			if (v.getType().equals(type))
			{
				log.addLogLine("number of seats in " + v.getType() + " " + id + ": " + v.getNumberOfSeats());
			}
			else
			{
				log.addLogLine(id + " not of type: " + type);
			}
		}
	}

	/**
	 * Removes the wagon from a train
	 * @param removeId The id of a wagon
	 * @param fromId The id of a train
	 */
	public void removeVehicle(String removeId, String fromId)
	{
		try
		{
			if (idExists(removeId) && idExists(fromId))
			{
				Train t = (Train)vehicles.get(fromId);
				try
				{
					Wagon w = (Wagon)vehicles.get(removeId);
					if (t.hasWagon(w))
					{
						t.removeWagon(w);
						log.addLogLine("wagon " + removeId + " removed from train " + fromId);
					}
				}
				catch(Exception en)
				{
					log.addLogLine(removeId + " isn't a wagon");
				}
			}
		}
		catch(Exception e)
		{
			log.addLogLine(fromId + " isn't a train");
		}
	}

	/**
	 * Get all known vehicles
	 * @return
	 */
	public LinkedHashMap<String, Vehicle> getVehicles()
	{
		return vehicles;
	}

	/**
	 * Checks if the vehicle id exists
	 * @param id The id of the vehicle
	 * @return true or false
	 */
	public boolean idExists(String id)
	{
		if (!vehicles.containsKey(id))
		{
			log.addLogLine(id + " does not exist");
			return false;
		}
		return true;
	}
}
