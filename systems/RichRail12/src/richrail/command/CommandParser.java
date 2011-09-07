package richrail.command;

import java.util.LinkedList;

import richrail.tools.Log;

public class CommandParser {
	
	private LinkedList<AbstractStringCommand> availableCommands;
	private LinkedList<AbstractStringCommand> usedCommands;	
	
	private String lastCommand;
	
	private static final int MAX_SAVED_COMMANDS = 50;
	
	private static final String
		UNDO 		= "undo",
		NO_COMMANDS = "no commands to undo",
		INCORRECT 	= "command is incorrect";

	public CommandParser(){
		this.availableCommands = new LinkedList<AbstractStringCommand>();
		this.usedCommands = new LinkedList<AbstractStringCommand>();
		this.lastCommand = "";
	}
	
	public boolean addAvailableCommand(AbstractStringCommand command) {
		return this.availableCommands.add(command);
	}
	
	public boolean parseCommand(String commandString) {
		lastCommand = commandString.trim();
		
		// Empty
		if(lastCommand.isEmpty()) 
		{
			return false;
		}
		// Undo
		else if(lastCommand.matches(UNDO)) {
			return this.undoLastCommand();
		}
		// Commands
		else 
		{
			for (AbstractStringCommand command : availableCommands) {
				if(command.executeOnMatch(lastCommand)){
					//Add the command to the used commands
					if(command.isUndoable()) {
						this.addUsedCommand(command.clone());
					}
					return true;
				}
			}
			// Log that the command was incorrect
			// if no command was executed
			Log.log(this,INCORRECT,Log.ERROR);
			return false;
		}		
	}
	
	/**
	 * Add a used command to the list of commands. If the maximum of commands
	 * is reached it removes the first command in the list.
	 * 
	 * @param command
	 * @return Returns <code>true</code> if the command was succesfully added
	 * to the used commands list.
	 */
	private boolean addUsedCommand(AbstractStringCommand command) {
		if(this.usedCommands.size() >= MAX_SAVED_COMMANDS) {
			this.usedCommands.poll();
		}
		return this.usedCommands.add(command);		
	}
	
	/**
	 * This function is used to undo the last command. This is not 
	 * implemented as a command itself because this would add the undo
	 * action. By calling undo after that it would undo the undo action
	 * rendering it useless.
	 * 
	 * @return
	 */
	private boolean undoLastCommand() {
		if(this.usedCommands.size() > 0) {
			this.usedCommands.pollLast().undo();
			return true;
		}
		Log.log(this,NO_COMMANDS,Log.OUTPUT);
		return false;
	}
	
	public String getLastCommandString() {
		return this.lastCommand;	
	}
}
