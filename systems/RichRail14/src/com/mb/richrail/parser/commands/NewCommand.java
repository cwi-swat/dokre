package com.mb.richrail.parser.commands;

import com.mb.richrail.core.TrainService;
import com.mb.richrail.parser.SyntaxException;

public class NewCommand implements Command {
    private String[] commandLine;
    private TrainService service;

    public NewCommand(String[] commandLine, TrainService service){
        this.commandLine = commandLine;
        this.service = service;
    }

    @Override
    public void execute() throws Exception {        
        if (commandLine.length == 3 && commandLine[1].equalsIgnoreCase("train")) {
            service.addTrain(commandLine[2].toString());
        } else if (commandLine.length == 3 && commandLine[1].equalsIgnoreCase("wagon")) {
            service.addWagonType(commandLine[2]);
        } else if (commandLine.length == 5 && commandLine[1].equalsIgnoreCase("wagon")) {
            try {
                int i = Integer.parseInt(commandLine[4]);
                service.addWagonType(commandLine[2], i);
            } catch (NumberFormatException e) {
                throw new Exception(commandLine[4] + " is not a valid number of seats");
            }
        } else {
            throw new SyntaxException("new train/wagon [name] (numseats [seats])");
        }
    }

}
