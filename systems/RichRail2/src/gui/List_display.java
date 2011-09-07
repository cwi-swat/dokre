package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Station;
import domain.Train;
import domain.Type;
import domain.Wagon;

@SuppressWarnings("serial")
public class List_display extends JPanel implements Observer {
	private JTextArea txt1 = new JTextArea(); 
	public List_display(){
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.setVisible(true);
		txt1.setEditable(false);
		this.add(txt1, BorderLayout.NORTH);
		
	}

	@Override
	public void refreshData() {
		Station s = Station.getInstance();
		ArrayList<Train> trains = s.getTrains();
		ArrayList<Wagon> wagons = s.getWagons();
		ArrayList<Type> types = s.getTypes();
		txt1.setText("Wagons:\n");
		for (Wagon wagon : wagons){
			
			txt1.setText(txt1.getText()+"("+wagon.getId()+" : "+wagon.getType().getNumberOfSeats()+")  ");
		}
		txt1.setText(txt1.getText()+"\nTrains:\n");
		for (Train train : trains){
			
			ArrayList<Wagon> train_wagons = train.getWagons();
			txt1.setText(txt1.getText()+"("+train.getId()+")");
			for(Wagon wagon : train_wagons){
				txt1.setText(txt1.getText()+"-("+wagon.getId()+") ");
			}
			txt1.setText(txt1.getText()+"\n");
		}
		txt1.setText(txt1.getText()+"Types:\n");
		for (Type type : types){
			
			txt1.setText(txt1.getText()+"("+type.getId()+") ");
		}
		
	}

}
