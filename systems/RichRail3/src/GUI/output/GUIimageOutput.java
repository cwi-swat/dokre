package GUI.output;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import datahandler.DataHandler;
import domain.Train;
import domain.Wagon;

public class GUIimageOutput extends GUIoutput {

	private static final long serialVersionUID = 4945660080806417922L;

	private ArrayList<Train> allTrains;

	public GUIimageOutput(){
		super();
		this.setBackground(Color.RED);
		this.setSize(400,300);
	}

	@Override
	public void notifyOutput(DataHandler handle)
	{
		allTrains = handle.getAllTrains();
		this.paintComponents(this.getGraphics());
		this.repaint();
		this.invalidate();
	}

	public void paintComponent(Graphics g)
	{		
		this.setSize(400,300);
		super.paintComponent(g);
		if(allTrains != null && allTrains.size() > 0)
		{
			int offsetY = 0;
			int offsetX = 120;
			
			for(Train t : allTrains)
			{
				offsetX = 0;
				drawTrain(g,t,offsetY);
				offsetX = 120;
				for(Wagon w : t.getWagons())
				{
					drawWagon(g,w,offsetX,offsetY);
					offsetX += 120;
				}
				offsetY += 120;
			}
		}		
	}
	
	/**
	 * This method draws trains
	 * @param g Graphics from the panel
	 * @param t Train
	 * @param offsetY Position on the Y-axis
	 */
	public void drawTrain(Graphics g, Train t, int offsetY) 
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(40, 65+offsetY, 15, 15);
		g.fillRect(30,80+offsetY,80,40);
		g.fillRect(80,80+offsetY,30,30);
		g.fillRect(80,60+offsetY,30,30);
		g.drawRoundRect(30, 40+offsetY, 20, 20, 20, 20);
		g.drawRoundRect(40, offsetY, 40, 40, 40, 40);	
		g.setColor(Color.BLACK);
		g.fillRect(80, 50+offsetY, 30,5);
		g.fillRect(75, 55+offsetY, 40,5);
		g.fillRoundRect(35, 120+offsetY, 20, 20, 20, 20);
		g.fillRoundRect(80, 120+offsetY, 20, 20, 20, 20);
		g.drawString(t.getName(),40,105+offsetY);	
	}

	/**
	 * This method draws wagons
	 * @param g Graphics from the panel
	 * @param wagon Wagon
	 * @param offsetX Position on the X-axis
	 * @param offsetY Position on the Y-axis
	 */
	public void drawWagon(Graphics g, Wagon wagon, int offsetX, int offsetY) 
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30+offsetX,80+offsetY,80,40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35+offsetX, 120+offsetY, 20, 20, 20, 20);
		g.fillRoundRect(80+offsetX, 120+offsetY, 20, 20, 20, 20);
		g.drawString(wagon.getType().getName(),40+offsetX,105+offsetY);
	}
}
