package Domain;
import java.util.ArrayList;



public class Train {

	private String naam;
	private ArrayList<Wagon> wagons = new ArrayList<Wagon>();
	public Train(String naam) {
		this.naam = naam;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public ArrayList<Wagon> getWagons() {
		return wagons;
	}
	public void setWagons(ArrayList<Wagon> wagons) {
		this.wagons = wagons;
	}
	public void AddWagon(Wagon w) {
		wagons.add(w);
	}
	public int getSeats() {
		int i = 0;
		for(Wagon w: wagons) {
			i += w.getWagonType().getSeats();
		}
		return i;
	}
	public void RemoveWagon(Wagon wagon) {
		wagons.remove(wagon);
	}
	
}
