package domain;

import logic.Controller;

public class CreateEndWagon extends Command{
	
	private String wagonName;

	public String getWagon(){ return wagonName; }
	public void setWagon(String wagon){ this.wagonName = wagon; }

	@Override
	public void execute(Controller controller) {
		EndWagon end = new EndWagon(wagonName, 20);
		controller.addLogcommand("endwagon " + wagonName + " created with 20 seats");
		controller.addWagon(end);
	}
}
