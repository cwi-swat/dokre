package nl.hu.richrail.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class ApplicationWindow extends JFrame
{
	private JDesktopPane desk;
	public final MenuFile mFile = new MenuFile();
	public final MenuWindows mWindows = new MenuWindows();
	
	public ApplicationWindow()
	{
		super("RichRail");
		initGUI();
		setVisible(true);
	}
	
	private void initGUI()
	{
		JMenuBar AppMenu = new JMenuBar();
		setJMenuBar(AppMenu);
		AppMenu.add(mFile);
		AppMenu.add(mWindows);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800,600));  
		desk = new JDesktopPane();
		setContentPane(desk);
		pack();
	}

	public void addView(int number, JInternalFrame f)
	{
	   // f.setBounds(number * 10 - 5, number * 10 - 5, 250, 150);
	    mWindows.addWindow(f);
	    desk.add(f, 1);
	}
	
	public void removeDocumentFrame(JInternalFrame f)
	{
		desk.remove(f);
	}

	
	public class MenuFile extends JMenu
	{

		public MenuFile()
		{			
			setText("File");
		}
		public JMenuItem addMenuItem(String name)
		{
			JMenuItem mi = new JMenuItem();
			add(mi);
			mi.setText(name);
			return mi;
		}
		
	}

	public class MenuWindows extends JMenu
	{
		ArrayList<JInternalFrame> windows = new ArrayList<JInternalFrame>();
		
		public MenuWindows()
		{
			setText("Window");
		}
		
		public void addWindow(JInternalFrame documentWindow)
		{
			windows.add(documentWindow);
			createMenuItem(documentWindow.getTitle());
		}
		
		private void createMenuItem(String name)
		{
			JMenuItem mi = new JMenuItem();
			add(mi);
			mi.setText(name);
		}
	}
	

}
