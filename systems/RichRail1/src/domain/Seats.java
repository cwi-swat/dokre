package domain;

import logic.Controller;

public class Seats extends Command {

	private String trainName;
	private String wagonName;
	private String seatString;
	
	public void execute(Controller controller) {
		int seats = 0;
		if(wagonName == null){
			for(Train train : controller.getTrains()){
				if(train.getName().equals(trainName)){
					for(Wagon wagon :train.getWagons()){
						seats += wagon.getSeats();
					}
					controller.addLogcommand("number of seats in train " + train.getName() + ": "+ seats);
					controller.update();
				}
			}
		}
		else{
			for(Wagon wagon : controller.getWagons()){
				if(wagon.getName().equals(wagonName)){
					seats = wagon.getSeats();
					controller.addLogcommand("number of seats in wagon " + wagon.getName() + ": "+ seats);
					controller.update();
				}
			}
		}
	}
	
	public String getSeatString(){
		return seatString;
	}
	
	void seatText(String s){
		seatString = "number of seats in " + s;
	}
	
	public void setTrain(String train) {this.trainName = train;	}
	public String getTrain() {		return trainName;	}
	public void setWagon(String wagon) {		this.wagonName = wagon;	}
	public String getWagon() {		return wagonName;	}

}
