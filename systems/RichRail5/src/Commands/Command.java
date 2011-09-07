package Commands;
import TaskSpecific.*;


public abstract class Command {

		public Converter conv;
		public Controller cont = Controller.getInstance();
		public abstract void convertCommand(String input);
		
		
		/*public  void setController(Controller controller){
			// TODO Auto-generated method stub
			cont = controller;
		}*/
}
