package richrail.application;

import java.util.Observable;
import richrail.commands.Command;

/**
 * Singleton use by the GUI to execute a command
 *
 */
public class RichRailController extends Observable {

	 private static RichRailController instance = null;

	 private RichRailController() {
	 }

	 /**
		*	Singleton
		*
		* @return
		*/
	 public static RichRailController getInstance() {
			if (instance == null) {
				 instance = new RichRailController();
			}
			return instance;
	 }

	 /**
		* Handles the given command.
		*
		* @param commandStr
		*/
	 public void interpret(String commandStr) {
			setChanged();
			Interpreter interpreter = new Interpreter(commandStr);
			Command command = interpreter.interpret();
			command.execute();
	 }

}
