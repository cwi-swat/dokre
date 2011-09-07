package trainManagement;

public class Train {
	private int seat = 0;
	private String trId;

	public Train(String newId){
		trId = newId;
	}

	public void setSeat(int seat) {
		this.seat += seat;
	}

	public int getSeat() {
		return seat;
	}
	
	public void removeSeat(int removeSeat){
		this.seat = this.seat - removeSeat;
	}

	public String getTrId() {
		return trId;
	}
	
}
