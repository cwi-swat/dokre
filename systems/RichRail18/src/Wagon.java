
public class Wagon extends Vehicle{
	private String name;
	private int seats;
	
	public Wagon(String nm, int s){
		setName(nm);
		setSeats(s);
	}

	public Wagon(String nm) {
		setName(nm);
		seats = 20;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getSeats() {
		return seats;
	}
	
	public String toString(){
		String s = "{" + name + ":" + seats + "}";
		return s;
	}

}
