

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CommandOuput {
	
	private ArrayList<Train> trains;
	private final DataFrame view;
	private LogView lv;
	private TextView tv;
	
	public CommandOuput(DataFrame v, ArrayList<Train> Trains, LogView logv){
		trains = Trains;
		view = v;
		lv = logv;
		
		view.addExecutionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String input = "";
				try{
					input = view.getExecuteString();
				
					lv.showtext(initiateCommand(input));
//					tv.showHistory(new TrainsToText(trains));
				} catch (Exception e){
					System.out.println("whoops!");
				}
				
			}
			
		});
	}
	
	public String initiateCommand(String cmd){
		String response = "incorrect command!";
		String[] entries;
		entries = cmd.split(" ");
		if(entries.length >5){
			return response;
		}
		
		if(entries[0].equals("new"))
		{
			if(entries.length == 3){
				if(entries[1].equals("train")){
					if(newTrain(entries[2])){					
						response = "train " + entries[2] + " created";
					} else {
						response = "cannot create train";
					}
				}else if(entries[1].equals("wagon")){
					if(Train.newWagon(entries[2])){
						response = "wagon " + entries[2] + " created";
					}	else {
						response = "cannot create waggon";
					}
				} else {
					return response;
				}
			}
			else if(entries.length == 5){
				if(entries[3].equals("numseats")){
					String temp = entries[4];
					if(isParsableToInt(temp)){
						int numseats = Integer.parseInt(entries[4]);
						if(Train.newWagon(entries[2],numseats)){
							response = "wagon " + entries[2] + " created with " + entries[4] + " seats";
						} else {
							response = "cannot create wagon with seats";
						}
					} else {
						response = "please fill in a number after numseats";
					}
				} else {
					return response;
				}		
				
			} else {
				return response;
			}	
							
		} else if(entries[0].equals("add")){
			
			if(entries.length==4 && entries[2].equals("to")){
				if(addWagonToTrain(entries[1], entries[3])){
					
					response = "wagon " + entries[1] + " added to train " + entries[3];
				} else {
					return "cannot add wagon to train";
				}
			} else {
				return response;
			}
			
		} else if(entries[0].equals("getnumseats")){
			if(entries.length==3){
				if(entries[1].equals("train")){
					response = "number of seats in train " + entries[2] + ": " + Integer.toString(getTrainSeats(entries[2]));
				}else if(entries[1].equals("wagon")){
					response = "number of seats in wagon " + entries[2] + ": " + Integer.toString(getWagonSeats(entries[2]));
				} else {
					response = "cannot retrieve seats";
				}				
			} else{
				return response;
			}
			
		} else if(entries[0].equals("delete"))
		{
			if(entries.length==3)
			{
				if(entries[1].equals("train"))
				{
					if(DeleteTrain(entries[2]))
					{
						response = "train " + entries[2] + " deleted";
					} else 
					{
						response = "train " + entries[2] + " does not exist";
					}
				} 
				else if(entries[1].equals("wagon"))
				{
					if(DeleteWagon(entries[2]))
					{
						response = "wagon " + entries[2] + " deleted";
					} else 
					{
						response = "wagon " + entries[2] + " does not exist";
					}
				} 
				else
				{
					return response;
				}
			} else 
			{
				return response;
			}
		} else if(entries[0].equals("remove"))
		{
				if(entries.length==4 && entries[2].equals("from"))
				{
					if(removeWagonFromTrain(entries[1],entries[3]))
					{
						response = "wagon " + entries[1] + " removed from train " + entries[3];
					} else 
					{
						response = "cannot remove wagon " + entries[1] + " from train " + entries[3];
					}
				} 
				else 
				{
					return response;
				}
		}
		else 
		{
			return response;
		}
		return response;
	}
	
	public boolean isParsableToInt(String i)
	{
		try
		{
			Integer.parseInt(i);
			return true;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
	}
	public boolean newTrain(String name) 
	{
		if (!trains.contains(new Train(name)))
		{
			trains.add(new Train(name));
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean addWagonToTrain(String wagon, String trainName) 
	{
		Train t1 = findTrain(trainName);

		if (t1 != null)
		{
			Wagon w1 = Train.comeHereThomas().findWagon(wagon);

			if (w1 != null)
			{
				t1.addWagon(w1);
				return true;
			}
		}

		return false;
	}

	public Train findTrain(String name)
	{
		for (Train t : trains)
		{
			if (t.getTrainName().equals(name))
			{
				return t;
			}
		}

		return null;
	}

	public int getTrainSeats(String name) 
	{
		int seats = 0;
		Train t1 = findTrain(name);

		if (t1 != null)
		{
			for (Wagon w : t1.getWagons())
			{
				seats += w.getNumSeats();
			}
		}

		return seats;
	}

	public int getWagonSeats(String name) 
	{
		Wagon w1 = Train.comeHereThomas().findWagon(name);

		if (w1 != null)
		{
			return w1.getNumSeats();
		}

		return 0;
	}

	public boolean DeleteTrain(String name) 
	{
		Train t1 = findTrain(name);

		if (t1 != null)
		{
			trains.remove(t1);
			return true;
		}

		return false;
	}

	public boolean DeleteWagon(String name) 
	{
		Wagon w1 = Train.comeHereThomas().findWagon(name);

		if (w1 != null)
		{
			Train.comeHereThomas().getWagons().remove(w1);
			return true;
		}

		return false;
	}

	public boolean removeWagonFromTrain(String wagonName, String trainName) 
	{
		Train t1 = findTrain(trainName);

		if (t1 != null)
		{
			Wagon w1 = Train.comeHereThomas().findWagon(wagonName);

			if (w1 != null)
			{
				t1.removeWagon(w1);
				return true;
			}
		}

		return false;
	}
}
