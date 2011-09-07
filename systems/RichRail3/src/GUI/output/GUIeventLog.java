package GUI.output;

import java.awt.Color;

import javax.swing.JTextArea;

import datahandler.DataHandler;

public class GUIeventLog extends GUIoutput {
	private static final long serialVersionUID = 1132948738616073559L;

	private JTextArea textArea;

	public GUIeventLog(){
		textArea = new JTextArea();
		textArea.setSize(getSize());
		textArea.setLocation(0,0);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		this.add(textArea);
	}

	public void notifyOutput(DataHandler handle) {
		logText(handle.getMessage());
	}	

	private void logText(String text){
		textArea.setText(text + "\n" + textArea.getText());
	}
}