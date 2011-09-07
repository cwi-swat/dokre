package Domain;

public class Wagon {
	private WagonType type = new WagonType();
	private int ID;
	
	public Wagon(int id, WagonType tp){
		type =  tp;
		ID = id;
	}

	public WagonType getType() {
		return type;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getID() {
		return ID;
	}
}
