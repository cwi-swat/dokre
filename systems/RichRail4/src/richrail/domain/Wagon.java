package richrail.domain;

import org.apache.log4j.Logger;


public class Wagon {
	
	static Logger logger = Logger.getLogger(Wagon.class);
	
	private int seats = 20;
	private String name;
	
	public Wagon(String name, int seats){
		logger.info("new wagon ("+name+") with "+seats+" seats");
		this.seats = seats;
		this.name = name;
	}
	
	public Wagon(String name){
		logger.info("new wagon ("+name+") with "+seats+" seats");
		this.name = name;
	}
	
	public int getSeats(){
		return this.seats;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		return "wagon '"+this.name+"': "+this.seats;
	}
	
}
