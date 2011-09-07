package control;

import java.util.ArrayList;
import java.util.Observable;

import model.*;

public class RichRailFacade extends Observable 
{
	private ArrayList<Train> trains = new ArrayList<Train>();
	private ArrayList<Wagon> wagons = new ArrayList<Wagon>();
	private CommandOutput commandOutput;
	//private TextView textViewObserver;
	
	
	public RichRailFacade(CommandOutput commandOutput)
	{
		this.commandOutput = commandOutput;
	//	TextView textViewObserver = new TextView();
	//	this.addObserver(textViewObserver);

	}
	
	
	
	private void newTrain(String id)
	{
		this.trains.add(new Train(id));
		setChanged();
		this.notifyObservers(new Train(id));
	}
	
	private Wagon newWagon(String id, int numSeats)
	{
		this.wagons.add(new Wagon(id, numSeats));
		setChanged();
		this.notifyObservers(new Wagon(id, numSeats));
		return this.wagons.get(this.wagons.size()-1);
	}
	
	private Wagon newWagon(String id)
	{
		  //System.out.println("Add wagon Line");
		this.wagons.add(new Wagon(id));
		setChanged();
		this.notifyObservers(new Wagon(""));
		return this.wagons.get(this.wagons.size()-1);
		
	}
	
	private Train searchTrain(String id)
	{
		for(Train train : this.trains)
		{
			if(train.getId().equals(id)) 
			{
				return train;
			}
		}
		
		return null;
	}
	
	private Wagon searchWagon(String id)
	{
		for(Wagon wagon : this.wagons)
		{
			if(wagon.getId().equals(id))
			{
				return wagon;
			}
		}
		
		return null;
	}
	
	private void addWagonToTrain(Wagon wagon, Train train)
	{
		train.addWagon(wagon); 
		setChanged();
		this.notifyObservers(new Train(""));
	}
	
	private void deleteTrain(Train train)
	{
		this.trains.remove(train);
		setChanged();
		this.notifyObservers(new Train(""));
	}
	
	private void deleteWagon(Wagon wagon)
	{
		this.wagons.remove(wagon);
		setChanged();
		this.notifyObservers(new Wagon(""));
	}
	
	private void removeWagonFromTrain(Wagon wagon, Train train)
	{
		train.removeWagon(wagon);
		setChanged();
		this.notifyObservers(new Train(""));
	}
	
	public ArrayList<Train> getTrains()
	{
		return this.trains;
	}
	
	public ArrayList<Wagon> getWagons()
	{
		return this.wagons;
	}
	
	public void executeCommand(String command)
	{
		String[] splittedCmd = command.split(" ");
		
		String output;
		
		//newcommand
		if(splittedCmd[0].equals("new"))
		{
			//check voor type argument
			if(splittedCmd.length < 2)
			{
				output = "no type specified";
			}
			//check voor id argument
			else if(splittedCmd.length < 3) 
			{
				output = "no id specified";
			}
			//train toevoegen
			else if(splittedCmd[1].equals("train"))
			{
				this.newTrain(splittedCmd[2]);
				output = "train "+splittedCmd[2]+" created";
			}
			//wagon toevoegen
			else if(splittedCmd[1].equals("wagon"))
			{
				try
				{
					Wagon newWagon;
					if(splittedCmd.length == 5)
					{
						newWagon = this.newWagon(splittedCmd[2], Integer.parseInt(splittedCmd[4]));
					}
					else
					{
						newWagon = this.newWagon(splittedCmd[2]);
					}
					output = "wagon "+splittedCmd[2]+" created with "+newWagon.getNumSeats()+" seats";
				}
				catch(NumberFormatException e)
				{
					output = "invalid seats amount specified";
				}
			}
			//onbekend type
			else
			{
				output = "invalid type '"+splittedCmd[1]+"'";
			}
		}
		//addcommand
		else if(splittedCmd[0].equals("add"))
		{
			//check voor aantal argumenten
			if(splittedCmd.length < 4)
			{
				output = "not enough arguments";
			}
			else
			{				
				Train searchedTrain = this.searchTrain(splittedCmd[3]);
				Wagon searchedWagon = this.searchWagon(splittedCmd[1]);
				
				if(searchedTrain == null) 
				{
					output = "train with id '"+splittedCmd[3]+"' does not exist";
				}
				else if(searchedWagon == null)
				{
					output = "wagon with id '"+splittedCmd[1]+"' does not exist";
				}
				else
				{
					this.addWagonToTrain(searchedWagon, searchedTrain);
					output = "wagon "+searchedWagon.getId()+" added to train "+searchedTrain.getId();
				}
			}
		}
		//getnumseats command
		else if(splittedCmd[0].equals("getnumseats"))
		{
			//aantal argumenten check
			if(splittedCmd.length < 3) 
			{
				output = "not enough arguments";
			}
			//aantal stoelen van trein
			else if(splittedCmd[1].equals("train"))
			{
				Train searchedTrain = this.searchTrain(splittedCmd[2]);
				if(searchedTrain == null) 
				{
					output = "train with id '"+splittedCmd[2]+"' does not exist";
				}
				else
				{
					output = "train "+searchedTrain.getId()+" has "+searchedTrain.getNumSeats()+" seats";
				}
			}
			//aantal stoelen van wagon
			else if(splittedCmd[1].equals("wagon"))
			{
				Wagon searchedWagon = this.searchWagon(splittedCmd[2]);
				if(searchedWagon == null)
				{
					output = "wagon with id '"+splittedCmd[2]+"' does not exist";
				}
				else
				{
					output = "wagon "+searchedWagon.getId()+" has "+searchedWagon.getNumSeats()+" seats";
				}
			}
			else 
			{
				output = "invalid type";
			}
		}
		//delete command
		else if(splittedCmd[0].equals("delete"))
		{
			//aantal argumenten check
			if(splittedCmd.length < 3)
			{
				output = "not enough arguments";
			}
			//delete train
			else if(splittedCmd[1].equals("train"))
			{
				Train searchedTrain = this.searchTrain(splittedCmd[2]);
				if(searchedTrain == null)
				{
					output = "train with id '"+splittedCmd[2]+"' does not exist";
				}
				else
				{
					this.deleteTrain(searchedTrain);
					output = "train "+searchedTrain.getId()+" deleted";
				}
			}
			//delete wagon
			else if(splittedCmd[1].equals("wagon"))
			{
				Wagon searchedWagon = this.searchWagon(splittedCmd[2]);
				if(searchedWagon == null)
				{
					output = "wagon with id '"+splittedCmd[2]+"' does not exist";
				}
				else
				{
					this.deleteWagon(searchedWagon);
					output = "wagon "+searchedWagon.getId()+" deleted";
				}
			}
			else
			{
				output = "invalid type";
			}
		}
		//remove command
		else if(splittedCmd[0].equals("remove"))
		{
			if(splittedCmd.length < 4)
			{
				output = "not enough arguments";
			}
			else 
			{
				Train searchedTrain = this.searchTrain(splittedCmd[3]);
				if(searchedTrain == null)
				{
					output = "train with id '"+splittedCmd[3]+"' does not exist";
				}
				else
				{
					Wagon searchedWagon = searchedTrain.searchWagon(splittedCmd[1]);
					if(searchedWagon == null)
					{
						output = "wagon with id '"+splittedCmd[1]+"' does not exist in train "+searchedTrain.getId();
					}
					else
					{
						this.removeWagonFromTrain(searchedWagon, searchedTrain);
						output = "wagon "+searchedWagon.getId()+" removed from train "+searchedTrain.getId();
					}
				}
			}
		}
		else
		{
			output = "invalid action '"+splittedCmd[0]+"'";
		}
		
		this.commandOutput.addLine(output);
	}



	 
}
