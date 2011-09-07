package domain;

import logic.Controller;

public class DeleteWagon extends Command {

	private String wagonName;

	public String getWagon() {
		return wagonName;
	}

	public void setWagon(String wagon) {
		this.wagonName = wagon;
	}

	@Override
	public void execute(Controller controller) {
		for (Wagon wagon : controller.getWagons()) {
			if (wagon.getName().equals(wagonName)) {
				controller.removeWagon(wagon);
				controller.addLogcommand("wagon " + wagonName + " deleted");
				controller.update();
				break;
			}
			else if (controller.getWagons().indexOf(wagon) == (controller.getWagons().size() - 1)) {
				controller.addLogcommand("wagon " + wagonName + " does not exist");
				controller.update();
				System.out.println("Wagon does not exist");
			}
		for(Train train : controller.getTrains()){
			for(Wagon wa : train.getWagons()){
				if(wa.getName().equals(this.wagonName)){
					train.removeWagon(wa);
				}
			}
		}
		}
		
	}
}
