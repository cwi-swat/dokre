package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JTextArea;

import logic.Controller;

public class LogView extends View {
	ArrayList<String> commands;
	JTextArea log;
	
	public LogView() {
		commands = new ArrayList<String>();
		log = new JTextArea();
		
		this.setLayout(new GridLayout(1,1));
		
		log.setSize(350, 250);
		log.setBackground(Color.BLACK);
		log.setForeground(Color.WHITE);
		log.setText("Commands Executed: \n");
		log.setEditable(false);
		log.setVisible(true);
		
		this.add(log);
		
		this.setSize(350,250);
		this.setVisible(true);
	}
	
	public void printLog(ArrayList<String> commands) {
		log.setText("Commands Executed: \n");
		if (commands.size() > 0) {
			int size = commands.size();
			for (int i = size-1; i >= 0; i--) {
				log.setText(log.getText() + commands.get(i) + "\n");
			}
		}
	}
	
	@Override
	public void update(Controller controller) {
				this.commands = controller.getLogcommands();
				printLog(commands);
	}

}
