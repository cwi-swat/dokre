package interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datahandler.DataHandler;
import domain.Train;

public class DSLOperator_removeWagon extends DSLOperator {

	@Override
	public boolean parseInput(DataHandler dataHandle, String input) {
		// DSL code:
		// 'remove' ID 'from' ID

		String wagonID, trainID;
		Train train;

		
		String regexp = "^(?i)remove\\s([a-zA-Z0-9]{1,})\\sfrom\\s([a-zA-Z0-9]{1,})";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(input);
		
		if(!matcher.find())
		{
			return false;
		}
		
		wagonID = matcher.group(1); // De 1e ([a-zA-Z0-9]{1,})
		trainID = matcher.group(2); // De 2e ([a-zA-Z0-9]{1,})
		train = dataHandle.getTrainByName(trainID);

		if(train == null){
			setMessage("The train is not found");
			return false;
		}

		for(int i = 0; i < train.getWagons().size(); i++){
			if(train.getWagons().get(i).getType().getName().equalsIgnoreCase(wagonID))
				train.getWagons().remove(i--);
		}
		dataHandle.updateTrain(train);
		setMessage("Wagon '" + wagonID + "' deleted");
		return true;
	}
}
