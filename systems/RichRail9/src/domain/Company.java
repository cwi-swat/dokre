package domain;

import java.util.ArrayList;

public class Company {

	/**
	 * Complete collectie van Parts met motor (locomotieven) en Parts zonder
	 * motor (wagons)
	 */
	private ArrayList<Part> parts;

	/**
	 * De treinen, dus de samenstellingen van Parts met motor en evt. Parts
	 * zonder motor
	 */
	private ArrayList<Train> trains;

	public Company() {
		parts = new ArrayList<Part>();
		trains = new ArrayList<Train>();
	}

	public void addPart(Part newPart) throws Exception {
		for (Part part : parts)
			if (part.getName().equals(newPart.getName()))
				throw new Exception("wagon already exists");
		parts.add(newPart);		
	}

	public void addTrain(Train newTrain) throws Exception {
		for (Train t : trains)
			if (t.getName().equals(newTrain.getName()))
				throw new Exception("train already exists");

		trains.add(newTrain);

	}

	public Train[] getTrains() {
		return (Train[]) trains.toArray(new Train[trains.size()]);
	}

	public Train getTrainByName(String name) {
		for (Train t : trains)
			if (t.getName().equals(name))
				return t;

		return null;
	}

	public void deleteTrain(Train t)
	{
		trains.remove(t);
	}
	
	/**
	 * Completely delete the train and all the parts that belong to the train.
	 * @param String name The name of the train.
	 * @param boolean keepParts When the parts of the train must be removed from the train first to prevent 
	 * them to be deleted too, set this to true. Otherwise all parts will be deleted.
	 * @throws Exception 
	 */
	public void deleteTrainByName(String name, boolean keepParts) throws Exception
	{
		Train train = getTrainByName(name);
		
		if(keepParts)
		{
			Object[] parts = train.getParts();
			
			for(Object o : parts)
			{
				Part p = (Part) o;
				p.detach();
			}
		}
		
		if(train != null) 
		{
			deleteTrain(train);
		} 
		else 
		{
			throw new Exception("train does not exist");
		}
	}
	
	/**
	 * Delete the wagon with the given name. If the wagon is assigned to a train, it will be removed. 
	 * @param String name The name of the wagon.
	 */
	public void deleteWagonByName(String name) throws Exception
	{
		for(Part p : parts)
		{
			if(p.getName().equals(name))
			{
				p.detach();
				parts.remove(p);
				return;
			}
		}
		
		throw new Exception("wagon " + name + " does not exist");
	}
	
	public Part getPartByName(String name) {
		for (Part p : parts)
			if (p.getName().equals(name))
				return p;

		return null;
	}

	private boolean hasTrains() {
		return !trains.isEmpty();
	}

	private boolean hasWagons() {
		for (Part p : parts)
			if (!p.isLocomotive())
				return true;

		return false;
	}

	public String toString() {
		String result = "";

		if (hasWagons()) {
			result += "wagons" + System.getProperty("line.separator");

			for (Part p : parts)
				if (!p.isLocomotive())
					result += p + " ";

			result += System.getProperty("line.separator");
		}

		if (hasTrains()) {
			result += "trains" + System.getProperty("line.separator");

			for (Train t : trains)
				result += t.toString() + System.getProperty("line.separator");
		}

		return result;
	}
}
