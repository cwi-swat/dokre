import java.util.ArrayList;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList<Train> trains = new ArrayList<Train>();
		
		LogView lv = new LogView();
		DataFrame view = new DataFrame(trains, lv);
		new CommandOuput(view, trains, lv);
//		new Controller(view, trains, lv);
	}

}
