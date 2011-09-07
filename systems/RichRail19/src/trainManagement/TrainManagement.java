package trainManagement;

import java.util.HashMap;

public class TrainManagement extends java.util.Observable {
	private HashMap<String, Train> trainData = new HashMap<String, Train>();
	private HashMap<String, TrainList> trainList = new HashMap<String, TrainList>();
	private HashMap<String, Wagon> wagonData = new HashMap<String, Wagon>();
	private Wagon w;
	
	public TrainManagement(){ }
	
	private void update(){
		this.setChanged();
		this.notifyObservers();
	}
	
	// create new train or wagon
	public void newCommand(String type, String id, String seat){
		if(type.equals("train")){
			Train tr = new Train(id);
			TrainList tl = new TrainList(tr);
			tl.setList(tr);
			setTrainData(id,tr);
			setTrainList(id,tl);
			System.out.println("new train "+id+" created");
		}else{
			if(seat.equals("")){
				w = new Wagon(id,20);
				setWagonData(id,w);
				System.out.println("new wagon "+w.getWgId()+" created with 20 seats");
			}else{
				int i = Integer.parseInt(seat);
				w = new Wagon(id,i);
				setWagonData(id,w);
				System.out.println("new wagon "+w.getWgId()+" created with "+i+" seats");
			}
		}
		
		update();
	}
	
	// add wagon to train
	public void addCommand(String wgId, String trId){
		if(checkTrainExist(trId) && checkWagonExist(wgId)){
			TrainList t = trainList.get(trId);
			w = getWagonData(wgId);
			t.addWagon(w);
			System.out.println("wagon "+w.getWgId()+" added to train "+trId);
		}
		update();
	}
	
	
	//delete train or wagon
	public void delCommand(String type, String id){
		if(type.equals("train")){
			if(checkTrainExist(id)){
				trainData.remove(id);
				trainList.remove(id);
				System.out.println("train "+id+" deleted!");
			}
		}else{
			if(checkWagonExist(id)){
				wagonData.remove(id);
				System.out.println("wagon "+id+" deleted!");
			}
		}
		update();
	}
	
	// remove wagon from train
	public void removeCommand(String wgId, String trId){
		if(checkTrainExist(trId) && checkWagonExist(wgId)){
			TrainList t = trainList.get(trId);
			w = getWagonData(wgId);
			t.removeWagon(w);
			System.out.println("wagon "+w.getWgId()+" removed from train "+trId);
		}
		update();
	}
	
	// get seat from wagon or train
	public void getSeat(String type, String id){
		if(type.equals("train")){
			if(checkTrainExist(id)){
				Train t = getTrainData(id);
				System.out.println("number of seats in train "+id+": "+t.getSeat());
			}
		}else{
			if(checkWagonExist(id)){
				w = getWagonData(id);
				System.out.println("number of seats in wagon "+w.getWgId()+": "+w.getSeat());
			}
		}
	}
	
	// check of train of wagon exist
	public boolean checkTrainExist(String id){
		if(getTrainData(id)!=null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkWagonExist(String id){
		if(getWagonData(id)!=null){
			return true;
		}else{
			return false;
		}
	}	

	// save the job
	public void setTrainData(String key, Train value){
		trainData.put(key, value);
	}
	
	public Train getTrainData(String keyset){
		return trainData.get(keyset);
	}
	
	public void setWagonData(String key, Wagon value){
		wagonData.put(key, value);
	}
	
	public Wagon getWagonData(String keyset){
		return wagonData.get(keyset);
	}
	
	public void setTrainList(String id, TrainList newList){
		trainList.put(id, newList);
	}
	
	// show the data to command gui
	public String getData(){
		String str = "wagons\n";
		for(String s : wagonData.keySet()){
			Wagon w = wagonData.get(s);
			str+=w.getWagon()+"\t";
		}
		str+="\r\n";
		str+="trains\n";
		for(String ss : trainList.keySet()){
			TrainList tl = trainList.get(ss);
			str+=tl.getTrainList()+"\n";
		}
		return str;
	}
	
	// print the train list.
	public String toString(){
		String str = "";
		for(String s : trainList.keySet()){
			TrainList tl = trainList.get(s);
			str+=tl.toString()+"\n";
		}
		return str;
	}

}
