package richrail.commands;

import richrail.control.TrainDataProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractCommand implements Command{

	static final String ID = "[a-z][0-9a-z]*?";
	private final TrainDataProvider provider;

	TrainDataProvider getProvider() {
		return provider;
	}

	AbstractCommand(TrainDataProvider provider) {
		this.provider = provider;
	}

	@Override
	public final boolean process(String dsl) {
		if(canProcess(dsl))	{
			Matcher matcher = getPattern().matcher(dsl);
			matcher.matches();
			executeCommand(matcher);
			return true;
		}

		return false;
	}

	@Override
	public boolean canProcess(String dsl) {
		return getPattern().matcher(dsl).matches();
	}

	protected abstract Pattern getPattern();
	protected abstract boolean executeCommand(Matcher matcher);
}
