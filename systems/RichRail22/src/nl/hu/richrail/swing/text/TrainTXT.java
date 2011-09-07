package nl.hu.richrail.swing.text;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JTextArea;

import javax.swing.*;
import javax.swing.border.Border;

import nl.hu.richrail.swing.JTrain;
import nl.hu.richrail.swing.JTrainSegment;
import nl.hu.richrail.trains.Car;
import nl.hu.richrail.trains.Engine;

public class TrainTXT extends JTrain
{


	public TrainTXT(String id) {
		super(id);
		FlowLayout f =new FlowLayout();
		f.setAlignment(FlowLayout.LEFT);
		setLayout(f);
		
	}
	public TrainTXT() {
		this("");
	}


	@Override
	public void add(JComponent trainSegment) {
			add((Component)trainSegment);
		
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		for(Component j : getComponents())
		{
			if(j instanceof JTrainSegment)
				if(((JTrainSegment)j).getID().equals(id))
					remove(j);
		}
		invalidate();
		validate();
	}
	
	
}
