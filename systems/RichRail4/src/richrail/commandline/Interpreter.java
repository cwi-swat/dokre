package richrail.commandline;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;

import org.apache.log4j.Logger;

import richrail.domain.Depot;

public class Interpreter extends Observable{
	
	private Depot depot;
	
	private Logger logger = Logger.getLogger(Interpreter.class);
	
	public Interpreter(Depot depot, CommandLineDelegate delegate) {
		this.depot = depot;
	}

	public void execute(String commandline) {
		// Try to find the command
		Command cmd = new Command(commandline);
		String classpath = "richrail.commandline.commands."+cmd.getClassString();
		try {
							
			Class<? extends RichRailCommand> c = Class.forName(classpath).asSubclass(RichRailCommand.class);
			c.getDeclaredConstructor(Depot.class, Command.class, Interpreter.class).newInstance(depot, cmd, this);
			
		} catch (ClassNotFoundException e) {
			message("Unknown command, type 'help'");
		} catch (ClassCastException e) {
			logger.fatal(classpath+" Does not extend RichRailCommand!");
		} catch (NoSuchMethodException e) {
			logger.fatal(cmd.getClassString()+"(Depot, Command, Interpreter) is not implemented!");
		} catch (IllegalAccessException e) {
			logger.fatal(cmd.getClassString() + " Constructor is not public!");
		} catch (InvocationTargetException e) {
			logger.fatal(cmd.getClassString() + " ?? InvocationTargetException ??");
		} catch (InstantiationException e) {
			logger.fatal(cmd.getClassString() + " ?? InstantiationException ??");
		} catch (SecurityException e) {
			logger.fatal(cmd.getClassString() + " ?? SecurityException ??");
		}
		
	}
	
	public void message(String message){
		this.setChanged();
		this.notifyObservers(message);
		logger.info(message);
	}
	
}
