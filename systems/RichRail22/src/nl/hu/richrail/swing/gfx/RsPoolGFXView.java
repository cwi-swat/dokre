package nl.hu.richrail.swing.gfx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import nl.hu.richrail.swing.JTrainComponent;
import nl.hu.richrail.swing.RsPoolView;
import nl.hu.richrail.swing.JTrain;
import nl.hu.richrail.swing.JTrainSegment;
import nl.hu.richrail.trains.Car;
import nl.hu.richrail.trains.Engine;

public class RsPoolGFXView extends RsPoolView implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel drawPane = new JPanel();
	
	public RsPoolGFXView()
	{
		super("Graphical view on the RollingStock pool", true, true, true);
		drawPane.setBackground(Color.white);
		drawPane.setSize(new Dimension(500,400));
	
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
		drawPane.add((Component) train);
		drawPane.invalidate();
		drawPane.validate();
	}


	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		for(Component c : drawPane.getComponents())
			if(c instanceof JTrainComponent)
				if(((JTrainComponent)c).getID().equals(id))
					drawPane.remove(c);
		super.invalidate();
		super.validate();
		repaint();
	}


}
