package GUI.output;

import javax.swing.JPanel;

import datahandler.DataHandler;

public abstract class GUIoutput extends JPanel {
	private static final long serialVersionUID = -8588730897086453657L;

	public abstract void notifyOutput(DataHandler handle);
}
