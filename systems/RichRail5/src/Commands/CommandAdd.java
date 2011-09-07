package Commands;
import javax.swing.JOptionPane;

import TaskSpecific.*;
public class CommandAdd extends Command {

	private int firstInstanceOfSpaces;
	private int secondInstanceOfSpaces;
	private int puntkomma;
	
	private String wagonID;
	private String trainName;
	
	@Override
	public void convertCommand(String input) {
		if (input.matches("^[a]+[d]+[d]+\\s[0-9]+\\s[t]+[o]+\\s[a-zA-Z0-9]+[;]$")){
			firstInstanceOfSpaces = input.indexOf(" ", 4);
			secondInstanceOfSpaces = input.indexOf(" ", firstInstanceOfSpaces + 1);
			puntkomma = input.indexOf(";", secondInstanceOfSpaces + 1);
			
			wagonID = input.substring(4, firstInstanceOfSpaces);
			trainName = input.substring(secondInstanceOfSpaces + 1, puntkomma);
			//System.out.println("wagonid: "+ wagonID);
			//System.out.println("trainname: "+ trainName);
			
			cont.add(trainName, Integer.parseInt(wagonID));
		}
		else{
			JOptionPane.showMessageDialog(null, "Unknown command");
		}
		
	}



}
