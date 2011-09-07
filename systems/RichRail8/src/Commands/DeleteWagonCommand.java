package Commands;

import java.util.ArrayList;

import Domain.Wagon;
import Facade.TrainFacadeSingleton;


public class DeleteWagonCommand implements Command {

	private ArrayList<String> input = new ArrayList<String>();
	private String output ="";
	private TrainFacadeSingleton tfs= TrainFacadeSingleton.getTfs();


	public DeleteWagonCommand(ArrayList<String> words){
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
			output = "type a wagon name in";
			return;
		}
		boolean b = false;
		for(Wagon w	: tfs.getWagons()) {
			if(w.getNaam().equals(s)) {
				tfs.deleteWagon(w);
				output = "Wagon " +w.getNaam()+" removed";
				b = true;
				return;
			} 
		}
		if(!b){
			output = "Wagon does not exist";
			return;
		}
	}



	public String getOutput() {
		return output;
	}

}
