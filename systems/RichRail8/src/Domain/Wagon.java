package Domain;


public class Wagon {
	
	private String naam;
	private WagonType type;
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public WagonType getWagonType() {
		return type;
	}
	public void setType(WagonType type) {
		this.type = type;
	}
	public Wagon(String naam, WagonType type) {
		this.naam = naam;
		this.type = type;
	}
	
	

}
