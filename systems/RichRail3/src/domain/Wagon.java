package domain;

public class Wagon {
    private WagonType type;

	public Wagon(WagonType type) {
		this.type = type;
	}

	public WagonType getType() {
		return type;
	}
}