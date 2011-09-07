package com.mb.richrail.parser.commands;

import com.mb.richrail.core.TrainService;
import com.mb.richrail.parser.SyntaxException;

public class AddCommand implements Command {
    private String[] commandLine;
    private TrainService service;

    public AddCommand(String[] commandLine, TrainService service){
        this.commandLine = commandLine;
        this.service = service;
    }

    
    @Override
    public void execute() throws Exception {
        if (commandLine.length == 4 && commandLine[2].equalsIgnoreCase("to")) {
            service.addWagonToTrain(commandLine[3], commandLine[1]);
        } else {
            throw new SyntaxException("add [wagontype] to [train]");
        }
    }

}
