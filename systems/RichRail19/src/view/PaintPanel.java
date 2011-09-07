package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PaintPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int currentNumberOfWagons;
	private int currentTrain = -1;
	private int OFFSET = 100;
	private int TRAINLENGTH = 100;
	
	public PaintPanel(){
		setBackground(Color.white);
	}
	
	public void paintComponent(Graphics g){
		super.paint(g);
	}

	public void clear(){
		repaint();
		setBackground(Color.white);
	}
	
	public void drawTrain(String train) 
	{
		currentTrain++;
		Graphics g = getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30,80+currentTrain*OFFSET,80,40);
		g.fillRect(80,60+currentTrain*OFFSET,30,30);
		g.drawRoundRect(85, 40+currentTrain*OFFSET, 20, 20, 20, 20);
		g.drawRoundRect(85, currentTrain*OFFSET, 40, 40, 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.fillRoundRect(80, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.drawString(train,40,105+currentTrain*OFFSET);
		
    }
	
	public void drawWagon(String wagon) 
	{
		
		Graphics g = getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30+currentNumberOfWagons*TRAINLENGTH,80+currentTrain*OFFSET,80,40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35+currentNumberOfWagons*TRAINLENGTH, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.fillRoundRect(80+currentNumberOfWagons*TRAINLENGTH, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.drawString(wagon,40+currentNumberOfWagons*TRAINLENGTH,105+currentTrain*OFFSET);
    }
}
