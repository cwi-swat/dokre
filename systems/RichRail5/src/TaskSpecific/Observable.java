package TaskSpecific;

public interface Observable {
	//Er staan de functies die er voor zorgen dat de Displayer en Controller
	//met elkaar verbonden zijn
	 public void addObserver( Displayer o );
     public void removeObserver( Displayer o );

}
