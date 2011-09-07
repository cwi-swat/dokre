package richrail.commands;

import richrail.control.TrainDataProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCommand extends AbstractCommand{

	private static final Pattern addPattern = Pattern.compile("add (" + ID + ") to (" + ID + ")");

	public AddCommand(TrainDataProvider provider) {
		super(provider);
	}

	@Override
	public Pattern getPattern() {
		return addPattern;
	}

	@Override
	public boolean executeCommand(Matcher matcher) {
		return getProvider().addWagonToTrain(matcher.group(1), matcher.group(2));
	}
}
