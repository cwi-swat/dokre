package controller.output;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;

public class MessageLog extends JTextArea implements Observer {

	private static final long serialVersionUID = 7251401618317921382L;
	private String log;

	public MessageLog() {
		super();
		setEditable(false);
		setForeground(new java.awt.Color(255, 255, 255));
		setBackground(new java.awt.Color(0, 0, 0));
		setEditable(false);
		setFont(new java.awt.Font("Tahoma", 0, 11));
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("MessageLog.update(" + o + ", " + arg + ")");

		Object[] args = (Object[]) arg;
		if (args[1] instanceof String) {
			log = (String) args[1];
			setText(log);
		}
	}

}
