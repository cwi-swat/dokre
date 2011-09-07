package richrail.command;

import richrail.tools.Log;

public abstract class AbstractStringCommand implements Cloneable {
	
	/**
	 * this string will be passed on executeOnMatch
	 */
	private String commandString;
	
	/**
	 * This String is a regex of the supposed command format
	 */
	private String commandFormat;
	
	/**
	 * These are the pieces from the command string when they are
	 * split on <code>WHITESPACE</code>
	 */
	private String[] commandPieces;
	
	/**
	 * When this is set to true, the command will be added to the used 
	 * commands so it can be undone
	 */
	private boolean undoable;
	
	public static final String FORMAT_NOT_SET = "command format not set";
	
	public AbstractStringCommand() {
		this.undoable = true;
	}
	
	/**
	 * This function will call the execute function if the passed
	 * <code>String</code> matches the given format. It also sets the command pieces which
	 * are parts of the given <code>String</code> splitted by <code>WHITESPACE</code>
	 * 
	 * @param commandString A string
	 * @return <code>false</code> if the format is <code>null</code> or if the 
	 * String does not match the format. Else return <code>true</code>
 	 */
	public boolean executeOnMatch(String commandString) {
		// Remember the given command string
		this.commandString = commandString;
		
		// If no command format (regex) is set, log an error
		if(this.commandFormat==null) {
			Log.log(this,FORMAT_NOT_SET,Log.ERROR);
			return false;
		}
		// Else check if the command string matches the format
		else if(commandString.matches(this.commandFormat)) {
			// Save the pieces of the command string
			this.commandPieces = commandString.split(WHITESPACE);
			// Execute the command
			this.execute();
			return true;
		}
		return false;
	}

	/**
	 * This will be the specific execution for every command
	 */
	protected abstract void execute();
	
	/**
	 * This function will do the opposite of the execute function
	 * to undo that function
	 */
	protected abstract void undo();

	public void setCommandFormat(String commandFormat) {
		this.commandFormat = commandFormat;
	}
	
	public String getCommandString() {
		return commandString;
	}

	public String getCommandFormat() {
		return commandFormat;
	}

	public String[] getCommandPieces() {
		return commandPieces;
	}
	
	/**
	 * Make a clone of the object to store in the used commands
	 */
	public AbstractStringCommand clone() {
		try {
			return (AbstractStringCommand) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public void setUndoable(boolean undoable) {
		this.undoable = undoable;
	}

	public boolean isUndoable() {
		return undoable;
	}

	protected static final String 
		NEW 		= "new",
		DELETE		= "delete",
		REMOVE 		= "remove",
		ADD			= "add",
		TO			= "to",
		FROM		= "from",
		TYPE 		= "(train|wagon)",
		VIEW		= "view",
		VIEWTYPE    = "(textualdepot|log|imagetrain)",
		WHITESPACE 	= "(\t| |\r|\n|\u000C)+",
		ID 			= "[a-z][a-z|0-9]*",
		NUMBER		= "[0-9]+",
		NUMSEATS	= "numseats",
		GET 		= "get",
		CLEAR 		= "(clear|clr|cls)",
		EXIT		= "(exit|quit)",
		UNDO		= "undo";
	
}
