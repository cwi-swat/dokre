package domain;

public class Part {
	private String name;
	private int numberOfSeats;
	private int numberOfMotors;

	/**
	 * The train to which the wagon is attached to.
	 * Can be null, which means that the wagon is not part of a train.
	 */
	private Train train;

	public Part(String name) {
		this.name = name;
		this.numberOfSeats = 20;
	}
	
	public Part(String name, int numberOfSeats) {
		this.name = name;
		this.numberOfSeats = numberOfSeats;
	}
	
	public Part(String name, int numberOfSeats, int numberOfMotors) {
		this.name = name;
		this.numberOfSeats = numberOfSeats;
		this.numberOfMotors = numberOfMotors;
	}

	public String getName(){
		return name;
	}
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	
	public boolean hasMotor() {
		return numberOfMotors > 0;
	}
	public boolean isLocomotive() {
		return hasMotor() && numberOfSeats == 0;
	}
	
	public Train getTrain() {
		return train;
	}
	
	public void setTrain(Train train) {
		this.train = train;
	}
		
	/**
	 * Detach the part from a train.
	 */
	public void detach() {
		if(train != null) {
			train.removePart(this);
		}
	}
	
	public String toString() {
		if(isLocomotive())
			return String.format("(%s)", name);
		else
			return String.format("(%s:%s)", name, numberOfSeats);
	}
}