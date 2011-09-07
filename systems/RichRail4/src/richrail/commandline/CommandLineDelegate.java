package richrail.commandline;

import java.util.Observable;
import java.util.Observer;

import richrail.domain.Depot;

public class CommandLineDelegate {

	private Depot depot;
	private CommandLineGui gui;
	private Interpreter interpreter;
	
	
	public CommandLineDelegate(Depot depot, CommandLineGui cgui) {
		// TODO Auto-generated constructor stub
		this.depot = depot;
		this.gui = cgui;
		this.interpreter = new Interpreter(depot, this);
		
		gui.setDelegate(this);
		gui.setVisible(true);
		
		
		this.depot.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				updateGui((String) arg);				
			}
		});
		
		this.interpreter.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				outputMessage((String) arg);
				
			}
		});
		
	}
	
	
	public void updateGui(String message){
		gui.outputpanel.append(message);
		gui.displaypanel.update(depot.getTrains(), depot.getWagons());
		
	}

	public void execute() {
		String value = this.gui.inputpanel.inputfield.getText();
		gui.inputpanel.inputfield.setText("");
		if(value.length() > 0){
			this.interpreter.execute(value);
		}
	}
	
	public void outputMessage(String message){
		this.gui.outputMessage(message);
	}
}
