import java.util.ArrayList;

public class TrainsToText {
	
	private ArrayList<Train> trains;
	
	public TrainsToText(ArrayList<Train> t){
		trains = t;
	}
	
	public String getWagons(){
		String s = "wagons\n";		
		for(Wagon w : Train.comeHereThomas().getWagons() ){
			s = s + "(" + w.getWagonName() + ":"+ w.getNumSeats() + ") ";
		}
			
		return s + "\n";
	}
	
	public String getTrains(){
		String s = "trains\n";
			for(Train t : trains){
				s = s + "(" + t.getTrainName() + ")";
					for(Wagon w : t.getWagons()){
						s = s + "-(" + w.getWagonName() + ")";
					}
				s = s + "\n";
			}
		return s;
	}
	
	public String toString(){
		return getWagons() + "\n" + getTrains();
	}
}
