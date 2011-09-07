package nl.hu.richrail.swing.text;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;

import nl.hu.richrail.swing.JTrain;
import nl.hu.richrail.swing.JTrainComponent;
import nl.hu.richrail.swing.JTrainSegment;
import nl.hu.richrail.trains.RollingStock;

public class Engine extends JLabel implements JTrainComponent
{


	private static final long serialVersionUID = -858470896824421697L;
	private RollingStock waggon;
	public Engine(RollingStock waggon) {
		super("("+waggon.getId() +":" +waggon.getSeatCount() +")");
		this.waggon = waggon;

		
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return waggon.getId();
	}

}
