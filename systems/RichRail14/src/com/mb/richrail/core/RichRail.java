package com.mb.richrail.core;

import com.mb.richrail.gui.CommandController;
import com.mb.richrail.gui.RichRailWindow;
import com.mb.richrail.parser.CommandParser;

public class RichRail {

    public static void main(String[] args) {
        TrainService trainService = new TrainService();
        CommandParser commandParser = new CommandParser(trainService);
        CommandController commandController = new CommandController(commandParser);
        new RichRailWindow(trainService, trainService.getLogger(), commandController);
        new RichRailWindow(trainService, trainService.getLogger(), commandController);
    }

}
