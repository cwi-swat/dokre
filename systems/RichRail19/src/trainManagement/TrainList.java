package trainManagement;

import java.util.*;

public class TrainList{
	private Train train = null;
	private String trId = "";
	private List<Object> list = new ArrayList<Object>();
	
	public TrainList(Train id){
		setTrain(id);
	}
	
	public void addWagon(Wagon w){
		Train t = train;
		list.add(w);
		t.setSeat(w.getSeat());
	}
	
	public void removeWagon(Wagon w){
		Train t = train;
		list.remove(w);
		t.removeSeat(w.getSeat());
	}

	public void setList(Object o) {
		list.add(o);
	}

	public void setTrain(Train train) {
		this.train = train;
		setTrId(train.getTrId());
	}

	public Train getTrain() {
		return train;
	}
	
	public void setTrId(String newTrId) {
		trId = newTrId;
	}

	public String getTrId() {
		return trId;
	}

	public String getTrainList(){
		String s = "("+getTrId()+")";
		for(Iterator<Object> iter = list.iterator();iter.hasNext();){
			try{
				Object o = iter.next();
				if(o instanceof Wagon){
					Wagon w = (Wagon)o;
					s+="-"+w.getWagon();
				}
			}catch (NoSuchElementException e){
				System.err.println("NoSuchElement");
			}
		}	
		return s;
	}
	
	public String toString(){
		String s =  getTrId()+"-";
		for(Iterator<Object> iter = list.iterator();iter.hasNext();){
			try{
				Object o = iter.next();
				if(o instanceof Wagon){
					Wagon w = (Wagon)o;
					s+=w.getWgId()+"-";
				}
			}catch (NoSuchElementException e){
				System.err.println("NoSuchElement");
			}
		}	
		return s;
	}
}
