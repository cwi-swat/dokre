package nl.hu.richrail.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;

import nl.hu.richrail.swing.gfx.TrainGFXUIfactory;
import nl.hu.richrail.swing.text.TrainTXTUIfactory;


public class ApplicationController extends WindowAdapter implements ActionListener
{
	private ArrayList<Controller> openWindows = new ArrayList<Controller>();
	private ApplicationWindow view = new ApplicationWindow();
	private Stack<JFrame> activeDialogs = new Stack<JFrame>();
	private JMenuItem miOpen,miExit,miRsPoolviewTXT,miRsPoolviewGFX;
	
	public ApplicationController()
	{
		initController();
	}
	
	private void initController()
	{
		view.addWindowListener(this);
		
		miOpen = view.mFile.addMenuItem("Open Terminal");
		miOpen.addActionListener(this);
		miRsPoolviewTXT = view.mFile.addMenuItem("Open Text Rolling stock Pool (Text)");
		miRsPoolviewTXT.addActionListener(this);
		miRsPoolviewGFX = view.mFile.addMenuItem("Open Text Rolling stock Pool (GFX)");
		miRsPoolviewGFX.addActionListener(this);
		miExit = view.mFile.addMenuItem("Close program");
		miExit.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) 
	{
		JMenuItem source = (JMenuItem)e.getSource();
		if(source.equals(miOpen)){
			TerminalController x = new TerminalController();
			view.addView(0,x.view );
			openWindows.add(x);
		}
		if(source.equals(miRsPoolviewGFX)){
			TrainManagementController x = new TrainManagementController(new TrainGFXUIfactory());
			view.addView(0,x.view );
			openWindows.add(x);
		}
		if(source.equals(miRsPoolviewTXT)){
			TrainManagementController x = new TrainManagementController(new TrainTXTUIfactory());
			view.addView(0,x.view );
			openWindows.add(x);
		}
		if(source.equals(miExit))
			closeApplication();
	}
	
	private void closeApplication()
	{
		for(Controller wc : openWindows)
			wc.close();
		view.dispose();
		
	}
	
	public static void main(String[] args)
	{
		new ApplicationController();
		
	}
}
