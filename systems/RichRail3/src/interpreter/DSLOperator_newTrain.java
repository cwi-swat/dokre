package interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datahandler.DataHandler;
import domain.Train;

public class DSLOperator_newTrain extends DSLOperator {

	@Override
	public boolean parseInput(DataHandler dataHandle, String input) {
		// DSL code:
		// 'new' 'train' ID
		
		String trainID;
		Train train;

		String regexp = "^(?i)new\\strain\\s([a-zA-Z0-9]{1,})"; //verwijder (?i) voor casesensitive
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(input);

		if(!matcher.find())
		{
			return false;
		}

		trainID = matcher.group(1); // ([a-zA-Z0-9]{1,})
		train = dataHandle.getTrainByName(trainID);
		
		if(train != null) // train exists
		{
			setMessage("Train '" + trainID + "' already exists");
			return false;
		}
		train = new Train();
		train.setName(trainID);

		dataHandle.addTrain(train);
		setMessage("Train '" + trainID + "' created");
		return true;
	}
}
