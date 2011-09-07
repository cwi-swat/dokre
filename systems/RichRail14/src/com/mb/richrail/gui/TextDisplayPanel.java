package com.mb.richrail.gui;

import com.mb.richrail.core.TrainService;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextArea;

public class TextDisplayPanel extends JTextArea implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof TrainService) {
            TrainService trainService = (TrainService) o;
            ArrayList<String> trains = trainService.getTrains();
            ArrayList<String> wagonTypes = trainService.getWagonTypes();
            setText("Wagons:\n");
            for(String wagonType : wagonTypes) {
                append("(" + wagonType + ":" + trainService.getNumOfSeatsWagonType(wagonType) + ")");
            }
            append("\nTrains:\n");
            for (String train : trains) {
                append("(" + train + ")");
                ArrayList<String> wagons = trainService.getWagons(train);
                for (String wagon : wagons) {
                    append("-(" + wagon + ")"); 
                }
                append("\n");
            }
        }
    }

}
