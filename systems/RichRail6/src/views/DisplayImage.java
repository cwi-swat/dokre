package views;

import java.util.ArrayList;
import java.awt.*;

import javax.swing.JPanel;

import models.Storage;
import models.Train;
import models.Wagon;

public class DisplayImage extends Display implements Observer{
	private static final long serialVersionUID = 1L;
	private static JPanel drawPanel;
	private int currentNumberOfWagons = 1;
	private int currentTrain = 0;
//	private int OFFSET = 150;
//	private int TRAINLENGTH = 100;
	private int OFFSET = 38;
	private int TRAINLENGTH = 25;
	
	public DisplayImage() {
		super();
		drawPanel = new JPanel();
		drawPanel.setBackground(Color.WHITE);
	}
	
	public JPanel getDrawPanel() {
		return drawPanel;
	}
	public void setDrawPanel(JPanel drawPanel) {
		this.drawPanel = drawPanel;
	}
	
	@Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawPanel = new JPanel();
        for (Train at : trains){
        	drawTrain(at.getName(), g);
        	for (Wagon w : at.getWagons()){
        		drawWagon(w.getWagon_type().toString(), g);
        		currentNumberOfWagons++;
        	}
        	currentNumberOfWagons = 1;
        	currentTrain++;
        }
    }

	public void drawTrain(String train, Graphics g) 
	{
		if (train != "")
		{
//			g.setColor(Color.LIGHT_GRAY);
//			g.fillRect(30,80+currentTrain*OFFSET,80,40);
//			g.fillRect(80,60+currentTrain*OFFSET,30,30);
//			g.drawRoundRect(85, 40+currentTrain*OFFSET, 20, 20, 20, 20);
//			g.drawRoundRect(85, currentTrain*OFFSET, 40, 40, 40, 40);
//			g.setColor(Color.BLACK);
//			g.fillRoundRect(35, 120+currentTrain*OFFSET, 20, 20, 20, 20);
//			g.fillRoundRect(80, 120+currentTrain*OFFSET, 20, 20, 20, 20);
//			if (train.length()>7) 
//				g.drawString(train.substring(0, 7) + "...",40,105+currentTrain*OFFSET);
//			else
//				g.drawString(train,40,105+currentTrain*OFFSET);
		
			
			g.setFont(new Font(g.getFont().getName() ,g.getFont().getStyle(), 10));
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(8,20+currentTrain*OFFSET,20,10);
			g.fillRect(20,15+currentTrain*OFFSET,8,8);
			g.drawRoundRect(21, 10+currentTrain*OFFSET, 5, 5, 5, 5);
			g.drawRoundRect(21, currentTrain*OFFSET, 10, 10, 10, 10);
			g.setColor(Color.BLACK);
			g.fillRoundRect(9, 30+currentTrain*OFFSET, 5, 5, 5, 5);
			g.fillRoundRect(20, 30+currentTrain*OFFSET, 5, 5, 5, 5);
			if (train.length()>7) 
				g.drawString(train.substring(0, 7) + "...",10,26+currentTrain*OFFSET);
			else
				g.drawString(train,10,26+currentTrain*OFFSET);
		}
    }
	
	public void drawWagon(String wagon, Graphics g) 
	{
//		g.setColor(Color.LIGHT_GRAY);
//		g.fillRect(30+currentNumberOfWagons*TRAINLENGTH,80+currentTrain*OFFSET,80,40);
//		g.setColor(Color.BLACK);
//		g.fillRoundRect(35+currentNumberOfWagons*TRAINLENGTH, 120+currentTrain*OFFSET, 20, 20, 20, 20);
//		g.fillRoundRect(80+currentNumberOfWagons*TRAINLENGTH, 120+currentTrain*OFFSET, 20, 20, 20, 20);
//		g.drawString(wagon,40+currentNumberOfWagons*TRAINLENGTH,105+currentTrain*OFFSET);
		
		g.setFont(new Font(g.getFont().getName() ,g.getFont().getStyle(), 10));
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(8+currentNumberOfWagons*TRAINLENGTH,20+currentTrain*OFFSET,20,10);
		g.setColor(Color.BLACK);
		g.fillRoundRect(9+currentNumberOfWagons*TRAINLENGTH, 30+currentTrain*OFFSET, 5, 5, 5, 5);
		g.fillRoundRect(20+currentNumberOfWagons*TRAINLENGTH, 30+currentTrain*OFFSET, 5, 5, 5, 5);
		g.drawString(wagon,10+currentNumberOfWagons*TRAINLENGTH,26+currentTrain*OFFSET);
    }
	
	public void repaint(ArrayList<Train> newTrains){
		trains = newTrains;
		drawPanel.repaint();
		repaint();
		currentNumberOfWagons = 1;
		currentTrain = 0;
//		OFFSET = 150;
//		TRAINLENGTH = 100;
		OFFSET = 38;
		TRAINLENGTH = 25;
	}
	
	public void refresh(){
		repaint(Storage.get().getTrains());
	}
}
