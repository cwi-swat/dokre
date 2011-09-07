package Command;

import java.util.regex.PatternSyntaxException;

import javax.swing.JOptionPane;

public class Factory {
	
	private String command;
	public Factory(String command){
		this.command = command;
	}
	
	public void setCommand(String cmd){
		this.command = cmd;
	}
	public Command createCommand(){
		int space = command.indexOf(' ');
		
		if(space > 0){
			String trigger = command.substring(0, space);
			String command2 = command.substring((space+1));
			if(trigger.equalsIgnoreCase("new")){
				return new new_command(command2);
			}
			else if(trigger.equalsIgnoreCase("add")){
				return new add_command(command2);
			}
			else if(trigger.equalsIgnoreCase("get")){
				return new get_command(command2);
			}
			else if(trigger.equalsIgnoreCase("delete")){
				return new delete_command(command2);
			}
			else if(trigger.equalsIgnoreCase("remove")){
				return new remove_command(command2);
			}else{
				JOptionPane.showMessageDialog(null, "Not a valid command", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Not a valid command", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
}
