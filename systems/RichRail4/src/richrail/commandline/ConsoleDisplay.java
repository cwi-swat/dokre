package richrail.commandline;

import java.util.List;

import richrail.domain.Train;
import richrail.domain.Wagon;
import richrail.gui.OutputPanel;

@SuppressWarnings("serial")
public class ConsoleDisplay extends OutputPanel {

	public void update(List<Train> trains, List<Wagon> wagons) {
		String s = "";
		if(wagons != null){
			s += "Wagons:\n";
			for(Wagon wg : wagons){
				s += "("+wg.getName()+":"+wg.getSeats()+")";
			}
			s += "\n";
		}
		
		
		if(trains != null){
			s += "Trains:\n";
			for(Train tr : trains){
				s += "("+tr.getName()+")";
				if(!tr.getWagons().isEmpty()){
					for(Wagon wg : tr.getWagons()){
						s += "-("+wg.getName()+")";
					}
				}
				s += "\n";
			}
		}
		
		
		this.setText(s);
		
	}

}
