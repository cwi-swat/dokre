package richrail.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import richrail.tools.Log;

public class LogObserverPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	
	private JTextArea textArea;

	public LogObserverPanel() {
		Log.getInstance().addObserver(this);
		
		textArea = new JTextArea(5,25);
		textArea.setLineWrap(true);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setPreferredSize(new Dimension(350,200));
		this.add(textArea);
	}

	@Override
	public void update(Observable o, Object arg) {
		textArea.setText(Log.getInstance().toString());
		if(o instanceof Log) {
			this.textArea.setText(o.toString());
		}
	}
}
