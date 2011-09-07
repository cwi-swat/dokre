package domain;

import logic.Controller;

public class RemoveWagonFromTrain extends Command {

	private String wagonName;
	private String trainName;

	public String getWagon(){ return wagonName; }
	public String getTrain(){ return trainName; }
	public void setWagon(String wagon){ this.wagonName = wagon; }
	public void setTrain(String train){ this.trainName = train; }

	@Override
	public void execute(Controller controller) {
		boolean error = false;
		for(Train train : controller.getTrains()){
			if(train.getName().equals(trainName)){
				for(Wagon wagon : train.getWagons()){
					if(wagon.getName().equals(wagonName)){
						train.removeWagon(wagon);
						controller.addLogcommand("wagon " + wagonName + " was removed from train " + trainName);
						error = false;
						break;
					}else{
						error = true;
					}
				}				
			}			
		}
		if(error)
		{
			controller.addLogcommand("error: train " + trainName + " does not contain " + wagonName);
		}
		
		controller.update();
	}

}
