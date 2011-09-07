package Commands;
import java.util.ArrayList;

public class CommandFactory {
	
	public CommandFactory(){}
	
	public Command CommandMethod(String s) {
		ArrayList<String> words = new ArrayList<String>();
		String[] i = s.split(" ");
		for(String p : i) {
			words.add(p);
		}
		try{
			if(words.get(0).equals("new")&& words.get(1).equals("train")) {
				return new NewTrainCommand(words);
			}
		} catch(Exception e) {}
		
		
		try {
			if(words.get(0).equals("help")) {
				return new HelpCommand();
			}
		} catch(Exception e) {}
		try {
			if(words.get(0).equals("new")&& words.get(1).equals("wagon")) {
				return new NewWagonCommand(words);
			}
		} catch (Exception e) {}
		try {
			if(words.get(0).equals("add")) {
				return new AddCommand(words);
			}
		} catch (Exception e){}
		try {
			if(words.get(0).equals("delete")&&words.get(1).equals("train")) {
				return new DeleteTrainCommand(words);
			}
		} catch(Exception e){}
		try {
			if(words.get(0).equals("delete")&&words.get(1).equals("wagon")) {
				return new DeleteWagonCommand(words);
			}
		} catch(Exception e){}
		try {
			if(words.get(0).equals("getnumseats") && words.get(1).equals("train")){
				return new GetnumseatsTrainCommand(words);
			}
		} catch (Exception e) {}
		try {
			if(words.get(0).equals("getnumseats") && words.get(1).equals("wagon")){
				return new GetnumseatsWagonCommand(words);
			}
		} catch (Exception e) {}
		try {
			if(words.get(0).equals("remove")) {
				return new RemoveWagonCommand(words);
			}
		} catch (Exception e) {}
		try {
			if(words.get(0).equals("rene")) {
				return new ReneCommand();
			}
		} catch (Exception e) {}
		return null;
	}

}
