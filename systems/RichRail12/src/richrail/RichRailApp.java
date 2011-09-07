package richrail;

import richrail.command.AddCommand;
import richrail.command.ClearLogCommand;
import richrail.command.CommandParser;
import richrail.command.DeleteCommand;
import richrail.command.ExitCommand;
import richrail.command.GetCommand;
import richrail.command.NewCommand;
import richrail.command.NewViewCommand;
import richrail.command.RemoveCommand;
import richrail.domain.ObserverableTrainDepot;
import richrail.tools.Log;
import richrail.view.MainFrame;

public class RichRailApp {
	
	private ObserverableTrainDepot trainDepot;
	private MainFrame mainFrame;
	private CommandParser commandParser;
	
	public static final String STARTED = "application started";
	
	public RichRailApp() {
		this.trainDepot = new ObserverableTrainDepot();
		this.mainFrame = new MainFrame(this);
		this.mainFrame.setVisible(true);
		
		// Command parser
		this.commandParser = new CommandParser();
		
		// TrainDepot commands
		this.commandParser.addAvailableCommand(new NewCommand(trainDepot));
		this.commandParser.addAvailableCommand(new DeleteCommand(trainDepot));
		this.commandParser.addAvailableCommand(new AddCommand(trainDepot));
		this.commandParser.addAvailableCommand(new RemoveCommand(trainDepot));
		this.commandParser.addAvailableCommand(new GetCommand(trainDepot));
		
		// Extra commands
		this.commandParser.addAvailableCommand(new ExitCommand());
		this.commandParser.addAvailableCommand(new ClearLogCommand());
		this.commandParser.addAvailableCommand(new NewViewCommand(trainDepot));
		
		Log.log(this,STARTED, Log.INFO);
	}
	
	/**
	 * 
	 * @return The garage that contains all trains and wagons
	 */
	public ObserverableTrainDepot getTrainDepot() {
		return this.trainDepot;
	}
	
	public MainFrame getMainFrame() {
		return this.mainFrame;
	}
	
	public static void main(String[] args) {
		new RichRailApp();	
	}

	public CommandParser getCommandParser() {
		return this.commandParser;
	}
}
