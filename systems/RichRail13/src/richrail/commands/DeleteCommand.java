package richrail.commands;

import richrail.control.TrainDataProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCommand extends AbstractCommand{
	private static final String TRAIN_TYPE = "train";
	private static final String WAGON_TYPE = "wagon";
	private static final Pattern pattern = Pattern.compile("delete (" + TRAIN_TYPE + "|" + WAGON_TYPE + ") (" + ID + ")" );

	public DeleteCommand(TrainDataProvider provider) {
		super(provider);
	}

	@Override
	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public boolean executeCommand(Matcher matcher) {
		String type = matcher.group(1);
		String id = matcher.group(2);

		if(TRAIN_TYPE.equals(type)) {
			return getProvider().deleteTrain(id);
		} else if(WAGON_TYPE.equals(type)) {
			return getProvider().deleteWagon(id);
		} else {
			return false;
		}

	}
}
