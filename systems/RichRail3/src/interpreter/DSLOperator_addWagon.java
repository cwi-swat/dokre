package interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datahandler.DataHandler;
import domain.Train;
import domain.Wagon;
import domain.WagonType;

public class DSLOperator_addWagon extends DSLOperator {

	@Override
	public boolean parseInput(DataHandler dataHandle, String input) {
		// DSL code:
		// 'add' ID 'to' ID

		String wagonID, trainID;
		WagonType type;
		Train train;
		Wagon newWagon;

		String regexp = "^(?i)add\\s([a-zA-Z0-9]{1,})\\sto\\s([a-zA-Z0-9]{1,})"; //verwijder (?i) voor casesensitive
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(input);
		
		if(!matcher.find()) 
		{
			return false;
		}

		wagonID = matcher.group(1); // De 1e ([a-zA-Z0-9]{1,})
		trainID = matcher.group(2); // De 2e ([a-zA-Z0-9]{1,})
		type = dataHandle.getWagonTypeByName(wagonID);
		
		if(type == null){
			setMessage("Wagon '" + wagonID + "' not found");
			return false;
		}
		train = dataHandle.getTrainByName(trainID);
		if(train == null){
			setMessage("Train '" + trainID + "' not found");
			return false;
		}

		newWagon = new Wagon(type);
		train.addWagon(newWagon);
		dataHandle.updateTrain(train);
		setMessage("Wagon '" + wagonID + "' added to wagon '" + trainID + "'");
		return true;
	}
}