package richrail.commands;

import richrail.control.TrainDataProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewWagonCommand extends AbstractCommand{

	private static final Pattern newWagonPattern = Pattern.compile("new wagon ([a-z][a-z0-9]*?)( numseats ([0-9]+))?");
	private static final int DEFAULT_NUMBER_OF_SEATS = 20;

	public NewWagonCommand(TrainDataProvider provider) {
		super(provider);
	}

 	@Override
	public Pattern getPattern() {
		return newWagonPattern;
	}

	@Override
	public boolean executeCommand(Matcher matcher) {
		int numseats = DEFAULT_NUMBER_OF_SEATS;

		String value = matcher.group(3);
		if(value != null) {
			numseats = Integer.parseInt(value);
		}
		getProvider().addWagon(matcher.group(1), numseats);
		return true;
	}
}
