package com.mb.richrail.parser;

import com.mb.richrail.core.TrainService;
import com.mb.richrail.parser.commands.AddCommand;
import com.mb.richrail.parser.commands.Command;
import com.mb.richrail.parser.commands.DelCommand;
import com.mb.richrail.parser.commands.GetCommand;
import com.mb.richrail.parser.commands.NewCommand;
import com.mb.richrail.parser.commands.RemoveCommand;

public class CommandParser {
    
    private TrainService trainService;
    
    public CommandParser(TrainService trainService) {
        this.trainService = trainService;
    }
    
    public void parse(String command) {
        try {
            //Remove extra spaces
            command.trim();
            command = command.replaceAll("  ", " ");
            command = command.replaceAll("  ", " "); //Yes twice, to replace tripple spaces
            
            //Split the command on spaces
            String[] commandParts = command.split(" ");

            Command commandToExecute = null;

            if (commandParts.length > 0) {
                //New
                if (commandParts[0].equalsIgnoreCase("new")) {
                    commandToExecute = new NewCommand(commandParts, trainService);
                }
                //Add
                else if (commandParts[0].equalsIgnoreCase("add")) {
                    commandToExecute = new AddCommand(commandParts, trainService);
                    
                //Delete
                } else if (commandParts[0].equalsIgnoreCase("delete")) {
                    commandToExecute = new DelCommand(commandParts, trainService);
                }
                    
                //Remove
                else if (commandParts[0].equalsIgnoreCase("remove")) {
                    commandToExecute = new RemoveCommand(commandParts, trainService);
                //Getnumseats
                } else if (commandParts[0].equalsIgnoreCase("getnumseats")) {
                    commandToExecute = new GetCommand(commandParts, trainService);
                    
                //Command not found
                } else {
                    throw new InvalidCommandException(commandParts[0]);
                }

                commandToExecute.execute();
            }
        } catch(Exception e) {
            //e.printStackTrace();
            trainService.log(e.getMessage());
        }
    }
}
