package richrail.domain;

public class Wagon implements Seatable{
	private final String name;
	private final int numberOfSeats;

	public Wagon(String name, int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
}
