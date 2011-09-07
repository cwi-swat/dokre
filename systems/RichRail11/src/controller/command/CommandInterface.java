package controller.command;

public interface CommandInterface {
	public CommandResult execute(String[] cmd) throws Exception;

}