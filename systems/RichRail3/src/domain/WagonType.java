package domain;

public class WagonType {
    private int seats;
    private String name;
    private boolean showNumSeats = false;
    
    public WagonType(){
    	
    }
    
	public WagonType(String wagonID, int numberOfSeats) {
		this.setSeats(numberOfSeats);
		this.setName(wagonID);
	}
	
	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	public int getSeats() {
		return seats;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setShowNumSeats(boolean showNumSeats) {
		this.showNumSeats = showNumSeats;
	}
	
	public boolean isShowNumSeats() {
		return showNumSeats;
	}
}
