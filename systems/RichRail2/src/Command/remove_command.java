package Command;

import controller.Station;

public class remove_command implements Command {

	private String command;
	public remove_command(String cmd){
		this.command = cmd;
		this.Execute(this.command);
	}
	@Override
	public void Execute(String cmd) {
		int spaceFirst = cmd.indexOf(' ');
		int spaceLast = cmd.lastIndexOf(' ');
		String removeItem = cmd.substring(0, spaceFirst);
		String fromItem = cmd.substring(spaceLast+1);

		Station.getInstance().removeWagonFromTrain(removeItem, fromItem);

	}
}
