package domain;

import logic.Controller;

public class CreateWagonSeats extends Command {

	private String wagonName;
	private int seats;

	public String getWagon(){ return wagonName; }
	public void setWagon(String wagon){ this.wagonName = wagon; }

	@Override
	public void execute(Controller controller) {
		NormalWagon normalWagon = new NormalWagon(wagonName,seats);
		controller.addLogcommand("wagon " + wagonName + " created with " + seats + " seats");
		controller.addWagon(normalWagon);
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public int getSeats() {
		return seats;
	}

}
