package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Observable;

import logging.Logger;
import view.MainWindow;
import controller.interpreter.Context;
import domain.Company;

public class AppController extends Observable implements WindowListener
{
	private Company company;

	private ArrayList<MainWindow> windows;

	public AppController()
	{
		this.company = new Company();
		this.addWindow(new MainWindow(this));
	}

	public void addWindow(MainWindow window)
	{
		if (this.windows == null)
			this.windows = new ArrayList<MainWindow>();

		this.windows.add(window);
		
		window.addWindowListener(this);
		
		this.setChanged();
		notifyObservers();
	}

	public Company getCompany()
	{
		return company;
	}

	/**
	 * Process the command from the console in one of the windows. When the command is processed, all the 
	 * windows will be updated.
	 * @param input
	 */
	public void processCommand(String input)
	{
		Context context = new Context(this, input);
		Logger.getInstance().log(new String(input));

		try
		{
			context.Evaluate();
		}
		catch (Exception e)
		{
			Logger.getInstance().log(new String(e.getMessage()));
		}

		// Er is na een commando altijd iets veranderd, dus:
		this.setChanged();
		notifyObservers();
	}


	@Override
	public void windowClosed(WindowEvent e)
	{
		if(windows.size() -1 == 0)
		{
			System.exit(0);
		}
	}

	@Override
	public void windowActivated(WindowEvent e){}

	@Override
	public void windowClosing(WindowEvent e){}

	@Override
	public void windowDeactivated(WindowEvent e){}

	@Override
	public void windowDeiconified(WindowEvent e){}

	@Override
	public void windowIconified(WindowEvent e){}

	@Override
	public void windowOpened(WindowEvent e){}

	

}
