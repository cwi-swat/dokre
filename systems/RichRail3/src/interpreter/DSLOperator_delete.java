package interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datahandler.DataHandler;
import domain.Train;
import domain.WagonType;

public class DSLOperator_delete extends DSLOperator {

	@Override
	public boolean parseInput(DataHandler dataHandle, String input) {
		// DSL code:
		// 'delete' type ID

		String type;
		String id;
		Train train;
		WagonType wagonType;

		String regexp = "^(?i)delete\\s(wagon|train)\\s([a-zA-Z0-9]{1,})"; //verwijder (?i) voor casesensitive
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(input);

		if(!matcher.find())
		{
			return false;
		}
		
		type = matcher.group(1); // (wagon|train)
		id = matcher.group(2); // ([a-zA-Z0-9]{1,})
		
		train = dataHandle.getTrainByName(id);
		wagonType = dataHandle.getWagonTypeByName(id);
		
		if (type.equals("train"))
		{
			
			if(train == null)
			{
				setMessage("Train '" + id + "' not found");
				return false;
			}
			dataHandle.removeTrain(train);
		}
		else if (type.equals("wagon"))
		{			
			if(wagonType == null)
			{
				setMessage("Wagon '" + id + "' not found");
				return false;
			}
			dataHandle.removeWagonType(wagonType);
		}
		else 
		{
			if(train == null){
				setMessage("Wagon '" + id + "' deleted");
			}
			else{
				setMessage("Train '" + id + "' deleted");
			}
		}		
		return true;
	}
}
