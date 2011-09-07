package GUI.output;

import java.util.ArrayList;

import javax.swing.JTextArea;

import datahandler.DataHandler;
import domain.Train;
import domain.Wagon;
import domain.WagonType;

public class GUITextOverview extends GUIoutput {
	private static final long serialVersionUID = -6220928975205973750L;

	private String displayText;

	private ArrayList<WagonType> wagonTypes;
	private ArrayList<Train> trains;

	private JTextArea textArea = new JTextArea();
	public GUITextOverview() {
		textArea.setSize(getSize());
		textArea.setLocation(0,0);
		trains = new ArrayList<Train>();
		wagonTypes = new ArrayList<WagonType>();
		this.add(textArea);
	}

	public void notifyOutput(DataHandler handle) {
		displayText = "";
		wagonTypes = handle.getAllWagonTypes();		
		trains = handle.getAllTrains();	
		
		displayTotal();
		displayWagonTypes();
		displayTrains();
		
		textArea.setText(displayText);
	}

	private void displayTotal(){
		displayText = "Total trains: " + trains.size() + "\n";
		displayText += "Total wagontypes: " + wagonTypes.size()+"\n"+"\n";
	}

	private void displayWagonTypes(){
		displayText += "Wagons\n";
		for(WagonType w : wagonTypes){
			displayText += "("+w.getName()+":"+w.getSeats()+")  ";
		}
	}

	private void displayTrains(){
		displayText += "\nTrains\n";
		for(Train t : trains){
			displayText += "("+t.getName()+")";
			for(Wagon w: t.getWagons()){
				displayText += "-("+w.getType().getName()+":"+w.getType().getSeats()+")";
			}
			displayText += "\n";
		}
	}
}
