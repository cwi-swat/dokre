package Commands;
import javax.swing.JOptionPane;

import TaskSpecific.*;

public class CommandRemove extends Command{

	private int firstInstanceOfSpaces;
	private int secondInstanceOfSpaces;
	private int puntkomma;
	
	private String wagonID;
	private String trainName;
	
	
	@Override
	public void convertCommand(String input) {
		if (input.matches("^[r]+[e]+[m]+[o]+[v]+[e]+\\s[0-9]+\\s[f]+[r]+[o]+[m]\\s[a-zA-Z0-9]+[;]$")){
			firstInstanceOfSpaces = input.indexOf(" ", 7);
			secondInstanceOfSpaces = input.indexOf(" ", firstInstanceOfSpaces + 1);
			puntkomma = input.indexOf(";", secondInstanceOfSpaces + 1);
			
			wagonID = input.substring(7, firstInstanceOfSpaces);
			trainName = input.substring(secondInstanceOfSpaces + 1, puntkomma);
			
			cont.removeWagon(trainName, Integer.parseInt(wagonID));
		}
		else{
			JOptionPane.showMessageDialog(null, "Unknown command");
		}
	}
}
