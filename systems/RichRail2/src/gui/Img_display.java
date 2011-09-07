package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.Station;
import domain.Train;
import domain.Wagon;


@SuppressWarnings("serial")
public class Img_display extends JPanel implements Observer{

	private int currentTrain=0;
	private int OFFSET=120;
	private int currentWagon = 0;
	private int TRAINLENGTH = 100;

	public Img_display(){
		
		init();
	}
	
	public void init(){
		this.setLayout(new FlowLayout());
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.setVisible(true);
		
	}

	@Override
	public void refreshData() {
		currentTrain = 0;
		Station s = Station.getInstance();
		ArrayList<Train> trains = s.getTrains();
		Graphics g = this.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (Train t : trains){
			drawtrain(t.getId());
			currentWagon = 1;
			for (Wagon w: t.getWagons()){
				drawWagon(w.getId());
				currentWagon++;
			}
			currentTrain++;
		}


	}

	private void drawWagon(String id) {
		Graphics g = this.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30+currentWagon*TRAINLENGTH,80+currentTrain*OFFSET,80,40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35+currentWagon*TRAINLENGTH, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.fillRoundRect(80+currentWagon*TRAINLENGTH, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.drawString(id,40+currentWagon*TRAINLENGTH,105+currentTrain*OFFSET);
		
	}

	public void drawtrain(String t) {
		this.setBackground(Color.WHITE);
		Graphics g = this.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30,80+currentTrain*OFFSET,80,40);
		g.fillRect(80,60+currentTrain*OFFSET,30,30);
		g.drawRoundRect(85, 40+currentTrain*OFFSET, 20, 20, 20, 20);
		g.drawRoundRect(85, currentTrain*OFFSET, 40, 40, 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.fillRoundRect(80, 120+currentTrain*OFFSET, 20, 20, 20, 20);
		g.drawString(t, 40, 105 + currentTrain*OFFSET);


	}

}