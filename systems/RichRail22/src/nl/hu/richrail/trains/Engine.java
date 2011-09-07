package nl.hu.richrail.trains;

public class Engine extends RollingStock
{
	private static final String RR_TYPE = "loco";
	
	
	public Engine(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getSeatCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	
}
