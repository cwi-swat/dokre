package richrail.commands;

import richrail.control.TrainDataProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewTrainCommand extends AbstractCommand {

	private static final Pattern newTrainPattern = Pattern.compile("new train ([a-z][a-z0-9]*)");

	public NewTrainCommand(TrainDataProvider provider) {
		super(provider);
	}

	@Override
	public Pattern getPattern() {
		return newTrainPattern;
	}

	@Override
	public boolean executeCommand(Matcher matcher) {
		getProvider().addTrain(matcher.group(1));
		return true;
	}
}
