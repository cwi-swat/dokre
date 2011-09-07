package control;

import java.awt.Component;
import java.awt.Label;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;

import model.Train;
import model.Wagon;

public class TextView implements Observer {

	
	private RichRailFacade rrf; 
	
	private JTextArea textPanel1;
	private JTextArea textPanel2;
 
	private ArrayList<Train> trains;
	private ArrayList<Wagon> wagons;
 
	

	public TextView(RichRailFacade rrfc,JTextArea jt1, JTextArea jt2 ) {
		
		
		rrf = rrfc;
		
		textPanel1 = jt1;
		textPanel2 = jt2;

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		if (arg1 instanceof Wagon) {
			
			
			textPanel1.setText("");
			textPanel1.append("Wagons \n");
		    wagons = rrf.getWagons();
			
		    for (Wagon wagon: wagons){

				textPanel1.append(wagon.toString() + "\n");
		    }
		 
		 
		 
		 
			
			
		}

		
		if (arg1 instanceof Train) {
		
			
			textPanel2.setText("");
			textPanel2.append("Trains \n");
		    trains = rrf.getTrains();
			
		    for (Train train: trains){

				textPanel2.append(train.toString() + "\n");
		    }
		 
		 
			
		}
		
		 
		
	}
}
