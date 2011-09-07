package nl.hu.richrail.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;

import nl.hu.richrail.rrdsl.CmdResult;
import nl.hu.richrail.rrdsl.Command;
import nl.hu.richrail.rrdsl.SingleCmdParser;
import nl.hu.richrail.trains.TrainsBS;

public class TerminalController implements Controller, ActionListener
{
	//This class is no longer a observer
	// before it was observing a "terminal" to the domain (the train facade)
	// no it directly handles the commands by having acces to the parser
	public final TerminalWindow view;
	private TrainsBS domain;

	public TerminalController()
	{
		view = new TerminalWindow(this);
		domain = new TrainsBS();
		
	}
	
	@Override
	public void close() {
		view.dispose();

		
	}

	@Override
	public void show() {
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			CmdResult<Command> cmd =SingleCmdParser.parse(view.jtCommand.getText());
			view.jtaResult.append(cmd.getResult() + "\n");
			view.jtCommand.setText("");	
		}catch (MismatchedTokenException e2)
		{
			view.jtaResult.append(" MismatchedTokenException Invalid command " + e2 +"\n");
		}
		catch (NoViableAltException e3)
		{
			view.jtaResult.append("NoViableAltException Invalid command " + e3+"\n");
		}catch (RecognitionException e1) {
			view.jtaResult.append(" Invalid command " + e1.getMessage());
		}
			
	}
	

}
