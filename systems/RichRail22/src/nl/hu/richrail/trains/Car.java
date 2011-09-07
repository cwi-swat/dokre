package nl.hu.richrail.trains;

import java.util.Set;

public class Car extends RollingStock
{
	private static final String RR_TYPE = "waggon";
	
	

	private int seatCount = 20;
	
	public Car(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public Car(String id, int seatCount) {
		super(id);
		this.seatCount = seatCount;
	}
	@Override
	public int getSeatCount() {
		// TODO Auto-generated method stub
		return seatCount;
	}

}
