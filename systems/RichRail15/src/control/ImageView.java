package control;

 
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.UI;

import model.Train;
import model.Wagon;

public class ImageView extends JFrame implements Observer{

	private JPanel drawingPanel;
	//private JPanel drawPanel;
	//private Graphics g;
	
 
	
	private RichRailFacade rrf;
	//private HashMap numberOfWagons;
	private int currentNumberOfWagons;
	private int currentTrain = -1;
	private int OFFSET = 100;
	private int TRAINLENGTH = 100;
	private Graphics g ;
	
	private ArrayList<Train> trains;
	private ArrayList<Wagon> wagons;
	
	
	public ImageView(RichRailFacade rrfc, JPanel dwp){
		drawingPanel = dwp;
		rrf = rrfc;
	
	 
		//drawPanel = new JPanel();
		//drawPanel.setBackground(Color.GREEN);
		//drawingPanel.add(drawPanel,BorderLayout.CENTER);
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		//Graphics g = drawPanel.getGraphics();
		
		if (arg1 instanceof Train) {
			
			 
			trains = rrf.getTrains();
		    for (Train train: trains){

		    	drawTrain(train.getId());
		    }
		 
		 } 
		
		if (arg1 instanceof Wagon) {
			
			 
			wagons = rrf.getWagons();
		    for (Wagon wagon: wagons){

		    	drawWagon(wagon.getId());
		    }
		}
		 
	 }
	
	
	public void drawTrain(String train) 
	{
		if (train != "")
		{
			
			//drawingPanel = intface.drawingPanel;
			g =  drawingPanel.getGraphics();
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(130,100+currentTrain*OFFSET,80,40);
			g.fillRect(180,80+currentTrain*OFFSET,30,30);
			g.drawRoundRect(185, 60+currentTrain*OFFSET, 20, 20, 20, 20);
			g.drawRoundRect(185, currentTrain*OFFSET, 40, 40, 40, 40);
			g.setColor(Color.BLACK);
			g.fillRoundRect(135, 140+currentTrain*OFFSET, 20, 20, 20, 20);
			g.fillRoundRect(180, 140+currentTrain*OFFSET, 20, 20, 20, 20);
			g.drawString(train,140,125+currentTrain*OFFSET);
		}
    }
	
	public void drawWagon(String wagon) 
	{
		//drawingPanel = intface.drawingPanel;
		//Graphics g = intface.drawingPanel.getGraphics();
		g =  drawingPanel.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(130+currentNumberOfWagons*TRAINLENGTH,120+currentTrain*OFFSET,80,40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(135+currentNumberOfWagons*TRAINLENGTH, 160+currentTrain*OFFSET, 20, 20, 20, 20);
		g.fillRoundRect(180+currentNumberOfWagons*TRAINLENGTH, 160+currentTrain*OFFSET, 20, 20, 20, 20);
		g.drawString(wagon,140+currentNumberOfWagons*TRAINLENGTH,145+currentTrain*OFFSET);
    }


}
