package richrail.commands;

import richrail.control.TrainDataProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetCommand extends AbstractCommand{

	private static final Pattern pattern = Pattern.compile("getnumseats (train|wagon) (" + ID + ")");

	public GetCommand(TrainDataProvider provider) {
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


		if("train".equals(type)) {
			getProvider().getNumberOfSeatsOfTrain(id);
		} else if("wagon".equals(type)) {
			getProvider().getNumberOfSeatsOfWagon(id);
		} else {
			getProvider().getLogger().addLogEntry("Unknown type");
			return false;
		}
		return true;
	}
}
