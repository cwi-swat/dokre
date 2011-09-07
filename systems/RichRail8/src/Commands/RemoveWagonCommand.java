package Commands;

import java.util.ArrayList;

import Domain.Train;
import Domain.Wagon;
import Facade.TrainFacadeSingleton;


public class RemoveWagonCommand implements Command {
	
	private String output = "Command not correct(Type help for a list of commands";
	private ArrayList<String> words;
	private TrainFacadeSingleton tfs = TrainFacadeSingleton.getTfs();
	private Wagon wagon;
	
	public RemoveWagonCommand(ArrayList<String> words2) {
		words = words2;
		execute();
	}

	@Override
	public void execute() {
		String w2;
		String w3;
		String w4;
		try {
			w2 = words.get(1);
		} catch (Exception e) {
		w2=""; }
		try {
			w3 = words.get(2);
		} catch (Exception e) {
			w3="";
		}
		try {
			w4 = words.get(3);
		} catch (Exception e) {
			w4= "";
		}
		if(w2.equals("")) {
			output = "Type in a wagon name";
			return;
		}
		boolean b = false;
		for(Wagon w: tfs.getWagons()) {
			if(w.getNaam().equals(w2)) {
				b=true;
				wagon = w;
			}
		}
		if(!b) {
			output= "Wagon " +w2 +" doesn't exist";
			return;
		}
		if(!(w3.equals("from"))) {
			output = "Syntax for removing a wagon from a train = remove <wg id> from <train id>";
			return;
		}
		if(w4.equals("")) {
			output = "type in a train";
			return;
		}
		boolean c = false;
		for(Train t: tfs.getTrains()) {
			if(t.getNaam().equals(w4)) {
				for(Wagon w: t.getWagons()) {
					if(w.getNaam().equals(wagon.getNaam())) {
						c = true;
						tfs.removeWagonFromTrain(t, wagon);
						output = "Wagon " + wagon.getNaam() + " removed from train " + t.getNaam();
						return;
					}
				}
				if(!c) {
					output = "Train does not have that wagon anymore.";
					return;
					
				}
				
			}
		}
		output = "train " + w4 + " doesn't exist";
		
	}

	@Override
	public String getOutput() {
		return output;
	}

}

