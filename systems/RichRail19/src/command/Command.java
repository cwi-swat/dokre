package command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trainManagement.TrainManagement;

public class Command {
	TrainManagement model = null;
	String [] tokens = null;
	String message = "";
	
	//Default Action methode.
	public Boolean Action(){
	//	System.out.println("This is default command action.");
		
		return false;
	}
	
	//Set an error message, which is set by the command.
	//Want to have: ca	ll an ID for pre message.
	public void setMessage(String message){
		this.message = message;
		System.out.println(this.message);
	}
	
	//Get an error message, which can be called by the lexer if return command is false.
	public String getMessage(){
		return message;
	}
	
	//Set full command token set.
	public void setParams(String [] tokens){
		this.tokens = tokens;
	}
	
	//Check Expression
	protected boolean checkExpression(String expression, String token){
		
		Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(token);
            
        boolean found = false;
        while (matcher.find()) {
            found = true;
        }
        return found;
	}
	
	public void setTrainManagment(TrainManagement tm){
		model = tm;	
	}
}
