package richrail.presentation;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextArea;
import richrail.commands.Command;

public class ExecutedCommandTextArea extends JTextArea implements Observer {

	 /**
		* Displays the executed command
		* @param o
		* @param arg
		*/
	 public void update(Observable o, Object arg) {
			if (arg instanceof Command) {
				 Command command = (Command) arg;
				 append(command.getCommandString());
			}
			append("\n");
	 }
}
