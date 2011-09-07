package TaskSpecific;

import java.util.ArrayList;
import java.util.Iterator;


import Commands.Command;
import Domain.*;

public class Controller implements Observable{
	private static Controller instance;
	private Command cmd;
	private WagonType tp;
	private Train tr = new Train();
	private Wagon wg;
	public ArrayList<String> logs = new ArrayList<String>();
	public ArrayList<Displayer> observers = new ArrayList<Displayer>();
	public ArrayList<Train> Trains = new ArrayList<Train>();
	public ArrayList<WagonType> WagonTypes = new ArrayList<WagonType>();
	public ArrayList<Wagon> Wagons = new ArrayList<Wagon>();
	protected ArrayList<Writer> writers;
	Writer writer1 = new TextWriter("RTFWriter", "Writes to RTF text file", "log.rtf");
	
	
	//Treincommand wordt verstuurd vanuit de converter en wordt hier toegevoegd aan de lijst van treinen
	//Eerst wordt gecontroleerd of de trein al bestaat
	//Niels
	public Controller(){
//		cmd.setController(this);
		this.writers = new ArrayList<Writer>();
		addWriter(writer1);
	}
	
	public static Controller getInstance(){
		if(instance == null)
			instance = new Controller();
		return instance;
	}
	public void newTrain(String name){
		if(trainExist(name)){
					Train t = new Train(name);
					
					logs.add("Train "+name+" created");
					Trains.add(t);
					notifyObservers();
		}
		else{
			logs.add("Train "+name+" not created");
			notifyObservers();
		}
	}
	//Functie om te controleren of de trein al bestaat
	//Niels
	public boolean trainExist(String name){
		for(Train t: Trains){
			if(t.getName().equals(name)){
				return false;		
			}
		}return true;
	}
	//WagonTypecommand wordt verstuurd vanuit de converter en wordt hier toegevoegd aan de lijst van wagontypes
	//Niels
	public void newWagonType(String name, int seats){
		
				if(wagonTypeExist(name)){
					WagonType wagontype = new WagonType(name, seats);
					logs.add("WagonType "+name+"("+seats+")"+" created");
					WagonTypes.add(wagontype);
					notifyObservers();
				}else{
					logs.add("WagonType "+name+"("+seats+")"+" not created");
					notifyObservers();
				}
			}
	//Functie om te controleren of de WagonType al bestaat
	public boolean wagonTypeExist(String name){
		
		for(WagonType wt: WagonTypes){
			if(wt.getNaam().equals(name)){
				return false;
			}
		}return true;
	}
	//Wagoncommand wordt verstuurd vanuit de converter en wordt hier toegevoegd aan de lijst van wagons
	//Eerst moet er gecheked worden of de aangegeven wagontype wel bestaat, anders foutmelding
	//Niels
	public void newWagon(int ID, String wt){
		boolean b = true;
		boolean b2 = true;
		boolean b3 = false;
		for(WagonType wagontype: WagonTypes){
			if(wagontype.getNaam().equals(wt)){
				b = false;
					for(Wagon w : Wagons ){ 
						if (ID == w.getID()){
							b3 = true;
						}
					}
					if(b3 == false ){
							b2 = false;
							Wagon wagon = new Wagon(ID, wagontype);
							Wagons.add(wagon);
							logs.add("Wagon "+ID+" created with "+wagontype.getSeats()+" seats");
							notifyObservers();
					}

				}
			}
		
		if(b){
			logs.add("Wagon "+ID+" not created, wagontype does not exist");
			notifyObservers();
		}
		if(b2){
			logs.add("Wagon "+ID+" not created, wagon already exist");
			notifyObservers();
		}
	}
	//Functie om te controleren of de WagonType al bestaat
	public boolean wagonExist(int id){
		ArrayList<Wagon> WagonList = Wagons;
		for(Wagon w: WagonList){
			if(w.getID()==id){
				return false;
			}
		}return true;
	}
	//Controle of de wagon niet al door een andere trein wordt gebruikt
	//Van elke trein uit de lijst van deze klasse wordt de lijst met wagons opgehaald
	//Deze worden vergeleken met de ingevoerde wagon
	//Niels
	public boolean wagonInUse(Wagon wagon){
		ArrayList<Wagon> WagonList = null;
		for(Train t: Trains){
			WagonList = t.getWagons();
			for(Wagon w: WagonList){
				if(w.equals(wagon)){
					return false;
				}
			}
		}
		return true;
	}
	//Hier wordt de wagon aan een trein gekoppeld
	//Er wordt gecontroleerd of de trein en wagon bestaan en of ze niet al gekoppeld zijn
	//Niels
	public void add(String train, int wagon){
		if(trainExist(train)){
			logs.add("train does not exist");
			notifyObservers();
		}
		if(wagonExist(wagon)){
			logs.add("Wagon doet not exist");
			notifyObservers();
		}
		
		for(Train t: Trains){
			//Bestaat de trein?
			if(t.getName().equals(train)){
				for(Wagon w: Wagons){
					//Bestaat de wagon?
					if(w.getID()==wagon){
						//Wagon al in gebruik?
						if(wagonInUse(w)){
							t.addWagon(w);
							logs.add("Wagon "+wagon+" added to train "+ train);
							notifyObservers();
						}else{
							logs.add("Wagon is already in use");
							notifyObservers();
						}
					}		
				}
			}
		}
	}
	//Met deze functie geven wij het aantal stoelen per wagonType terug
	//Niels
	public void getSeatsFromWagon(int wagon){
		if(wagonExist(wagon)){
			logs.add("Wagon does not exist");
			notifyObservers();
		}
		ArrayList<Wagon> WagonList = Wagons;
		for(Wagon w: WagonList){
			if(w.getID()==wagon){
				logs.add("Number of seats in wagon "+wagon+":"+w.getType().getSeats());
				notifyObservers();
			}
		}
	}
	//Met deze functie geven wij het aantal stoelen per Trein terug
	//Niels
	public void getSeats(String train){
		if(trainExist(train)){
			logs.add("Train does not exist");
			notifyObservers();
		}
		int seats = 0;
		ArrayList<Train> Trainlist = Trains;
		ArrayList<Wagon> WagonList = null;
		for(Train t: Trainlist){
			if(t.getName().equals(train)){
				WagonList = t.getWagons();
			}
		}
		for(Wagon w: WagonList){
			seats = seats + w.getType().getSeats();
		}
		logs.add("Number of seats in train "+train+":"+seats);
		notifyObservers();
	}
	
	//Wagon verwijderen uit de lijst van wagons in deze klasse
	public void deleteWagon(int wagon){
		boolean b = false;
		if(wagonExist(wagon)){
			logs.add("Wagon does not exist");
			notifyObservers();
			b = true;
		}
		if(!b){
		for(Wagon w: Wagons){
			if(w.getID()==wagon){
				Wagons.remove(w);
				for(Train t: Trains){
					t.removeWagon(w);
					logs.add("Wagon "+wagon+" deleted");
					notifyObservers();
					}
					
			}
		}
		}
	}
	public void deleteWagonType(String wagontype){
		boolean b = false;
		if(wagonTypeExist(wagontype)){
			logs.add("Wagontype does not exist");
			notifyObservers();
			b = true;
		}
		if(!b){
		//ArrayList<Wagon> WagonList = null;
		WagonType wT = new WagonType();
		for(WagonType wt: WagonTypes){
			if(wt.getNaam().equals(wagontype)){
				//WagonList = t.getWagons();
				//for(Wagon w: WagonList){
				//	Wagons.remove(w);
				//}
				wT = wt;
				
			}
		}
		logs.add("WagonType "+wagontype+" deleted");
		WagonTypes.remove(wT);
		notifyObservers();
		}
	}
	//Trein wordt hier verwijderd uit de lijst uit deze klasse
	//Niels
	public void deleteTrain(String train){
		boolean b = false;
		if(trainExist(train)){
			logs.add("Train does not exist");
			notifyObservers();
			b = true;
		}
		if(!b){
		//ArrayList<Wagon> WagonList = null;
		Train tr = new Train();
		for(Train t: Trains){
			if(t.getName().equals(train)){
				//WagonList = t.getWagons();
				//for(Wagon w: WagonList){
				//	Wagons.remove(w);
				//}
				tr = t;
				
			}
		}
		logs.add("Train "+train+" deleted");
		Trains.remove(tr);
		notifyObservers();
		}
	}
	//Hier wordt een wagon weggehaald bij een trein
	//Eerst moet worden gecheck of de ingevoerde trein en wagon bestaat
	//Daarna wordt de wagon uit de list van de trein gehaald
	//Niels
	public void removeWagon(String train, int wagon){
		if(trainExist(train)){
			logs.add("Train does not exist");
			notifyObservers();
		}
		if(wagonExist(wagon)){
			logs.add("Train does not exist");
			notifyObservers();
		}
		boolean b = false;
		//ArrayList<Wagon> WagonList = new ArrayList<Wagon>();
		for(Train t: Trains){
			if(t.getName().equals(train)){
				//WagonList = t.getWagons();
					for(Wagon w: t.getWagons()){
						if(w.getID()==wagon){
							b = true;
							t.removeWagon(w);
							logs.add("Wagon "+wagon+" deleted from train "+ train);
							notifyObservers();
						}
					}
				}
			}
		if(!b){
			logs.add("Wagon "+wagon+" not deleted from train "+ train);
			notifyObservers();
		}
		}
	public ArrayList<String> getLogs(){
		//Dit is nodig voor in LogDisplay
		return logs;
	}
	public void addObserver(Displayer o) {
		//Dit is om de verschillende displays toe te voegen
		 observers.add(o);
	}
	public void removeObserver(Displayer o) {
		//Dit is nodig om een display te verwijderen
		observers.remove(o);
	}
	private void notifyObservers() {
        //Met deze functie worden alle displays gewaarschuwd
        Iterator<Displayer> i = observers.iterator();
        while( i.hasNext() ) {
        	Displayer o = ( Displayer ) i.next();
              o.update( this );
        }
  }
	
	
	public void addWriter(Writer writer)
	{
		//TODO: mogen writers 2 keer voorkomen? zo nee writers.contains toevoegen (equals methode writer)

		// kan worden opgelost met een simpele 
		// if(writers.indexOf(writer)) return false;
		writers.add(writer);
	}
	
	public void removeWriter(Writer writer)
	{
		//TODO: writers equals methode schrijven
		if(writers.contains(writer))
		{
			writers.remove(writer);
		}
	}
	public ArrayList<Writer> getWriters() {
		return writers;
	}
	public void setWriters(ArrayList<Writer> writers) {
		this.writers = writers;
	}
	
}
