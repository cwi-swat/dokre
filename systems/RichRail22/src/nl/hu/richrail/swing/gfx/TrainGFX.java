package nl.hu.richrail.swing.gfx;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JTextArea;

import javax.swing.*;

import nl.hu.richrail.swing.JTrain;
import nl.hu.richrail.swing.JTrainSegment;
import nl.hu.richrail.trains.Car;
import nl.hu.richrail.trains.Engine;

public class TrainGFX extends JTrain
{


	public TrainGFX(String id) {
		super(id);
		setLayout(new BorderLayout());
		jbTrainID = new JLabel("Train# " + id);
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.LEFT);
		jpScrollContent.setLayout(f);
		jscScroll = new JScrollPane(jpScrollContent);
		jscScroll.setPreferredSize(new Dimension(500,170));
		add(jbTrainID,BorderLayout.NORTH);
		add(jscScroll,BorderLayout.CENTER);
	}

	private JScrollPane jscScroll ;
	private JPanel jpScrollContent = new JPanel();
	private JLabel jbTrainID;

	@Override
	public void add(JComponent trainSegment) {
		jpScrollContent.add(trainSegment);
		jpScrollContent.invalidate();
		jpScrollContent.validate();
		
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		for(Component j : jpScrollContent.getComponents())
		{
			if(j instanceof JTrainSegment)
				if(((JTrainSegment)j).getID().equals(id))
					jpScrollContent.remove(j);
		}
		super.invalidate();
		super.validate();
		repaint();
	}
	
	
}
