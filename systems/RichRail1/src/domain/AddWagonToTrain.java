package domain;

import logic.Controller;

public class AddWagonToTrain extends Command {

	private String wagonName;
	private String trainName;

	public String getWagon(){ return wagonName; }
	public String getTrain(){ return trainName; }
	public void setWagon(String wagon){ this.wagonName = wagon; }
	public void setTrain(String train){ this.trainName = train; }

	@Override
	public void execute(Controller controller) {
		boolean error1 = false;
		boolean error2 = false;
		for(Train t : controller.getTrains()){
			if(t.getName().equals(trainName)){
				for(Wagon wagonObject : controller.getWagons()){
					if(wagonObject.getName().equals(wagonName)){
						t.addWagon(wagonObject);
						controller.addLogcommand("wagon " + wagonName + " added to train " + trainName);
						error1 = false;
						error2 = false;
						break;
					}else{
						error1 = true;
					}
				}				
			}else{
				error2 = true;
			}
		}
		if(error1)
		{
			controller.addLogcommand("error: wagon " + wagonName + " does not exist and cannot be added to " + trainName);
		}else if(error2)
		{
			controller.addLogcommand("error: train " + trainName + " does not exist");
		}
		controller.update();
	}

}
