package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import view.displays.BasicGraphicalDisplay;
import view.displays.Display;
import view.displays.TextDisplay;
import controller.AppController;

public class MainWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	public AppController controller;

	private JPanel upperPanel;

	private ArrayList<Display> displays;

	private JTabbedPane tabPanel;
	private CommandOutput outputPanel;
	private CommandInput inputPanel;

	private JMenuBar menuBar;

	private JMenuItem exitMenuItem, windowsMenuItem;

	public MainWindow(AppController controller)
	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.controller = controller;

		this.setTitle("RichRail v1.0");
		this.setLayout(new BorderLayout());

		upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(2, 1));
		tabPanel = new JTabbedPane();

		upperPanel.add(tabPanel);
		outputPanel = new CommandOutput();
		upperPanel.add(outputPanel);

		this.add(upperPanel, BorderLayout.CENTER);
		inputPanel = new CommandInput(controller);
		this.add(inputPanel, BorderLayout.SOUTH);

		menuBar = new JMenuBar();
		;

		addFileMenu();
		addWindowsMenu();

		this.setSize(640, 480);
		this.setVisible(true);

		inputPanel.setFocus();

		this.initWindow();

	}

	private void addFileMenu()
	{
		JMenu menu = new JMenu("File");
		menuBar.add(menu);

		exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_Q);
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		exitMenuItem.addActionListener(this);
		menu.add(exitMenuItem);

		this.setJMenuBar(menuBar);
	}

	private void addWindowsMenu()
	{
		JMenu menu = new JMenu("Windows");
		menuBar.add(menu);

		windowsMenuItem = new JMenuItem("New window");
		windowsMenuItem.addActionListener(this);
		menu.add(windowsMenuItem);

		this.setJMenuBar(menuBar);
	}

	private void initWindow()
	{
		this.addDisplay(new TextDisplay());
		this.addDisplay(new BasicGraphicalDisplay());
	}

	public void addDisplay(Display d)
	{
		if (this.displays == null)
			this.displays = new ArrayList<Display>();

		this.displays.add(d);
		this.addDisplayAsTab(d);
		controller.addObserver(d);
	}

	public void addDisplayAsTab(Display d)
	{
		tabPanel.addTab(d.getDisplayName(), d);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == windowsMenuItem)
		{
			controller.addWindow(new MainWindow(controller));
		}
		else if (event.getSource() == exitMenuItem)
		{
			System.exit(0);
		}
	}

}
