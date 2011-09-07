/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment2.command;

import assignment2.log.Log;

/**
 *
 * @author Ricardo Vink
 */
public class HelpCommand implements Command
{

	public HelpCommand()
	{
	}

	public void execute(String inputCommand)
	{
		Log oLog = Log.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append("==========Help==========\n\r");
		sb.append("ID = starting with a-z then a-z or 0-9\n\r");
		sb.append("TYPE = wagon or train\n\r");
		sb.append("NUMBER = 4 digits\n\r");
		sb.append("\n\r");
		sb.append("====Usable Commands====\n\r");
		sb.append("new train 'ID'\n\r");
		sb.append("new wagon 'ID'\n\r");
		sb.append("new wagon 'ID' numseats 'NUMBER'\n\r");
		sb.append("add 'ID' to 'ID'\n\r");
		sb.append("getnumseats 'TYPE' 'ID'\n\r");
		sb.append("delete 'TYPE' 'ID'\n\r");
		sb.append("remove 'ID' from 'ID'\n\r");
		sb.append("help\n\r");
		sb.append("========================");

		oLog.addLogLine(sb);
	}

}
