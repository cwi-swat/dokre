package Domain;

public class WagonType {
	private int seats;
	private String naam;
	
	public WagonType(){
	}
	
	public WagonType(String nm, int se){
		naam = nm;
		seats = se;
	}
		
	public String getNaam(){
		return naam;
	}
	
	public void setNaam(String nm){
		naam = nm;
	}
	
	public int getSeats()
	{
		return seats;
	}

	public void setSeats(int se)
	{
		this.seats = se;
	}
}
