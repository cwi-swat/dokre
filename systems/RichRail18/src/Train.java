import java.util.ArrayList;

public class Train extends Vehicle{
	private String name;
	private ArrayList<Wagon> wagons = new ArrayList<Wagon>();
	
	public Train(String nm){
		setName(nm);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setWagons(ArrayList<Wagon> wagons) {
		this.wagons = wagons;
	}

	public ArrayList<Wagon> getWagons() {
		return wagons;
	}

	public void addWagon(Wagon w) {
		wagons.add(w);		
	}

	public int getSeats() {
		int seats = 0;
		for(Wagon w : wagons){
			seats += w.getSeats();
		}
		return seats;
	}

	public boolean removeWagon(String wgn) {
		for(Wagon w : wagons){
			if(w.getName().equals(wgn)){
				wagons.remove(w);
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		String s = "{" + name + "}";
		for(Wagon w : wagons){
			s = s + "-{" + w.getName() + "}";
		}
		return s;
	}
}
