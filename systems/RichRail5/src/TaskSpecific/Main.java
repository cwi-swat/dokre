package TaskSpecific;

import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//
//import Displays.DataEntry;
//import Displays.DrawningDisplay;
//import Displays.LogDisplay;
import Displays.MasterGUI;
//import Displays.TextDisplay;
//import Domain.Train;
//import Domain.Wagon;
//import Domain.WagonType;

public class Main {
	

	public static void main(String[] args) 
	{

		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
			//new LogDisplay();
				
				new MasterGUI();
				
				//PoorRail inst = new PoorRail();
				//inst.setLocationRelativeTo(null);
				//inst.setVisible(true);
				/*Converter conv = new Converter();
				
				Controller c = new Controller();
				conv.setControllerObject(c);
				
				conv.convertCommand("new train tr1;");
				conv.convertCommand("new wagontype wt1 15;");
				
				conv.convertCommand("new train Tjoek;");
				conv.convertCommand("new wagontype blauw 25;");
				conv.convertCommand("new wagon 101 blauw;");
				conv.convertCommand("new wagon 102 blauw;");

				
				DataEntry DE = new DataEntry();
				DE.setConverterObject(conv);*/
				
				
				/*c.newTrain("TjoekTjoek");
				c.newTrain("Baaas");
				c.newWagonType("Blauw", 20);
				c.newWagon(1, "Blauw");
				c.newWagon(2, "Blauw");
				c.add("TjoekTjoek", 1);
				c.add("TjoekTjoek", 2);
				c.deleteTrain("Baaas");
				*/
			/*	for(Train t: c.Trains){
				
				System.out.println(t.getName()+" "+t.getWagons().size());
				}
				for(WagonType wt: c.WagonTypes){
					System.out.println(wt.getNaam()+" "+wt.getSeats());
				}
				for(Wagon w: c.Wagons){
					System.out.println(w.getID()+" "+w.getType().getNaam());
				}
				
				System.out.println("Observers:"+c.observers.size());*/

				

			}
		});
	}
}
