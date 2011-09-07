package trainManagement;

public class Wagon {
	private int seat = 20;
	private String wgId;
	
	public Wagon(String newID,int newSeat){
		wgId = newID;
		setSeat(newSeat);
	}

	/**
	 * @param seat the seat to set
	 */
	public void setSeat(int seat) {
		this.seat = seat;
	}

	/**
	 * @return the seat
	 */
	public int getSeat() {
		return seat;
	}

	public String getWgId() {
		return wgId;
	}
	
	public String getWagon(){
		return "("+getWgId()+":"+getSeat()+")";
	}
	
	public String toString(){
		return wgId;
	}

}
