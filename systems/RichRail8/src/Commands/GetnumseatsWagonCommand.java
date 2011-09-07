package Commands;

import java.util.ArrayList;

import Domain.Wagon;
import Facade.TrainFacadeSingleton;

public class GetnumseatsWagonCommand implements Command{
	
	private String output = "Command not correct(Type help for a list of commands";
	private ArrayList<String> words;
	private TrainFacadeSingleton tfs = TrainFacadeSingleton.getTfs();
	private Wagon w;

	
	public GetnumseatsWagonCommand(ArrayList<String> words2) {
		words = words2;
		execute();
	}
	@Override
	public void execute() {
		String wagonname;
		try {
			wagonname = words.get(2);
		} catch (Exception e){ 
			wagonname = "";
		}
		if(wagonname.equals("")) {
			output = "Type a wagon name in";
			return;
		}
		boolean b = false;
		for(Wagon g: tfs.getWagons()) {
			if(wagonname.equals(g.getNaam())) {
				b=true;
				w = g;
			}
		}
		if(!b) {
			output = "Wagon " + wagonname + " doesn't exist";
			return;
		}
		output = "number of seats in wagons " + w.getNaam() + ": " + w.getWagonType().getSeats();
	
		
	}

	@Override
	public String getOutput() {	
		return output;
	}

}
