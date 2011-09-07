package richrail.control;

import java.util.List;

public interface Logger extends Observable{

	void addLogEntry(String entry);
    String getLastLog();
	List<String> getAllLogs();

}
