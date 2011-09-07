package Commands;

import java.util.ArrayList;

import Domain.Train;
import Facade.TrainFacadeSingleton;

public class GetnumseatsTrainCommand implements Command{
	
	private String output = "Command not correct(Type help for a list of commands";
	private ArrayList<String> words;
	private TrainFacadeSingleton tfs = TrainFacadeSingleton.getTfs();
	private Train t;
	
	public GetnumseatsTrainCommand(ArrayList<String> words2) {
		words = words2;
		execute();
	}

	@Override
	public void execute() {
		String train;
		try {
			train = words.get(2);
		} catch (Exception e) {
			train ="";
		}
		if(train.equals("")) {
			output = "type a train name in";
		}
		boolean b = false;
		for(Train t: tfs.getTrains()) {
			if(t.getNaam().equals(train)) {
				this.t=t;
				b = true;
			}
		}
		if(!b) {
			output = "train " + train + "doesn't exist";
		}
		output = "number of seats in train " + train + ": " + t.getSeats();
	}

	@Override
	public String getOutput() {	
		return output;
	}

}
