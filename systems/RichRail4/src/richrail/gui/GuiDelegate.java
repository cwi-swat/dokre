package richrail.gui;

import java.util.Observable;
import java.util.Observer;

import richrail.domain.Depot;

public class GuiDelegate {
	
	private Gui gui;
	private Depot depot;
	
	public GuiDelegate(Depot depot, Gui gui) {
		this.depot = depot;
		this.gui = gui;
		gui.setDelegate(this);
		gui.setVisible(true);
		
		this.depot.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				updateGui((String) arg);				
			}
		});
		
	}
	
	public void updateGui(String message) {
		gui.outputpanel.append(message);
		gui.updateTrains(depot.getTrains());
		gui.updateWagons(depot.getWagons());
		
	}

	public void createTrain() {
		String name = gui.trainspanel.newname.getText();
		depot.createTrain(name);
	}

	public void removeTrain() {
		int selected = gui.trainspanel.traincombo.getSelectedIndex();
		if(selected != -1){
			depot.removeTrain(depot.getTrains().get(selected));
		}
	}

	public void addWagon() {
		int selectedtrain = gui.trainspanel.train_addtowagoncombo.getSelectedIndex();
		int selectedwagon = gui.trainspanel.wagon_addtowagoncombo.getSelectedIndex();
		
		if(selectedtrain != -1 && selectedwagon != -1){
			depot.addWagonToTrain(depot.getTrains().get(selectedtrain), depot.getWagons().get(selectedwagon));
		}
		
	}

	public void createWagon() {
		String name = gui.wagonspanel.newname.getText();
		int seats = -1;
		
		try {
			seats = Integer.parseInt(gui.wagonspanel.seats.getText());
		} catch (NumberFormatException e){
			gui.wagonspanel.seats.setText("");
		}
		
		depot.createWagon(name, seats);			
		
	}

	public void removeWagon() {
		int selected = gui.wagonspanel.wagoncombo.getSelectedIndex();
		if(selected != -1){
			depot.removeWagon(depot.getWagons().get(selected));
		}
	}
	
}
