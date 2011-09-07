package richrail.commands;

import richrail.control.TrainDataProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveCommand extends AbstractCommand{
	private static final Pattern pattern = Pattern.compile("remove (" + ID + ") from (" + ID + ")");

	public RemoveCommand(TrainDataProvider provider) {
		super(provider);
	}

	@Override
	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public boolean executeCommand(Matcher matcher) {
		String idToBeRemoved = matcher.group(1);
		String idFrom = matcher.group(2);

		return getProvider().removeWagonFromTrain(idToBeRemoved, idFrom);

	}
}
