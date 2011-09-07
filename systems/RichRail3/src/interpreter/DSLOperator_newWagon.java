package interpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datahandler.DataHandler;
import domain.WagonType;

public class DSLOperator_newWagon extends DSLOperator {

	@Override
	public boolean parseInput(DataHandler dataHandle, String input) {
		// DSL code:
		// 'new' 'wagon' ID ('numseats' NUMBER)
		// 'new' 'wagon' ID

		String wagonID;
		int numberOfSeats = 20; // Every wagon has at least 20 seats
		boolean numseatsgiven = false;
		WagonType wagonType;	

		String beginofcommand = "^(?i)new\\swagon\\s([a-zA-Z0-9]+)";
		String numseats = "\\s\\(numseats (-?[0-9]+)\\)";
		
		// Does it start with new wagon?
		if(!input.matches(beginofcommand)){
			// This check is necessary or the numseats won't pass
			if (!input.matches(beginofcommand+numseats)){
				return false;
			}
			else{
				// The user used the numseats version
				numseatsgiven = true;
			}
		}
		
		// Compile the regexp, so we can retrieve the wagonid
		Pattern pattern = Pattern.compile(beginofcommand);
		Matcher matcher = pattern.matcher(input);
		
		// Start the hunt for the wagonid
		matcher.find();

		// Capture the wagon
		wagonID = matcher.group(1); // ([a-zA-Z0-9]{1,})
		
		// Do we have a number of seats?
		if(numseatsgiven){
			// Yes we have, so lets hunt the seats
			pattern = Pattern.compile(numseats);
			matcher = pattern.matcher(input);
			
			// Start the hunt
			matcher.find();
			
			try{
				// Try to convert it to the number religion
				numberOfSeats = Integer.parseInt(matcher.group(1));
				
				// Not enough seats found
				if(numberOfSeats <= 0){
					setMessage("Number of seats too low. (Should be 1 or higher)");
					return false;
				}
			}
			catch(NumberFormatException ex) // Should not happen because of regexp, but in the world of programming one never knows.
			{
				setMessage("Could not set the number of seats. Number invalid.");
				return false;
			}
		}
		
		wagonType = dataHandle.getWagonTypeByName(wagonID);

		if(wagonType != null)
		{
			setMessage("Wagon '" + wagonID + "' is already in use.");
			return false;
		}

		wagonType = new WagonType();
		wagonType.setName(wagonID);
		wagonType.setSeats(numberOfSeats);
		dataHandle.addWagonType(wagonType);
		setMessage("Wagon '" + wagonID + "' created");
		return true;
	}
}