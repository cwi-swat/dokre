package models;

public class Wagon {

	private int wagon_id;
	private WagonType wagon_type;

	public Wagon(int wagon_id, WagonType wagon_type) {
		this.wagon_id = wagon_id;
		this.wagon_type = wagon_type;
	}

	//Getters & Setters
	public WagonType getWagon_type() {
		return wagon_type;
	}

	public void setWagon_type(WagonType wagon_type) {
		this.wagon_type = wagon_type;
	}

	public int getWagon_id() {
		return wagon_id;
	}

	public void setWagon_id(int wagon_id) {
		this.wagon_id = wagon_id;
	}

	public String toString(){
		return "("+this.getWagon_type().getName()+")";
	}
}
