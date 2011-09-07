package Command;

import javax.swing.JOptionPane;

import controller.Station;

public class delete_command implements Command{

	private String command;
	public delete_command(String cmd){
		this.command = cmd;
		this.Execute(this.command);
	}
	@Override
	public void Execute(String cmd) {
		
		int space = cmd.indexOf(' ');
		if(space < 1){
			JOptionPane.showMessageDialog(null, "Not a valid command", "ERROR", JOptionPane.ERROR_MESSAGE);
		}else{
			String type = cmd.substring(0, space);
			String id = cmd.substring((space+1));
			
			if (type.equalsIgnoreCase("Train")){
				if(Station.getInstance().checkTrains(id)){
					Station.getInstance().deleteTrains(id);
				}else{
					JOptionPane.showMessageDialog(null, "Train "+id+" does not exist", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			else if (type.equalsIgnoreCase("Wagon")){
				if(Station.getInstance().checkWagons(id)){
					Station.getInstance().deleteWagon(id);
				}else{
					JOptionPane.showMessageDialog(null, "Wagon "+id+" does not exist", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			else if (type.equalsIgnoreCase("Type")){
				if(Station.getInstance().checkTypes(id)){
					Station.getInstance().deleteType(id);
				}else{
					JOptionPane.showMessageDialog(null, "Type "+id+" does not exist", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Not a valid command", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

}


