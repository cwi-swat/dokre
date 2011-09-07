package Commands;
import javax.swing.JOptionPane;

import TaskSpecific.*;

public class CommandDelete extends Command{
	//[delete]+\s[train]+\s[a-zA-Z]*[;]$
	
	private int firstInstanceOfSpaces;
	private int secondInstanceOfSpaces;
	//private int thirdInstanceOfSpaces;
	//private int fourthInstanceOfSpaces;
	//deze instance of spaces gelden allemaal voor na het woordje new
	//dus bij de zin: new wagontype wt1 20 is het:
	//                             |   |  |  3 instances (en dus niet 4)
	private String objectToCreate;
	private String nameOfObjectToCreate;
	private int idOfWagonToCreate;
	//private String typeWagon;
	//private int numSeats;


	@Override
	public void convertCommand(String input) {
		if (input.matches("^[d]+[e]+[l]+[e]+[t]+[e]\\s[t]+[r]+[a]+[i]+[n]\\s[a-zA-Z0-9]+[;]$") || input.matches("^[d]+[e]+[l]+[e]+[t]+[e]\\s[w]+[a]+[g]+[o]+[n]\\s[0-9]+[;]$") || input.matches("^[d]+[e]+[l]+[e]+[t]+[e]\\s[w]+[a]+[g]+[o]+[n]+[t]+[y]+[p]+[e]\\s[a-zA-Z0-9]+[;]$")){
			firstInstanceOfSpaces = input.indexOf(" ", 7);
			objectToCreate = input.substring(7, firstInstanceOfSpaces);
			if (objectToCreate.equals("train")){
				//vanaf hier weten we dat er een trein moet worden toegevoegd
				secondInstanceOfSpaces = input.indexOf(";", firstInstanceOfSpaces + 1);
				nameOfObjectToCreate = input.substring(firstInstanceOfSpaces + 1, secondInstanceOfSpaces);
				//System.out.println("naam van de trein die we toevoegen: " + nameOfObjectToCreate);
				cont.deleteTrain(nameOfObjectToCreate);
			
			}
			else if(objectToCreate.equals("wagon")){
				//vanaf hier weten we dat er een wagon moet worden toegevoegd
				secondInstanceOfSpaces = input.indexOf(";", firstInstanceOfSpaces + 1);
				//thirdInstanceOfSpaces = input.indexOf(";", secondInstanceOfSpaces + 1);
				idOfWagonToCreate = Integer.parseInt(input.substring(firstInstanceOfSpaces + 1, secondInstanceOfSpaces));
				//typeWagon = input.substring(secondInstanceOfSpaces + 1, thirdInstanceOfSpaces);
				cont.deleteWagon(idOfWagonToCreate);
			}
			else if(objectToCreate.equals("wagontype")){
				secondInstanceOfSpaces = input.indexOf(";", firstInstanceOfSpaces + 1);
				//thirdInstanceOfSpaces = input.indexOf(";", secondInstanceOfSpaces + 1);
				nameOfObjectToCreate = input.substring(firstInstanceOfSpaces +1, secondInstanceOfSpaces);
				//numSeats = Integer.parseInt(input.substring(secondInstanceOfSpaces + 1, thirdInstanceOfSpaces));
				cont.deleteWagonType(nameOfObjectToCreate);
			}
		}
		else{
			System.out.println("Unknown command");
			JOptionPane.showMessageDialog(null, "Unknown command");
		}
	}

}
