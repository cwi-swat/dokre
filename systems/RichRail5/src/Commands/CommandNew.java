package Commands;
import javax.swing.JOptionPane;

import TaskSpecific.*;

public class CommandNew extends Command {	
	private int firstInstanceOfSpaces;
	private int secondInstanceOfSpaces;
	private int thirdInstanceOfSpaces;
	private int fourthInstanceOfSpaces;
	//deze instance of spaces gelden allemaal voor na het woordje new
	//dus bij de zin: new wagontype wt1 20 is het:
	//                             |   |  |  3 instances (en dus niet 4)
	private String objectToCreate;
	private String nameOfObjectToCreate;
	private int idOfWagonToCreate;
	private String typeWagon;
	private int numSeats;
	@Override
	public void convertCommand(String input){

		//controleren of de command klopt
		if (input.matches("^[n]+[e]+[w]+\\s[t]+[r]+[a]+[i]+[n]\\s[a-zA-Z0-9]+[;]$") || input.matches("^[n]+[e]+[w]+\\s[w]+[a]+[g]+[o]+[n]\\s[0-9]+\\s[a-zA-Z0-9]*[;]$") || input.matches("^[n]+[e]+[w]+\\s[w]+[a]+[g]+[o]+[n]+[t]+[y]+[p]+[e]\\s[a-zA-Z0-9]+\\s[0-9]+[;]$")){
			firstInstanceOfSpaces = input.indexOf(" ", 4);
			objectToCreate = input.substring(4, firstInstanceOfSpaces);
			if (objectToCreate.equals("train")){
				//vanaf hier weten we dat er een trein moet worden toegevoegd
				secondInstanceOfSpaces = input.indexOf(";", firstInstanceOfSpaces + 1);
				nameOfObjectToCreate = input.substring(firstInstanceOfSpaces + 1, secondInstanceOfSpaces);
				cont.newTrain(nameOfObjectToCreate);
			
			}
			else if(objectToCreate.equals("wagon")){
				//vanaf hier weten we dat er een wagon moet worden toegevoegd
				secondInstanceOfSpaces = input.indexOf(" ", firstInstanceOfSpaces + 1);
				thirdInstanceOfSpaces = input.indexOf(";", secondInstanceOfSpaces + 1);
				idOfWagonToCreate = Integer.parseInt(input.substring(firstInstanceOfSpaces + 1, secondInstanceOfSpaces));
				typeWagon = input.substring(secondInstanceOfSpaces + 1, thirdInstanceOfSpaces);
				cont.newWagon(idOfWagonToCreate, typeWagon);
			}
			else if(objectToCreate.equals("wagontype")){
				secondInstanceOfSpaces = input.indexOf(" ", firstInstanceOfSpaces + 1);
				thirdInstanceOfSpaces = input.indexOf(";", secondInstanceOfSpaces + 1);
				nameOfObjectToCreate = input.substring(firstInstanceOfSpaces + 1, secondInstanceOfSpaces);
				numSeats = Integer.parseInt(input.substring(secondInstanceOfSpaces + 1, thirdInstanceOfSpaces));
				cont.newWagonType(nameOfObjectToCreate, numSeats);

			}
		}
		else{

			JOptionPane.showMessageDialog(null, "Unknown command");
		}
		
	
		
		
		
		

		//gemaakt door Rens
		//2 maart 20:25
		//Hier worden alle treinen, wagons en wagontypes herkend die gecreate moeten worden
		//de convertCommand functie converteerd de objecten zodat ie weet wat het is (trein of wagon etc)
		//daarna controleert hij de naam, en roept de converter aan, die het op zijn beurt
		//de controller aanroept met welk type er gemaakt moet worden	

//		if (input.substring(3, 4).equals(" ")){
//			//controle om te kijken of er wel een spatie na new komt	
//			firstInstanceOfSpaces = input.indexOf(" ", 4);
//			if(firstInstanceOfSpaces > 0){//ervoor zorgen dat er een 2e spatie inzit
//				objectToCreate = input.substring(4, firstInstanceOfSpaces);
//
//
//				//---------------
//				//trein toevoegen
//				//---------------
//
//				if (objectToCreate.equals("train")){
//					//vanaf hier weten we dat er een trein moet worden toegevoegd
//					secondInstanceOfSpaces = input.indexOf(";", firstInstanceOfSpaces + 1);
//					//controleren of de naam van het object 1 geheel is en niet meerdere woorden
//					thirdInstanceOfSpaces = input.indexOf(" ", firstInstanceOfSpaces + 1);
//					System.out.println("invoer command: " + input);
//					nameOfObjectToCreate = input.substring(firstInstanceOfSpaces + 1, secondInstanceOfSpaces);
//					if (thirdInstanceOfSpaces == -1){ //er mag geen third instance inzitten
//						//trein toevoegen
//						System.out.println("naam van de trein die we toevoegen: " + nameOfObjectToCreate);
//						cont.newTrain(nameOfObjectToCreate);
//					}
//					else{
//						//TODO: foutafhandeling
//						System.out.println("Error in command, naam van het object is meer dan 1 woord of het ; staat er niet gelijk achter");
//					}
//				}
//
//				//---------------
//				//wagon toevoegen
//				//---------------
//
//				else if(objectToCreate.equals("wagon")){
//					//vanaf hier weten we dat er een wagon moet worden toegevoegd
//					secondInstanceOfSpaces = input.indexOf(" ", firstInstanceOfSpaces + 1);
//					thirdInstanceOfSpaces = input.indexOf(";", secondInstanceOfSpaces + 1);
//					fourthInstanceOfSpaces = input.indexOf(" ", secondInstanceOfSpaces + 1);
//
//					try{
//						idOfWagonToCreate = Integer.parseInt(input.substring(firstInstanceOfSpaces + 1, secondInstanceOfSpaces));
//						typeWagon = input.substring(secondInstanceOfSpaces + 1, thirdInstanceOfSpaces);
//						if (fourthInstanceOfSpaces == -1){ //er mag geen third instance inzitten
//							//trein toevoegen
//							cont.newWagon(idOfWagonToCreate, typeWagon);
//						}
//						else{
//							//TODO: foutafhandeling
//							System.out.println("Error in command: teveel woorden");
//						}
//
//					}catch(Exception e){
//						//TODO: foutafhandeling
//						System.out.println("Fout in command");
//					}
//				}
//
//
//				//-------------------
//				//wagontype toevoegen
//				//-------------------
//
//				else if(objectToCreate.equals("wagontype")){
//					//vanaf hier weten we dat er een wagontype moet worden toegevoegd
//					secondInstanceOfSpaces = input.indexOf(" ", firstInstanceOfSpaces + 1);
//					thirdInstanceOfSpaces = input.indexOf(";", secondInstanceOfSpaces + 1);
//					fourthInstanceOfSpaces = input.indexOf(" ", secondInstanceOfSpaces + 1);
//
//					try{
//						nameOfObjectToCreate = input.substring(firstInstanceOfSpaces, secondInstanceOfSpaces);
//						try{
//							numSeats = Integer.parseInt(input.substring(secondInstanceOfSpaces + 1, thirdInstanceOfSpaces));
//							if (fourthInstanceOfSpaces == -1){ //er mag geen third instance inzitten
//								//trein toevoegen
//								cont.newWagonType(nameOfObjectToCreate, numSeats);
//
//							}
//							else{
//								//TODO: foutafhandeling
//								System.out.println("Error in command");
//							}
//						}catch(Exception e){
//							//TODO: foutafhandeling
//							System.out.println("Opgegeven aantal plaatsen kan niet geconverteerd worden, zorg dat u een getal invoert, en afsluit met ';'");
//						}
//					}catch(Exception e){
//						//TODO: foutafhandeling
//						System.out.println("Fout in command");
//					}
//				}
//				else{
//					//TODO: foutafhandeling
//					System.out.println("Error in command, '" + objectToCreate + "' niet herkend");
//				}
//			}
//			else{
//				//TODO: foutafhandeling
//				System.out.println("Error in command, geen spatie tussen de commands");
//			}
//		}
//
//		else{
//			//TODO: foutafhandeling
//			System.out.println("Error in command, geen spatie tussen de commands");
//		}
//	}
//
	}
}
