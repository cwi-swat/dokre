package richrail.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListLogger implements Logger {

	private final List<Observer> observers = new ArrayList<Observer>();
	private final List<String> log = new ArrayList<String>();

	@Override
	public void addLogEntry(String entry) {
		log.add(entry);
		notifyObservers();
	}

	private void notifyObservers() {
		for(Observer observer : observers) {
			observer.notifyChange();
		}
	}

	@Override
	public String getLastLog() {
		int size = log.size();
		if (size > 0) {
			return log.get(log.size() - 1);
		} else {
			return "";
		}
	}

	@Override
	public List<String> getAllLogs() {
		return Collections.unmodifiableList(log);
	}

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

}
