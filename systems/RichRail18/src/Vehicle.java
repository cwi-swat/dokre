import java.util.ArrayList;

public class Vehicle {
	private ArrayList<Wagon> listWagons = new ArrayList<Wagon>();
	private ArrayList<Train> listTrains = new ArrayList<Train>();

	public Vehicle(){
		
	}
	
	public boolean checkWagon(String wgn){
		boolean b = true;
		for(Wagon w : listWagons){
			if(w.getName().equals(wgn)){
				b = false;
			}
		}
		return b;
	}

	public String getWagonName(Wagon w) {
		return w.getName();
	}
	
	public String getTrainName(Train t) {
		return t.getName();
	}

	public void addWagon(Train t, Wagon w) {
		t.addWagon(w);
		
	}

	public int getSeats(Train t) {
		return t.getSeats();
	}

	public int getSeats(Wagon w) {
		return w.getSeats();
	}

	public boolean removeWagon(Train t, String wgn) {
		t.removeWagon(wgn);
		return true;
	}

	public String toStringTrain(Train t) {
		return t.toString();
	}
	
	public String toStringWagon(Wagon w) {
		return w.toString();
	}
	
}
