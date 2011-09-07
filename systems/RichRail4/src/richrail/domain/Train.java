package richrail.domain;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class Train {
	
	static Logger logger = Logger.getLogger(Train.class);
	
	private List<Wagon> wagons = new ArrayList<Wagon>();
	private String name;
	
	public Train(String name){
		logger.info("new train ("+name+")");
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void addWagon(Wagon wagon){
		logger.info("adding wagon ("+wagon.getName()+") to train ("+this.getName()+")");
		this.wagons.add(wagon);
	}
	
	public void removeWagon(Wagon wagon){
		logger.info("removing wagon ("+wagon.getName()+") to train ("+this.getName()+")");
		this.wagons.remove(wagon);
	}
	
	public List<Wagon> getWagons(){
		return this.wagons;
	}
	
	public int getSeats(){
		int num = 0;
		for(Wagon wg : this.wagons){
			num += wg.getSeats();
		}
		return num;
	}
}
