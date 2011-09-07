package Facade;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JScrollPane;

import Domain.Train;
import Domain.Wagon;

public class GraphicViewer extends JPanel implements TrainObserver{
	
	private int numberoftrains = 0;
	private int offset = 35;
	private int numberofwagons = 0;
	private JPanel GraphPanel;
	private TrainFacadeSingleton tfs = TrainFacadeSingleton.getTfs();
	
	public GraphicViewer() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		GraphPanel = new JPanel();
		scrollPane.setViewportView(GraphPanel);
		
	}
	public void drawTrain(Train t) {
		Graphics g = GraphPanel.getGraphics();
		g.setColor(Color.RED);
		g.fillRect(5,5+(numberoftrains*offset),30,30);
		g.setColor(Color.YELLOW);
		g.drawString(t.getNaam(), 15, 15+numberoftrains*offset);
		for(Wagon w: t.getWagons())	{
		
			drawWagon(w);
			numberofwagons++;
		}
		
	}
	public void drawWagon(Wagon w) {
		Graphics g = GraphPanel.getGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(40+(numberofwagons*offset), 5+numberoftrains*offset,30,30);
		g.setColor(Color.YELLOW);
		g.drawString(w.getNaam(), 47+numberofwagons*offset, 15+numberoftrains*offset);
	}
	@Override
	public void update() {
		Graphics g = GraphPanel.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0,0, 1000,1000);
		numberoftrains =0;
		numberofwagons =0;
		for(Train t: tfs.getTrains()) {
			drawTrain(t);
			numberofwagons=0;
			numberoftrains++;
		}
		
	}

}
