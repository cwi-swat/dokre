package interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datahandler.DataHandler;
import domain.Train;
import domain.WagonType;

public class DSLOperator_getSeats extends DSLOperator {

	@Override
	public boolean parseInput(DataHandler dataHandle, String input) {
		// DSL code:
		// 'getnumseats' type ID

		String type;
		String id;
		Train train;
		WagonType theWagon = null;
		
		String regexp = "^(?i)getnumseats\\s(wagon|train)\\s([a-zA-Z0-9]{1,})"; //verwijder (?i) voor casesensitive
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(input);
		
		if(!matcher.find())
		{
			// command voldoet niet aan eisen
			return false;
		}
		
		type = matcher.group(1); // group(1) is de type: (wagon|train)
		id = matcher.group(2); // group(2) is id van wagon/trein: ([a-zA-Z0-9]{1,})
				
		if(type.equals("train"))
		{
			train = dataHandle.getTrainByName(id);
			if(train == null)
			{
				setMessage("Train '"+ id + "' not found");
				return false;
			}
			train.showNumSeats = true;
		}
		else if(type.equals("wagon"))
		{
			theWagon = dataHandle.getWagonTypeByName(id);
			if(theWagon == null)
			{
				setMessage("Wagon "+ id + " not found");
				return false;
			}
			theWagon.setShowNumSeats(true);
		}
		else 
		{
			setMessage("Type must be 'train' or 'wagon'");
			return false;
		}
		
		dataHandle.notifyOutputs();
		return true;
	}
}
