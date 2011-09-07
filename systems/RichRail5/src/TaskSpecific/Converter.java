package TaskSpecific;

//import java.util.jar.Attributes.Name;

import javax.swing.JOptionPane;

import Commands.*;
//import Displays.DrawningDisplay;
//import Displays.LogDisplay;
//import Displays.TextDisplay;
//import TaskSpecific.*;


public class Converter {
	private Command cmdAdd = new CommandAdd();
	private Command cmdDelete = new CommandDelete();
	private Command cmdRemove = new CommandRemove();
	private Command cmdNew = new CommandNew();
	private Command cmdGet = new CommandGet();
	//private Controller controller = new Controller();
	private int firstInstanceOfSpaces;




	public Converter(){
		//deze constructor moet 1x aangeroepen worden zodat er altijd 1 converter is, en dus 1 controller
		//als we dit niet doen dan krijgen we verschillende trein, wagonnen etc objecten in verschillende
		//controller objecten en dat willen we niet, we willen altijd maar 1 controller!

	}

	public void convertCommand(String inputCommand){
		//gemaakt door Rens 2 maart 18:41
		//Deze methode kijkt of het een new, add, get, delete of remove command is,
		//en spreekt daarna de verantwoordelijke klasse aan.
		//wanneer deze bijvoorbeeld een new command onderscheidt, roept deze de .convertCommand van
		//de CommandNew klasse aan met dezelfde inputstring
		firstInstanceOfSpaces = inputCommand.indexOf(" ", 0);
		if(firstInstanceOfSpaces >= 0){//kijken of wel een spatie inzit om het eerste woord er uit te halen
			if (inputCommand.substring(0, firstInstanceOfSpaces).equals("new")){
				cmdNew.convertCommand(inputCommand);
			}
			else if(inputCommand.substring(0, firstInstanceOfSpaces).equals("add")){
				cmdAdd.convertCommand(inputCommand);
			}
			else if(inputCommand.substring(0, firstInstanceOfSpaces).equals("delete")){
				cmdDelete.convertCommand(inputCommand);
			}
			else if(inputCommand.substring(0, firstInstanceOfSpaces).equals("getnumseats")){
				cmdGet.convertCommand(inputCommand);
			}
			else if(inputCommand.substring(0, firstInstanceOfSpaces).equals("remove")){
				cmdRemove.convertCommand(inputCommand);
			}
			else{
				System.out.println("Unknown command");
				JOptionPane.showMessageDialog(null, "Unknown command");
			}
		}
		else{
			System.out.println("Unknown command: " + firstInstanceOfSpaces);
			JOptionPane.showMessageDialog(null, "Unknown command");
		}
	}

//	public void addTrain(String nm){
//		controller.newTrain(nm);
//		System.out.println("De grootte van de array is: " + controller.Trains.size());
//	}
//
//	public void addWagonType(String nameOfObjectToCreate, int numSeats) {
//		controller.newWagonType(nameOfObjectToCreate, numSeats);
//
//	}
//
//	public void addWagon(int idOfWagonToCreate, String typeWagon) {
//		controller.newWagon(idOfWagonToCreate, typeWagon);
//
//	}
//	public void deleteWagon(int idOfWagonToCreate) {
//		controller.deleteWagon(idOfWagonToCreate);
//
//	}
//	public void deleteWagonType(String nameOfObjectToCreate) {
//		controller.deleteWagonType(nameOfObjectToCreate);
//
//	}
//	public void deleteTrain(String nm){
//		controller.deleteTrain(nm);
//	}
//	public void getTrainseats(String nm){
//		controller.getSeats(nm);
//	}
//	public void getWagonseats(int idOfWagonToCreate) {
//		controller.getSeatsFromWagon(idOfWagonToCreate);
//
//	}

}
