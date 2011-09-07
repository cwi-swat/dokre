package richrail.presentation;

import java.util.Enumeration;
import richrail.commands.NewTrainCommand;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextArea;
import richrail.commands.AddCommand;
import richrail.commands.DelTrainCommand;
import richrail.commands.DelWagonCommand;
import richrail.commands.ErrorCommand;
import richrail.commands.MissingArgumentCommand;
import richrail.commands.NewWagonCommand;
import richrail.commands.RemCommand;
import richrail.commands.ShowWagonsCommand;
import richrail.commands.TrainSeatsCommand;
import richrail.commands.WagonSeatCommand;
import richrail.domain.Wagon;

/**
 * Displays the output of the executed commands.
 */
public class CommandResponseTextArea extends JTextArea implements Observer {

	 /**
	  * Depending on the execute command a result must be display on the screen
	  * @param o
	  * @param arg
	  */
	 public void update(Observable o, Object arg) {
			if (arg instanceof NewTrainCommand) {
				 NewTrainCommand command = (NewTrainCommand) arg;
				 append("train " + command.getID() + " created");
			} else if (arg instanceof NewWagonCommand) {
				 NewWagonCommand command = (NewWagonCommand) arg;
				 append("wagon " + command.getWagonID() + " created");
				 if (command.getSeats() != null) {
						append(" with " + command.getSeats() + " seats");
				 }
			} else if (arg instanceof AddCommand) {
				 AddCommand command = (AddCommand) arg;
				 append("added wagon " + command.getWagonID() + " to train " + command.getTrainID());
			} else if (arg instanceof TrainSeatsCommand) {
				 TrainSeatsCommand command = (TrainSeatsCommand) arg;
				 append("number of seats in train " + command.getID() + ": " + command.getNumberOfSeats());
			} else if (arg instanceof WagonSeatCommand) {
				 WagonSeatCommand command = (WagonSeatCommand) arg;
				 append("number of seats in wagon " + command.getID() + ": " + command.getNumberOfSeats());
			} else if (arg instanceof DelTrainCommand) {
				 DelTrainCommand command = (DelTrainCommand) arg;
				 append("train " + command.getID() + " deleted");
			} else if (arg instanceof DelWagonCommand) {
				 DelWagonCommand command = (DelWagonCommand) arg;
				 append("train " + command.getID() + " deleted");
			} else if (arg instanceof RemCommand) {
				 RemCommand command = (RemCommand) arg;
				 append("wagon " + command.getWagonID() + " removed from train " + command.getTrainID());
			} else if (arg instanceof ShowWagonsCommand) {
				 ShowWagonsCommand command = (ShowWagonsCommand) arg;
				 Enumeration<Wagon> wagonIDs = command.getWagons().elements();
				 append("Available wagons (" + command.getWagons().size() + "):");
				 while (wagonIDs.hasMoreElements()) {
						Wagon wagon = wagonIDs.nextElement();
						append("\n" + wagon.getID() + " with " + wagon.getNumberOfSeats() + " seats");
				 }
			} else if (arg instanceof ErrorCommand) {
				 ErrorCommand command = (ErrorCommand) arg;
				 if (command.getMessage().equals("")) {
						append("Command not correct");
				 } else {
						System.out.println(command.getMessage());
						append(command.getMessage());
				 }
			} else if (arg instanceof MissingArgumentCommand) {
				 append("Missing arguments");
			} else {
				 append("You entered an unimplemented command");
			}
			append("\n");
	 }
}
