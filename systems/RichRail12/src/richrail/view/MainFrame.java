package richrail.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import richrail.RichRailApp;

public class MainFrame extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private RichRailApp app;
	private JTextField commandTextField;

	public MainFrame(RichRailApp app) {
		this.app = app;
		initGUI();
	}

	private void initGUI(){
		this.setTitle("RichRail - v1.0");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(0, 0, 400, 400);
		this.setPreferredSize(new java.awt.Dimension(400, 400));
		this.setSize(800, 600);
		this.setBackground(new java.awt.Color(0,0,0));
		this.setLayout(null);
		this.setForeground(new java.awt.Color(255,255,255));
		{
			TextualDepotPanel garagePanel = new TextualDepotPanel(app.getTrainDepot());
			this.add(garagePanel);
			garagePanel.setBounds(1, 200, 350, 200);
			garagePanel.setBackground(new java.awt.Color(200,200,200));
		}
		{
			ImageTrainPanel trainPanel = new ImageTrainPanel(app.getTrainDepot());
			this.add(trainPanel);
			trainPanel.setBounds(0,0,800,200);
		}
		{
			JLabel outputText = new JLabel();
			this.add(outputText);
			outputText.setText("output");
			outputText.setBounds(385, 175, 50, 100);
		}
		{
			LogObserverPanel loggerPanel = new LogObserverPanel();
			this.add(loggerPanel);
			loggerPanel.setBounds(433, 200, 350, 200);
			loggerPanel.setBackground(new java.awt.Color(200,200,200));
		}
		{
			JLabel commandText = new JLabel();
			this.add(commandText);
			commandText.setText("command");
			commandText.setBounds(10, 370, 100, 100);
		}
		{
			JButton executeButton = new JButton();
			this.add(executeButton);
			executeButton.setText("execute");
			executeButton.setBounds(270, 408, 80, 25);
			executeButton.addActionListener(this);
		}
		{
			commandTextField = new JTextField();
			this.add(commandTextField);
			
			commandTextField.setBounds(85, 408, 180, 25);
			commandTextField.addActionListener(this);
			commandTextField.addKeyListener(this);
		}
		this.addWindowFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		    	commandTextField.requestFocusInWindow();
		    }
		});

	}

	public void actionPerformed(ActionEvent arg0) {
		this.app.getCommandParser().parseCommand(commandTextField.getText());
		commandTextField.setText("");
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// On key up, retrieve the last command string
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			String lastCommandText = this.app.getCommandParser().getLastCommandString();
			commandTextField.setText(lastCommandText);
		} 
		// On key down, set the field to empty
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			commandTextField.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
