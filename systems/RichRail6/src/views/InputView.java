package views;

import helpers.Command;
import java.awt.event.*;
import java.awt.Dimension;

import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import models.Storage;

public class InputView extends JFrame implements ActionListener, KeyListener {
    private static final long serialVersionUID = 1L;
	
    private Command command;
	
    private int x = 5;
    private int y;
    private int width = 670;
    
    private JTextField commandField = new JTextField();
    private JButton commandButton = new JButton();
    private JButton newFrameButton = new JButton();
    
    public InputView(Command cmd) {
    	Storage.get().addDisplay(new DisplayImage());
    	Storage.get().addLogDisplay(new DisplayLog());
    	Storage.get().addDisplay(new DisplayText());
    	command = cmd;
        try {
            initGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initGUI() throws Exception {
        getContentPane().setLayout( null );
        setSize(new Dimension(this.width, 720));
        
        loadViews();
        
        commandField.setBounds(new Rectangle(5, 5, 500, 20));
        commandButton.setText("Execute");
        commandButton.addActionListener(this);
        commandField.addKeyListener(this);
        commandButton.setBounds(new Rectangle(510, 5, 140, 20));
        newFrameButton.setText("New displayframe");
        newFrameButton.addActionListener(this);
        newFrameButton.setBounds(new Rectangle(510, 35, 140, 20));
        getContentPane().add(commandButton, null);
        getContentPane().add(commandField, null);
        getContentPane().add(newFrameButton, null);
        getContentPane().setComponentZOrder(newFrameButton, 0);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void loadViews(){
    	y = 70;
    	ArrayList<Display> logviews = Storage.get().getLogDisplays();
    	for(Display display : logviews){
    		display.setLogs(Storage.get().getLogs());
    		addView(display);
    	}
    	
    	ArrayList<Display> views = Storage.get().getDisplays();
    	for(Display display : views){
    		display.setTrains(Storage.get().getTrains());
    		display.setWagonTypes(Storage.get().getWagonTypes());
    		addView(display);
    	}
    }
    
    public void addView(Display display){
		display.refresh();
		display.setBounds(new Rectangle(x, y, 640, 200));
		getContentPane().add(display, null);
		y += 200;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==commandButton){
			command.parse_command(commandField.getText());
			commandField.setText("");
		}else if(e.getSource() == newFrameButton) {
			JFrame f = new JFrame(); 
	    	Display d = new DisplayImage();
	    	f.add(d);
	    	f.setSize(new Dimension(500,500));
	    	f.setDefaultCloseOperation(HIDE_ON_CLOSE);
	    	f.setVisible(true);
	    	Storage.get().addDisplayAndRefresh(d);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource()==commandField){
           if(e.getKeyCode()==KeyEvent.VK_ENTER){ 
   				command.parse_command(commandField.getText());
				commandField.setText("");
           }
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}