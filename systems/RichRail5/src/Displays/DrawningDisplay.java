package Displays;

import java.awt.*;

import javax.swing.*;

import Domain.Train;
import Domain.Wagon;
import TaskSpecific.*;
public class DrawningDisplay extends JPanel implements Displayer {

	//Attributen
	private Controller converter = Controller.getInstance();
	private JLabel KopTekst = new JLabel("This is the drawning display");
	private JPanel drawPanel = new JPanel();
	private static int TRAINWIDTH = 80;
	private static int TRAINHEIGHT = 30;
	private static int TRAINSPACING = 5;
	private static int WAGONWIDTH = 80;
	private static int WAGONHEIGHT = 25;
	private static int WAGONSPACING = 5;
	private int counter = 0;
	private static int LABELOFFSET = 3;
	private JScrollPane editorScroll;
	private JScrollBar verticalScrollBar;
	private JScrollBar scrollerR;
	
     public DrawningDisplay(JPanel jp){
 //Onderlaag waar de tekenlaag op komt   	 

    	// JScrollPane scroller = new JScrollPane(panel);  
    	// this.getContentPane().add(scroller, BorderLayout.CENTER);  
			drawPanel.setBackground(Color.CYAN);
			drawPanel.add(KopTekst);
			jp.add(drawPanel);

     }

     
   //Constructor
     public void setController(Controller controller){
	 		//Dit is nodig voor de Displayer
	 		this.converter = controller;               
	        controller.addObserver( this );
	 	}
       
     public void paintComponent(Graphics g){
    	 super.paintComponent(g);
 		g.setColor(Color.CYAN);
 		g.fillRect (5, 5, 975, 265);
 		g.setColor(Color.CYAN);
 		g.drawRect (5, 5, 975, 265);
 		
 		
 		// draw trains when possible
 		
 		//if(con.Trains != null){
 			Point trainpointer = new Point(10+TRAINSPACING, 10+TRAINHEIGHT+TRAINSPACING);
 			
 			for(Train tr : converter.Trains){
 				int currentTrain = converter.Trains.indexOf(tr);
 				int seats = 0;
 				for (Wagon w: tr.getWagons()){
 					seats = seats + w.getType().getSeats();
 				}
 				int OFFSET = 100;
 				// draw engine
 				g.setColor(Color.LIGHT_GRAY);
 	 			g.fillRect(30,80+currentTrain*OFFSET,80,40);
 	 			g.fillRect(80,60+currentTrain*OFFSET,30,30);
 	 			g.drawRoundRect(85, 40+currentTrain*OFFSET, 20, 20, 20, 20);
 	 			//g.drawRoundRect(85, currentTrain*OFFSET+30, 30, 30, 30, 30);
 	 			g.setColor(Color.BLACK);
 	 			g.fillRoundRect(35, 120+currentTrain*OFFSET, 20, 20, 20, 20);
 	 			g.fillRoundRect(85, 120+currentTrain*OFFSET, 20, 20, 20, 20);
 	 			g.drawString(tr.getName()+"("+seats+")",40,105+currentTrain*OFFSET);
 				
 				// draw wagons
 				if(!tr.getWagons().isEmpty()){
 					
 					Point wagonpointer = new Point(trainpointer.x+TRAINWIDTH+WAGONSPACING, trainpointer.y);
 					int counter = 1;
 					for(Wagon wg : tr.getWagons()){
 						g.setColor(Color.LIGHT_GRAY);
 						g.fillRect(30+(counter*OFFSET),80+currentTrain*OFFSET,80,40);
 						g.setColor(Color.BLACK);
 						g.fillRoundRect(35+(counter*OFFSET), 120+currentTrain*OFFSET, 20, 20, 20, 20);
 						g.fillRoundRect(80+(counter*OFFSET), 120+currentTrain*OFFSET, 20, 20, 20, 20);
 						g.drawString(""+wg.getID()+" ("+wg.getType().getSeats()+")",40+(counter*OFFSET),105+currentTrain*OFFSET);
 						
 						wagonpointer.setLocation(wagonpointer.x + WAGONWIDTH + WAGONSPACING, wagonpointer.y);
 						counter++;
 					}
 				}
 				trainpointer.setLocation(trainpointer.x, trainpointer.y + TRAINSPACING + TRAINHEIGHT);
 			}
 	}
 	//Updaten
 	public void update(Observable subject) {
 		
 		this.repaint();
 		counter++;
 		}
}
