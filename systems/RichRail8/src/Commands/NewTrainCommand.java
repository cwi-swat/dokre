package Commands;

import java.util.ArrayList;

import Domain.Train;
import Facade.TrainFacadeSingleton;


public class NewTrainCommand implements Command{
	private ArrayList<String> input = new ArrayList<String>();
	private String output ="";
	private TrainFacadeSingleton tfs= TrainFacadeSingleton.getTfs();
	
	public NewTrainCommand(ArrayList<String> words) {
		input = words;
		execute();
	}

	@Override
	public void execute() {
		boolean b = false;
		String s = "";
		try {
			s = input.get(2);
		} catch(Exception e) {
			s = "";
		}
		System.out.println(s);
			if(s.equals("")) {
				output = "type a train name in";
				return;
			}
			
			
			for(Train t	: tfs.getTrains()) {
				if (t.getNaam().equals(s)) {
					output = "Train already exists";
					return;
				}
			}
			
		
				Train t = new Train(s);
				tfs.addTrain(t);
				output = "Train "+ t.getNaam() + " is created";
			
			
	}
	public String getOutput() {
		return output;
	}

}
