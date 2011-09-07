package Commands;
import javax.swing.JOptionPane;

import TaskSpecific.*;

public class CommandGet extends Command{
	private int firstInstanceOfSpaces;
	private int secondInstanceOfSpaces;
	private int thirdInstanceOfSpaces;
	private int fourthInstanceOfSpaces;
	//deze instance of spaces gelden allemaal voor na het woordje new
	//dus bij de zin: new wagontype wt1 20 is het:
	//                             |   |  |  3 instances (en dus niet 4)
	private String objectToEdit;
	private String nameOfobjectToEdit;
	private int idOfWagonToCreate;
	private String typeWagon;
	private int numSeats;

	@Override
	public void convertCommand(String input) {

		if (input.matches("^[g]+[e]+[t]+[n]+[u]+[m]+[s]+[e]+[a]+[t]+[s]\\s[t]+[r]+[a]+[i]+[n]+\\s[a-zA-Z0-9]+[;]$") || input.matches("^[g]+[e]+[t]+[n]+[u]+[m]+[s]+[e]+[a]+[t]+[s]+\\s[w]+[a]+[g]+[o]+[n]\\s[0-9]+[;]") ){

			firstInstanceOfSpaces = input.indexOf(" ", 12);
			objectToEdit = input.substring(12, firstInstanceOfSpaces);
			if (objectToEdit.equals("train")){
				//vanaf hier weten we dat er een trein moet worden toegevoegd
				secondInstanceOfSpaces = input.indexOf(";", firstInstanceOfSpaces + 1);
				nameOfobjectToEdit = input.substring(firstInstanceOfSpaces + 1, secondInstanceOfSpaces);

				cont.getSeats(nameOfobjectToEdit);

			}
			else if(objectToEdit.equals("wagon")){
				//vanaf hier weten we dat er een wagon moet worden toegevoegd
				secondInstanceOfSpaces = input.indexOf(";", firstInstanceOfSpaces + 1);
				idOfWagonToCreate = Integer.parseInt(input.substring(firstInstanceOfSpaces + 1, secondInstanceOfSpaces));
				cont.getSeatsFromWagon(idOfWagonToCreate);

			}
		}
		else{

			JOptionPane.showMessageDialog(null, "Unknown command");


		}
	}




}
