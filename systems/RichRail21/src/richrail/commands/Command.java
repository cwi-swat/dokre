package richrail.commands;

import richrail.application.RichRailController;

/**
 * Standaard implementatie voor een commando
 */
public abstract class Command {

	 /**
	  * Commando string
	  */
	 private String commandStr = "";
	 /**
	  * Error commando
	  */
	 protected ErrorCommand errorCommand = null;

	 /**
	  * This method executes the user writen todo() method and handles the
	  * errorCommand. If errorCommand is set.
	  */
	 public void execute() {
			todo();
			Command returnCommand;
			if (errorCommand == null) {
				 returnCommand = this;
			} else {
				 returnCommand = errorCommand;
				 returnCommand.setCommandString(this.commandStr);
			}
			RichRailController.getInstance().notifyObservers(returnCommand);
	 }

	 /**
	  * Het instellen van een commando string.
	  * @param commandStr Commando string
	  */
	 public void setCommandString(String commandStr) {
			this.commandStr = commandStr;
	 }

	 /**
	  * Het terug geven van de commando string.
	  * @return String commandStr
	  */
	 public String getCommandString() {
			return this.commandStr;
	 }

	 /**
	  * This method will be executed in the method execute()
	  */
	 protected abstract void todo();
}
