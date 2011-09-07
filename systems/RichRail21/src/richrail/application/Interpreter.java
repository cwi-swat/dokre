package richrail.application;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import richrail.commands.*;
import richrail.exceptions.InvalidIDException;

/**
 *
 */
public class Interpreter {

	 private String commandStr;

	 /**
	  * Initializes the interpreter
	  * @param commandStr
	  */
	 public Interpreter(String commandStr) {
			this.commandStr = commandStr;
	 }

	 /**
		* Analyzes the command and derives a child of the Command class
		* @return
		*/
	 public Command interpret() {
			LinkedList<String> commandList = new LinkedList<String>();
			for (String token : commandStr.split("\\s")) {
				 commandList.add(token);
			}

			Command command = null;
			try {
				 String word = commandList.pop();
				 if (word.equals("new")) {
						word = commandList.pollFirst();
						if (word.equals("train")) {
							 command = new NewTrainCommand(commandList.pop());
						} else if (word.equals("wagon")) {
							 String wagonID = commandList.pop();
							 String numseatsStr = commandList.pollFirst();//pollFirst, omdat deze null terug geeft bij een lege lijst ipv een exception
							 if (numseatsStr != null && numseatsStr.equals("numseats")) {
									int numseats = Integer.parseInt(commandList.pop());
									command = new NewWagonCommand(wagonID, numseats);
							 } else {
									command = new NewWagonCommand(wagonID);
							 }
						}
				 } else if (word.equals("add")) {
						String wagonID = commandList.pop();
						if (commandList.pop().equals("to")) {
							 command = new AddCommand(wagonID, commandList.pop());
						}
				 } else if (word.equals("getnumseats")) {
						String type = commandList.pop();
						if (type.equals("wagon")) {
							 command = new WagonSeatCommand(commandList.pop());
						} else if (type.equals("train")) {
							 command = new TrainSeatsCommand(commandList.pop());
						}
				 } else if (word.equals("delete")) {
						String type = commandList.pop();
						if (type.equals("train")) {
							 command = new DelTrainCommand(commandList.pop());
						} else if (type.equals("wagon")) {
							 command = new DelWagonCommand(commandList.pop());
						}
				 } else if (word.equals("remove")) {
						String wagonId = commandList.pop();
						if (commandList.pop().equals("from")) {
							 command = new RemCommand(wagonId, commandList.pop());
						}
				 } else if (word.equals("show")) {
						command = new ShowWagonsCommand();
				 } else {
						throw new Exception();
				 }
			} catch (NoSuchElementException rte) {
				 command = new MissingArgumentCommand();
			} catch (Exception e) {
				 command = new ErrorCommand();
			}

			try {
				 command.setCommandString(commandStr);
			} catch (NullPointerException npe) {
				 command = new ErrorCommand();
				 command.setCommandString(commandStr);
			}
			return command;

	 }
}
