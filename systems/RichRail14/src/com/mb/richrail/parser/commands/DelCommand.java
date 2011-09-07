package com.mb.richrail.parser.commands;

import com.mb.richrail.core.TrainService;
import com.mb.richrail.parser.SyntaxException;

public class DelCommand implements Command {
    private String[] commandLine;
    private TrainService service;

    public DelCommand(String[] commandLine, TrainService service){
        this.commandLine = commandLine;
        this.service = service;
    }

    @Override
    public void execute() throws Exception {
        if (commandLine.length == 3 && commandLine[1].equalsIgnoreCase("train")) {
            service.deleteTrain(commandLine[2]);
        } else if (commandLine.length == 3 && commandLine[1].equalsIgnoreCase("wagon")) {
            service.deleteWagonType(commandLine[2]);
        } else {
            throw new SyntaxException("delete train/wagon [name]");

        }
    }
}
