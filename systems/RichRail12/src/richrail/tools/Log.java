package richrail.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

public class Log extends Observable {
	
	private static Log instance;
	
	public final static String 
		OUTPUT 	= "OUTPUT",
		ERROR 	= "ERROR",
		INFO 	= "INFO",
		
		CLEARED = "output cleared";
	
	private static LinkedList<String> messages;
	private static final int MAX_MESSAGES = 12;
	
	private Log() {
		messages = new LinkedList<String>();
	}
	
	public static Log getInstance() {
		if(instance == null) {
			instance = new Log();
		}
		return instance;
	}
	
	public static void error(Object o, String message) {
		log(o, message, ERROR);
	}
	
	public static void output(Object o, String message) {
		log(o, message, OUTPUT);
	}
	
	public static void log(Object o, String message, String type) {
		String className = o.getClass().getSimpleName();
		
		String fullMessage = 
			getDateTime() + " [" + type.toUpperCase() + "] \t\t" + className + "\t" + message; 
		
		if(type.equals(ERROR)) {
			System.err.println(fullMessage);
		} else {
			System.out.println(fullMessage);
		}
		if(!type.equals(INFO)) {
			addMessage(message);
		}
		getInstance().notifyObservers();
	}
	
	private static boolean addMessage(String message) {
		if(messages.size() >= MAX_MESSAGES) {
			messages.poll();
		}
		getInstance().setChanged();
		return messages.add(message);
	}
	
	public String toString() {
		Iterator<String> strings = messages.listIterator();
		return this.implode(strings, "\n");
	}

	/**
	 * Clear the log. Removes all messages
	 */
	public static void clear() {
		if(messages.size() > 0) {
			messages = new LinkedList<String>();
			getInstance().setChanged();
			log(getInstance(),CLEARED, INFO);
		}
	}
	
	private String implode(Iterator<String> strings, String glue) {
		if (strings.hasNext() == false) {
			return "";
		}
		if (glue == null) {
			glue = "\n";
		}
		StringBuilder sb = new StringBuilder();
		while (strings.hasNext()) {
			sb.append(strings.next()).append(glue);
		}
		return sb.substring(0, sb.length() - glue.length());
	}
	
	/**
	 * @return A date <code>(HH:mm:ss)</code> to place in front of the log message.
	 */
	private static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
