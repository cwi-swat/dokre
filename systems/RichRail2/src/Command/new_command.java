package Command;

import javax.swing.JOptionPane;

import controller.Station;
public class new_command implements Command{


	private String command;
	public new_command(String cmd){
		this.command = cmd;
		this.Execute(this.command);
	}
	
	public void Execute(String cmd) {
		int space = cmd.indexOf(' ');
		if(space > 0){
			String type = cmd.substring(0, space);
			String id = cmd.substring((space+1));
			if(type.equalsIgnoreCase("Train")){
				if(Station.getInstance().checkTrains(id)){
					JOptionPane.showMessageDialog(null, "Train "+id+" already exists", "ERROR", JOptionPane.ERROR_MESSAGE);
				}else{
					this.createTrain(id);
				}
				
			}else if(type.equalsIgnoreCase("Wagon")){
				this.createWagon(id);
			}else if(type.equalsIgnoreCase("Type")){
				if(Station.getInstance().checkTypes(id)){
					JOptionPane.showMessageDialog(null, "Type "+id+" already exists", "ERROR", JOptionPane.ERROR_MESSAGE);
				}else{
					if (cmd.indexOf("numseats") > 1){
						int seatsIndex = cmd.lastIndexOf(' ');
						int endIndexTypeID = cmd.indexOf(" ", space+1);
						String seats = cmd.substring(seatsIndex+1);
						id = cmd.substring(space+1, endIndexTypeID);
						int seatsint = Integer.parseInt(seats);
						this.createType(id, seatsint);
					}else{
						this.createType(id, 0);
					}
					
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "Not a valid command\n Only Train [name] Wagon [name] or Type [name] allowed", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Not a valid command\n Only Train [name] Wagon [name] or Type [name] allowed", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	private void createTrain(String id){
		Station.getInstance().createTrain(id);
	}
	private void createWagon(String cmd){
		
		int space = cmd.indexOf(' ');
		if(space < 1){
			JOptionPane.showMessageDialog(null, "Not a valid command\n Add a Wagon type", "ERROR", JOptionPane.ERROR_MESSAGE);
		}else{
			String id = cmd.substring(0, space);
			if(Station.getInstance().checkWagons(id)){
				JOptionPane.showMessageDialog(null, "Wagon "+id+" already exists", "ERROR", JOptionPane.ERROR_MESSAGE);
			}else{
				String typeId = cmd.substring(space+1);
				if(!Station.getInstance().checkTypes(typeId)){
					JOptionPane.showMessageDialog(null, "Type "+typeId+" does not exist\n Create type first", "ERROR", JOptionPane.ERROR_MESSAGE);
				}else{
					Station.getInstance().createWagon(id, Station.getInstance().getType(typeId));
				}
				
			}
			
		}
		
	}

	private void createType(String id, int numseats){
		Station.getInstance().createType(id, numseats);
	}
}
