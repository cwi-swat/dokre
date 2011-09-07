package view.displays;

import java.util.Observer;

import javax.swing.JPanel;

public abstract class Display extends JPanel implements Observer
{
	private static final long serialVersionUID = 1L;

	public abstract String getDisplayName();
}
