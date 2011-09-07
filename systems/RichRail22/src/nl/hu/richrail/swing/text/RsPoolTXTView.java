package nl.hu.richrail.swing.text;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import nl.hu.richrail.swing.JTrainComponent;
import nl.hu.richrail.swing.RsPoolView;
import nl.hu.richrail.swing.JTrain;
import nl.hu.richrail.swing.JTrainSegment;
import nl.hu.richrail.trains.Car;
import nl.hu.richrail.trains.Engine;

public class RsPoolTXTView extends RsPoolView implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel drawPane = new JPanel();
	private TrainTXT roolstockPanel = new TrainTXT();
	
	public RsPoolTXTView()
	{
		super("Text view on the RollingStock pool", true, true, true);
		drawPane.setBackground(Color.white);
		drawPane.setSize(new Dimension(500,400));
		drawPane.setLayout(new GridLayout(3,1));
		
		JLabel jlWaggons = new JLabel("Wagons");
		JLabel jltrains = new JLabel("Train");
	
		drawPane.add(jlWaggons);
		
		drawPane.add(roolstockPanel);
		drawPane.add(jltrains);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(drawPane,BorderLayout.CENTER);
		
		//Test Button
		JButton test = new JButton("test");
		test.addActionListener(this);
		getContentPane().add(test,BorderLayout.SOUTH);
		
		setSize(new Dimension(640,480));
		
		setVisible(true);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		nl.hu.richrail.trains.RollingStock x = new Car("New1");
		nl.hu.richrail.trains.RollingStockPool.getInstance().addRollingStock(x);
		 x = new Car("New2");
		nl.hu.richrail.trains.RollingStockPool.getInstance().addRollingStock(x);
		 x = new Car("New3");
		nl.hu.richrail.trains.RollingStockPool.getInstance().addRollingStock(x);
		nl.hu.richrail.trains.RollingStockPool.getInstance().addRollingStock(new Engine("New2"));
		nl.hu.richrail.trains.RollingStockPool.getInstance().removeRollingStock(x);

	}

	@Override
	public void add(JTrainComponent train) {
		// TODO Auto-generated method stub
		if(train instanceof TrainTXT)
		{
			GridLayout g = (GridLayout)drawPane.getLayout();
			g.setRows(g.getRows() +1);
			drawPane.add((Component) train);
		}else{
			System.out.println("roolstockPanel.add((JTrainSegment) train)");
			((Waggon)train).showSeats();
			roolstockPanel.add((Component) train);
		}
		drawPane.invalidate();
		drawPane.validate();
	}


	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		Component cc = null;
			for(Component c : drawPane.getComponents())
				if(c instanceof JTrainComponent)
					if(((JTrainComponent)c).getID().equals(id))
					{
						drawPane.remove(c);
						cc = c;
					}
		if(cc ==null)
			roolstockPanel.remove(id);

		drawPane.invalidate();
		drawPane.validate();
	}


}
