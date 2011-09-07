package Command;

import javax.swing.JOptionPane;

import controller.Station;

public class get_command implements Command{

	private String command;
	public get_command(String cmd){
		this.command = cmd;
		this.Execute(this.command);
	}
	@Override
	public void Execute(String cmd) {
		int spaceLast = cmd.lastIndexOf(' ');
		if(spaceLast < 0){
			JOptionPane.showMessageDialog(null, "Not a valid command.\n No type selected.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}else{

			String toItem = cmd.substring(spaceLast+1);
			
			Station.getInstance().getSeatsFromType(toItem);
		}

	}

}