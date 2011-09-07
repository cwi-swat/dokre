package controller.command;

import view.PopUpJFrame;
import controller.output.GraphicDisplay;
import controller.output.TextLog;

public class Displaycommand implements CommandInterface {
	private CommandResult cresult = new CommandResult();

	@Override
	public CommandResult execute(String[] cmd) throws Exception {
		display(cmd);
		return cresult;
	}

	private void display(String[] cmd) throws Exception {
		System.out.println("Displaycommand.display(" + cmd + ")");

		Object o = null;
		if (cmd[1].equals("graphicdisplay")) {
			o = new GraphicDisplay();
		} else if (cmd[1].equals("textlog")) {
			o = new TextLog();
		} else {
			throw new Exception("unknown type given");
		}

		// Create new frame
		PopUpJFrame popup = new PopUpJFrame();
		// Create new display
		popup.initGUI();
		// Set the display in the new frame
		popup.setDisplay(o);
		cresult.setObject(o);

		cresult.setMessage("display \"" + cmd[1] + "\" created");
	}
}
