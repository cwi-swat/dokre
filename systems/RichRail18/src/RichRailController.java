import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RichRailController{
	private RichRailUI ui;
	private File f = new File("database");
	private CommandReader grammar;
	private ArrayList<Vehicle> listVehicles = new ArrayList<Vehicle>();
	private ArrayList<String> trains = new ArrayList<String>();
	private Log log = new Log();
	private Graphics g;
	

	public RichRailController(RichRailUI richRailUI) {
		ui = richRailUI;
		grammar = new CommandReader(this);
	}

	public void execute(String cmd) {
		String s = grammar.execute(cmd);
		ui.setOutput(s);
		try {
			log.add(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ui.setTrainList(getTrainList());
		draw();
		ui.draw(trains);
		//write(); doesn't work properly
	}
	
	public boolean write(){
		try{
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(listVehicles);
			fos.close();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		return true;
	}
	
	public boolean read(){
		if(f.exists()){	
			try{
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				try {
					listVehicles = (ArrayList<Vehicle>)ois.readObject();//TODO: hier geeft hij een foutmelding!!!
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				fis.close();
				ui.setTrainList(getTrainList());
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
		return true;
	}
	
	public String getTrainList(){
		String s = "Wagons\n";
		for (Vehicle w : listVehicles){
			if(w instanceof Wagon){
				s = s + w.toStringWagon((Wagon)w) + " ";
			}
		}
		s = s + "\nTrains\n";
		for(Vehicle t : listVehicles){
			if(t instanceof Train){
				s = s + t.toStringTrain((Train)t) + "\n";
			}
		}
		return s;
	}


	public boolean newTrain(String nm) {
		boolean b = true;
		for(Vehicle t : listVehicles){
			if(t instanceof Train && t.getTrainName((Train)t).equals(nm)){
				b = false;
			}
		}
		if(b){
			Vehicle t = new Train(nm);
			listVehicles.add(t);
		}
		return b;
	}

	public boolean newWagon(String wgn) {
		boolean b = true;
		for(Vehicle w : listVehicles){
			if(w instanceof Wagon && w.getWagonName((Wagon)w).equals(wgn)){
				b = false;
			}
		}
		if(b){
			Vehicle w = new Wagon(wgn);
			listVehicles.add(w);
		}
		return b;
	}

	public boolean newWagon(String wgn, int numseats) {
		boolean b = true;
		for(Vehicle w : listVehicles){
			if(w instanceof Wagon && w.getWagonName((Wagon)w).equals(wgn)){
				b = false;
			}
		}
		if(b){
			Vehicle w = new Wagon(wgn, numseats);
			listVehicles.add(w);
		}
		return b;
	}

	public boolean addWagonToTrain(String wgn, String trn) {
		boolean b = false;
		for (Vehicle t : listVehicles){
			if(t instanceof Train && t.getTrainName((Train)t).equals(trn)){
				for(Vehicle w : listVehicles){
					if(w instanceof Wagon && w.getWagonName((Wagon)w).equals(wgn)){
						t.addWagon((Train)t, (Wagon)w);
						b = true;
					}
				}
			}
		}
		return b;
	}
	
	public int getTrainSeats(String trn) {
		int seats = 0;
		for (Vehicle t : listVehicles){
			if(t instanceof Train && t.getTrainName((Train)t).equals(trn)){
				seats += t.getSeats((Train)t);
			}
		}
		return seats;
	}

	public int getWagonSeats(String wgn) {
		for(Vehicle w : listVehicles){
			if(w instanceof Wagon && w.getWagonName((Wagon)w).equals(wgn)){
				return w.getSeats((Wagon)w);
			}
		}
		return 0;
	}

	public boolean DeleteTrain(String trn) {
		for(Vehicle t : listVehicles){
			if(t instanceof Train && t.getTrainName((Train)t).equals(trn)){
				listVehicles.remove(t);
				return true;
			}
		}
		return false;
	}

	public boolean DeleteWagon(String wgn) {
		boolean b = false;
		for(Vehicle t : listVehicles){
			if(t instanceof Train){
				t.removeWagon((Train)t, wgn);
			}
		}
		for(Vehicle w : listVehicles){
			if(w instanceof Wagon && w.getWagonName((Wagon)w).equals(wgn)){
				listVehicles.remove(w);
				b = true;
			}
		}
		return b;
	}

	public boolean removeWagonFromTrain(String wgn, String trn) {
		boolean b = false;
		for(Vehicle t : listVehicles){
			if(t instanceof Train && t.getTrainName((Train)t).equals(trn)){
				b = t.removeWagon((Train)t, wgn);
			}
		}
		return b;
	}
	
	public void draw(){
		String s = "";
		for(Vehicle t : listVehicles){
			if(t instanceof Train){
				s = t.toStringTrain((Train)t);
				trains.add(s);
			}
		}
	}
}
