package domain;

import logic.Controller;
	

public class CreateWagon extends Command {

	private String wagonName;

	public String getWagon(){ return wagonName; }
	public void setWagon(String wagon){ this.wagonName = wagon; }

	@Override
	public void execute(Controller c) {
		NormalWagon normalWagon = new NormalWagon(wagonName, 20);
		c.addLogcommand("wagon " + wagonName + " created with 20 seats");
		c.addWagon(normalWagon);
	}

}
