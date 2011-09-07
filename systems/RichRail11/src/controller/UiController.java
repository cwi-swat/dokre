package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.RichRailJFrame;
import controller.output.GraphicDisplay;
import controller.output.MessageLog;
import controller.output.TextLog;

public class UiController implements ActionListener {
	private RichRailJFrame jframe;
	private CommandController cc;

	/**
	 * GuiController controller.
	 */
	public UiController() {
		// Create new jframe
		jframe = new RichRailJFrame(this);
		// Create the traincontroller
		cc = new CommandController();

		// Create the default display for the gui
		GraphicDisplay graphicDisplay = new GraphicDisplay();
		TextLog textlog = new TextLog();
		MessageLog messagelog = new MessageLog();

		// Add the different displays to the observerable.
		cc.addObserver(graphicDisplay);
		cc.addObserver(textlog);

		// Add the different logs to the observable
		cc.addObserver(messagelog);

		// Start the GUI
		jframe.initGUI();

		// Set the outputs
		jframe.setGraphicDisplay(graphicDisplay);
		jframe.setTextOutput(textlog);

		// Set the logdisplay
		jframe.setMessageLog(messagelog);

		// TODO weghalen, dit is ene test:
		cc.parseCommand("new train tr1");
		cc.parseCommand("new train tr2");
		cc.parseCommand("new wagon wg1");
		cc.parseCommand("new wagon wg2");
		cc.parseCommand("new wagon wg3");

		cc.parseCommand("add wg1 to tr1");
		cc.parseCommand("add wg2 to tr1");
		cc.parseCommand("add wg3 to tr1");

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == jframe.jButtonExecute || arg0.getSource() == jframe.jTextFieldCmd) {
			System.out.println("GuiController.actionPerformed() - Execute command");

			String cmd = jframe.getjTextFieldCmd().getText();
			cc.parseCommand(cmd);

		} else if (arg0.getSource() == jframe.jButtonDuplicateGraphic) {
			System.out.println("GuiController.actionPerformed() - Duplicate graphic view");
			cc.parseCommand("display graphicdisplay");

		} else if (arg0.getSource() == jframe.jButtonDuplicateText) {
			System.out.println("GuiController.actionPerformed() - Duplicate text view");
			cc.parseCommand("display textlog");

		} else {
			System.out.println("GuiController.actionPerformed() - Unknown button is pressed");
		}
	}
}
