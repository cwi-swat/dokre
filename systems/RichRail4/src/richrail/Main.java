package richrail;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import richrail.commandline.CommandLineDelegate;
import richrail.commandline.CommandLineGui;
import richrail.domain.Depot;
import richrail.gui.DisplayDemo;
import richrail.gui.Gui;
import richrail.gui.GuiDelegate;


public class Main {

	static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		
		PropertyConfigurator.configure("richrail.properties");

		
		logger.debug("start");
		
		Depot depot = new Depot();
		Gui gui = new Gui();
		CommandLineGui cgui= new CommandLineGui();
		
		
		new GuiDelegate(depot, gui);
		new DisplayDemo(depot);
		new DisplayDemo(depot);
			new CommandLineDelegate(depot, cgui);
		
		logger.debug("creating dummy trains");
		
		depot.createTrain("tr1");
		depot.createTrain("tr2");
		depot.createTrain("tr3");
		
		logger.debug("creating dummy wagons");
		
		depot.createWagon("wg1");
		depot.createWagon("wg2");
		depot.createWagon("wg3");
		depot.createWagon("wg4");
		depot.createWagon("wg5");
		depot.createWagon("wg6");
		depot.createWagon("wg7");
		
		logger.debug("adding wagons to trains");
		
		depot.addWagonToTrain(depot.getTrainByName("tr2"), depot.getWagonByName("wg5"));
		depot.addWagonToTrain(depot.getTrainByName("tr2"), depot.getWagonByName("wg3"));
		
		
		logger.debug("terminated");
	}

}
