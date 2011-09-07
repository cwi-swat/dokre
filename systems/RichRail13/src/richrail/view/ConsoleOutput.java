package richrail.view;

import richrail.control.Logger;
import richrail.control.Observer;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class ConsoleOutput extends JTextArea implements Observer {

	private final Logger logger;

	public ConsoleOutput(Logger logger) {

		this.logger = logger;
		logger.addObserver(this);

		DefaultCaret caret = (DefaultCaret) getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
	}

	public void notifyChange() {
		this.append(logger.getLastLog() + "\n");
	}

}
