package domain;

import java.util.ArrayList;

import logic.Controller;

public abstract class Command {
	protected String command;
	
	public Command()
	{
		this.command = "";
	}
	
	public Command(String command)
	{
		this();
		this.setCommand(command);
	}
	
	public String getCommand()
	{
		return command;
	}
	
	public void setCommand(String command)
	{
		this.command = command;
	}
	
	public abstract void execute(Controller controller);


}
