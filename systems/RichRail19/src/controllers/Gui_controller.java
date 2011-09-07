package controllers;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import command.Command;

import trainManagement.TrainManagement;
import view.MainFrame;
import view.MyOutputStream;


public class Gui_controller implements ActionListener, KeyListener, Observer{
	private TrainManagement model = null;
	private JButton execute = null;
	private JPanel drawpanel = null;
	private JTextField input = null;
	private MainFrame mf;
	
	public Gui_controller(TrainManagement model){
		this.model = model;
		model.addObserver(this); 
	}
	
	public void show(){
		JFrame frame = new JFrame("Rich Rail");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawpanel = new JPanel();
		input = new JTextField(40);
		execute = new JButton("Execute");
		execute.addActionListener(this);
		execute.setMnemonic(KeyEvent.VK_X);
		input.addKeyListener(this);
		mf = new MainFrame(frame,execute,input,drawpanel);
		frame.addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e){
				input.requestFocus();
			}
		});
		frame.pack();
		input.setRequestFocusEnabled(true);
		frame.setVisible(true);
		System.setOut(new PrintStream(new MyOutputStream(mf)));
		
	}
	
	//Controleerd de command op juistheid 
	@SuppressWarnings("unchecked")
	public void doCommand(){
		//Split the command
		String command = mf.getCommand();
		String [] tokens = null;
		tokens = command.split(" ");
  
		//First node = class. Can be found in command package
		String className = "command." + tokens[0] + "_command";

		//Try to instantiate command, else error.
		try {
			Class clas = Class.forName( className );
			Command o = (Command) clas.newInstance();
		    
			o.setTrainManagment(model);
			o.setParams(tokens);
			Boolean a = o.Action();
			
			if(a == false) {
				paint();
			}
		}	
		catch (ClassNotFoundException e) {
			System.out.println("command not correct" );
		} catch( Exception e ) {
			System.out.println("command not correct.");
		} 

	}
	
	//
	public void drawTrain(String t,int i){
		mf.drawTrain(t,i);
	}
	
	public void drawWagon(String w, int wg, int tr){
		mf.drawWagon(w,wg,tr);
	}
	
	public void paint() throws IOException{
		String str;
		String[] split;
		BufferedReader reader = new BufferedReader(
				new StringReader(model.toString()));
		int tr = -1;
		
		
		
		while((str=reader.readLine())!=null){
			int wg = 0;
			tr++;
			mf.setData(model.getData());
			split = str.split("-");
			drawTrain(split[0],tr);
			for(int i=1;i<split.length;i++){
				wg++;
				mf.setData("wg:"+wg);
				drawWagon(split[i],wg, tr);
			}
		}
		input.setText("");
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		doCommand();
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			doCommand();
		}else if(key==KeyEvent.VK_X && key == KeyEvent.VK_ALT){
			doCommand();
		}
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void update(Observable o, Object arg) {
		try {
			this.paint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("View is niet geupdate.");
		}
	}



}
