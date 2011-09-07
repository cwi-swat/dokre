package com.mb.richrail.gui;

import com.mb.richrail.core.TrainService;
import com.mb.richrail.data.Train;
import com.mb.richrail.data.Wagon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

public class GraphicDisplayPanel extends JPanel implements Observer {

    private TrainService trainService;
    
    public GraphicDisplayPanel() {
        setBackground(Color.WHITE);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof TrainService) {
            trainService = (TrainService) o;
            ArrayList<String> trains = trainService.getTrains();
            int longest = 0;
            for(String train : trains) {
                int items = trainService.getWagons(train).size();
                if (1 + items > longest) {
                    longest = 1 + items;
                }
            }
            setPreferredSize(new Dimension(10 + longest * 80, trains.size() * 80 + 20));
            revalidate();
            repaint();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (trainService != null) {
            int i = 1;
            ArrayList<String> trains = trainService.getTrains();
            for (String train : trains) {
                int baseLine = i * 80 + 10;
                g.setColor(Color.LIGHT_GRAY);
                //body
                g.fillRect(10, baseLine-46, 70, 30);
                //cabin
                g.fillRect(55, baseLine-60, 25, 30);
                //smoke
                g.drawOval(60, baseLine-77, 15,15);
                g.drawOval(70, baseLine-90, 20,20);
                
                g.setColor(Color.BLACK);
                //wheels
                g.fillOval(15, baseLine-16, 16, 16);
                g.fillOval(59, baseLine-16, 16, 16);
                //name
                g.drawString(train, 15, baseLine-25);
                
                //Wagons
                int j = 1;
                ArrayList<String> wagons = trainService.getWagons(train);
                for (String wagon : wagons) {
                    int baseVLine = j * 80 + 10;
                    g.setColor(Color.LIGHT_GRAY);
                    //body
                    g.fillRect(baseVLine, baseLine-46, 70, 30);
                    
                    g.setColor(Color.BLACK);
                    //wheels
                    g.fillOval(baseVLine + 5, baseLine-16, 16, 16);
                    g.fillOval(baseVLine + 49, baseLine-16, 16, 16);
                    //name
                    g.drawString(wagon, baseVLine + 5, baseLine-25);
                    j++;
                }
                
                i++;
            }
        }
    }

}
