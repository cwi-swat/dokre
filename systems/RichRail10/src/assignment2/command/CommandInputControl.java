package assignment2.command;

import assignment2.log.Log;
import java.util.regex.*;

public class CommandInputControl
{
	private String mInputCommand;

	/** Bron
	 * http://java.sun.com/developer/technicalArticles/releases/1.4regex/
	 * http://www.mkyong.com/regular-expressions/10-java-regular-expression-examples-you-should-know/
	 */
	private final String ID = "([a-z])([a-z|0-9])*";
	private final String TYPE = "(train|wagon)";
	private final String WAGONTYPE = "(wagon|passenger)";
	private final String NUMSEATS = "(\\snumseats\\s(([1-9])([0-9]){0,3}))?";

	// New commands
	private final String NEWTRAIN_COMMAND = "^(new\\strain\\s"+ID+")$";
	private final String NEWWAGON_COMMAND = "^(new\\s"+WAGONTYPE+"\\s"+ID+NUMSEATS+")$";
	private final String NEW_COMMAND = "("+NEWTRAIN_COMMAND+"|"+NEWWAGON_COMMAND+")";

	// Add, Get, Del and Rem Command
	private final String ADD_COMMAND = "^(add\\s"+ID+"\\sto\\s"+ID+")$";
	private final String GET_COMMAND = "^(getnumseats\\s"+TYPE+"\\s"+ID+")$";
	private final String DEL_COMMAND = "^(delete\\s"+TYPE+"\\s"+ID+")$";
	private final String REM_COMMAND = "^(remove\\s"+ID+"\\sfrom\\s"+ID+")$";

	private final String HELP_COMMAND = "^(help)$";

	private final String COMMANDS = NEW_COMMAND+"|"+ADD_COMMAND+"|"+GET_COMMAND+"|"+DEL_COMMAND+"|"+REM_COMMAND+"|"+HELP_COMMAND;

	public CommandInputControl(String inputCommand)
	{
		mInputCommand = inputCommand;
	}

	public boolean isCommand(String commandPattern)
	{
		Pattern p = Pattern.compile(commandPattern);
		return p.matcher(mInputCommand).find();
	}

	public boolean checkCommand()
	{
		if (isCommand(COMMANDS))
		{
			try
			{
				handleCommand();
				return true;
			}
			catch (NullPointerException npe)
			{
				System.err.println(npe.getMessage());
			}
		}
		else
		{
			Log log = Log.getInstance();
			log.addLogLine("command not correct");
		}
		return false;
	}

	public void handleCommand() throws NullPointerException
	{
		Command command = null;

		if (isCommand(NEWTRAIN_COMMAND))
		{
			command = new NewTrainCommand();
		}
		else if (isCommand(NEWWAGON_COMMAND))
		{
			command = new NewWagonCommand();
		}
		else if (isCommand(ADD_COMMAND))
		{
			command = new AddCommand();
		}
		else if (isCommand(GET_COMMAND))
		{
			command = new GetCommand();
		}
		else if (isCommand(DEL_COMMAND))
		{
			command = new DeleteCommand();
		}
		else if (isCommand(REM_COMMAND))
		{
			command = new RemoveCommand();
		}
		else if (isCommand(HELP_COMMAND))
		{
			command = new HelpCommand();
		}

		command.execute(mInputCommand);
	}
}
