package logging;

import java.util.ArrayList;
import java.util.Observable;

public class Logger extends Observable
{

	private static Logger instance = null;
	private ArrayList<String> logs;

	public static Logger getInstance()
	{
		if(instance == null)
		{
			instance = new Logger();
		}

		
		return instance;
	}

	private Logger()
	{
		logs = new ArrayList<String>();
	}

	public ArrayList<String> getLogs()
	{
		return logs;
	}

	public void log(String message)
	{
		logs.add(message);

		instance.setChanged();
		instance.notifyObservers();

	}

}
