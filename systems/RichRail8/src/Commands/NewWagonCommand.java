package Commands;

import java.util.ArrayList;

import Domain.Wagon;
import Domain.WagonType;
import Facade.TrainFacadeSingleton;


public class NewWagonCommand implements Command {

	private String output = "";
	private ArrayList<String> words;
	private TrainFacadeSingleton tfs = TrainFacadeSingleton.getTfs();

	public NewWagonCommand(ArrayList<String> words2) {
		words = words2;
		execute();
	}

	@Override
	public void execute() {
		boolean b = false;
		String w3;
		String w4;
		String w5;
		try {
			w3 = words.get(2);
		} catch (Exception e) {
			w3 = "";
		}
		try { 
			w4 = words.get(3);
		} catch (Exception e) {
			w4="";
		}
		try {
			w5 = words.get(4);
		} catch(Exception e) {
			w5="";
		}
		if(w3.equals("")) {
			output = "Type in a wagon name";
			return;
		}

		for(Wagon w: tfs.getWagons()) {
			if(w.getNaam().equals(w3)) {
				output = "Wagon already exists";
				return;
			}
		}
		if(!(w4.equals("numseats"))) {
			Wagon g = new Wagon(w3, new WagonType(20));
			output = "wagon "+ g.getNaam() + " is created with " + g.getWagonType().getSeats() + " seats";
			tfs.addWagon(g);
			return;
		}
		if(w5.equals("")) {
			output = "Type in a number of seats";
			return;
		}
		try {
		Wagon w = new Wagon(w3, new WagonType(Integer.valueOf(w5)));
		output = "wagon "+ w.getNaam() + " is created with " + w.getWagonType().getSeats() + " seats";
		tfs.addWagon(w);
		
		
		} catch (Exception e) {
			output = "Type in a valid number of seats";
			return;
		}
		
		
	}



	@Override
	public String getOutput() {
		// TODO Auto-generated method stub
		return output;
	}

}
