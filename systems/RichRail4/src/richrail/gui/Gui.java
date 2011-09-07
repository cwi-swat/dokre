package richrail.gui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import richrail.domain.Train;
import richrail.domain.Wagon;

@SuppressWarnings("serial")
public class Gui extends JFrame{
	
	public DisplayPanel displaypanel;
	public TrainsPanel trainspanel;
	public WagonsPanel wagonspanel;
	public OutputPanel outputpanel;
	
	private GuiDelegate delegate;
	
	public Gui(){
		
		setSize(795, 620);
		setTitle("RichRail");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		displaypanel = new DisplayPanel();
		displaypanel.setBounds(0, 0, 785, 200);
		
		trainspanel = new TrainsPanel();
		trainspanel.setBounds(5, 210, 390, 200);
		
		wagonspanel = new WagonsPanel();
		wagonspanel.setBounds(400, 210, 390, 200);
		
		outputpanel = new OutputPanel();
		
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new GridLayout(3,1));
		mainpanel.add(displaypanel);
		
		
		JPanel controlspanel = new JPanel(new GridLayout(1,2, 50,50));
		controlspanel.add(trainspanel);
		controlspanel.add(wagonspanel);
		
		mainpanel.add(controlspanel);
		mainpanel.add(outputpanel);
		
		this.getContentPane().setLayout(new GridLayout(1,1));
		this.getContentPane().add(mainpanel);
		
	}
	
	public void setDelegate(GuiDelegate guiDelegate) {
		this.delegate = guiDelegate;
		trainspanel.setDelegate(delegate);
		wagonspanel.setDelegate(delegate);
	}
	
	public void updateTrains(List<Train> trains){
		displaypanel.updateTrains(trains);
		trainspanel.updateTrains(trains);
	}
	
	public void updateWagons(List<Wagon> wagons){
		trainspanel.updateWagons(wagons);
		wagonspanel.updateWagons(wagons);
	}
}
