package domain;

import java.util.ArrayList;

public class Train {
    private ArrayList <Wagon> wagons;
    private String name;
    public boolean showNumSeats = false;
    
    public Train(){
    	wagons = new ArrayList<Wagon>();
    }
    
    public boolean addWagon(Wagon wagon){
    	return wagons.add(wagon);
    }
    
	public ArrayList <Wagon> getWagons() {
		return wagons;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setWagons(ArrayList<Wagon> wagons){
		this.wagons = wagons;
	}
	
	public int getNumSeats() {
		int ret = 0;
		for(int i = 0; i < wagons.size(); i++){
			ret += wagons.get(i).getType().getSeats();
		}
		return ret;
	}
}
