package ui;

import java.util.ArrayList;
import javax.swing.*;
import domain.Train;
import logic.Controller;
import logic.Subject;

public abstract class View extends JPanel {
	public View()
	{
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	public abstract void update(Controller controller);
}
