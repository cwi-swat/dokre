package assignment2.log;

import java.util.ArrayList;
import java.util.Observable;

public class Log extends Observable
{

	private ArrayList<String> logLines = new ArrayList<String>();
	private static Log logInstance = null;

	private Log()
	{
	}

	public static Log getInstance()
	{
		if (logInstance == null)
		{
			logInstance = new Log();
		}
		return logInstance;
	}

	public void addLogLine(String newEntry)
	{
		logLines.add(newEntry);
		setChanged();
		notifyObservers();
	}

	public void addLogLine(StringBuilder sb)
	{
		logLines.add(sb.toString());
		setChanged();
		notifyObservers();
	}

	public synchronized ArrayList<String> getLogLines()
	{
		return logLines;
	}
}
