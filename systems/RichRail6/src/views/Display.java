package views;

import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JPanel;

import models.Log;
import models.Storage;
import models.Train;
import models.WagonType;


public abstract class Display extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	protected ArrayList<Train> trains = new ArrayList<Train>();
	protected ArrayList<WagonType> wagontypes = new ArrayList<WagonType>();
	protected ArrayList<Log> logs = new ArrayList<Log>();
	
	protected int width = 630;
	public int getWidth(){
		return this.width;
	}
	
	public void setVariables(){
		setTrains(Storage.get().getTrains());
		setWagonTypes(Storage.get().getWagonTypes());
		setLogs(Storage.get().getLogs());
	}
    
    public void setTrains(ArrayList<Train> trains){
    	this.trains = trains;
    }
    public void setWagonTypes(ArrayList<WagonType> wagontypes){
    	this.wagontypes = wagontypes;
    }    
    public void setLogs(ArrayList<Log> logs){
    	this.logs = logs;
    }
    public void refresh() {}
	public void update(Observable arg0, Object arg1) {}
}
