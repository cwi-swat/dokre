package Displays;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Frame;
//import java.awt.GridLayout;
//import java.awt.event.AdjustmentEvent;
//import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
//import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import TaskSpecific.*;

public class LogDisplay extends JPanel implements Displayer {
	 private ArrayList<String> commandsString = new ArrayList<String>();
	 private Controller controller = Controller.getInstance();
	 private MasterGUI MGUI;
	 private JPanel panel;
	 private static JTextArea textArea;
	 private JFrame frame;
	 private JLabel LabelKopTekst = new JLabel("This is the log display");
     
	 public LogDisplay(JPanel panel) {
           //Hier wordt het panel gemaakt
		   // bestaat enkel uit een label
           panel = new JPanel(); 
           add(panel); 
           panel.add(LabelKopTekst);
           panel.setBackground(Color.CYAN); 
           panel.setPreferredSize(new Dimension(450, 250));
           
           commandsString.addAll(controller.getLogs());
           String s = "";
           for(String string: commandsString){
        	   s = string+ "\n" + s;
           }
           
           textArea = new JTextArea(13, 36);
           textArea.setPreferredSize(new Dimension(400, 450));
           textArea.setBackground(Color.BLACK);
           textArea.setForeground(Color.WHITE);
           textArea.setEditable(false);
           textArea.setText(s);
           textArea.setSize(400, 510);
   		   setVisible(true);
   		  // JScrollPane scrollpane;
   		
   	       textArea.setLineWrap(true);
   	       //scrollpane = new JScrollPane(jb);
   	       JScrollPane scrollpane = new JScrollPane(textArea);
   	       panel.add(scrollpane, BorderLayout.CENTER);
   	      // scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
   	      // scrollpane.setVerticalScrollBar(new JScrollBar());
   		   
   		   //panel.add(jp);
   		 
   		   
     }
	 	public void setController(Controller con){
	 		//Dit is nodig voor de Displayer
	 		this.controller = con;               
	        con.addObserver( this );
	 	}
     

	@Override
	public void update(Observable subject) {
		//Bij het toevoegen van een nieuwe command moet die aan deze lijst
		//Toegevoegd worden
		for(String string :controller.getLogs()){
				if(!commandsString.contains(string)){
					commandsString.add(string);
			}
		}
        String s = "";
        for(String string: commandsString){
     	   s = s + string+ "\n";
        }
        textArea.setText(s);
	}
}