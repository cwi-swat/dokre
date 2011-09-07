package Commands;

import java.util.ArrayList;

import Domain.Train;
import Facade.TrainFacadeSingleton;


public class DeleteTrainCommand implements Command {

	private ArrayList<String> input = new ArrayList<String>();
	private String output ="";
	private TrainFacadeSingleton tfs= TrainFacadeSingleton.getTfs();


	public DeleteTrainCommand(ArrayList<String> words){
		input = words;
		execute();
	}


	public void execute() {
		String s;

		try {
			s = input.get(2);
		} catch(Exception e) {
			s = "";
		}

		if(s.equals("")) {
			output = "type a train name in";
			return;
		}
		boolean b = false;
		for(Train t	: tfs.getTrains()) {
			if(t.getNaam().equals(s)) {
				tfs.removeTrain(t);
				b = true;
				output = "Train " +t.getNaam()+" removed";
				return;
			} 
		}
		if(!b){
			output = "Train does not exist";
			return;
		}
	}



	public String getOutput() {
		return output;
	}

}

