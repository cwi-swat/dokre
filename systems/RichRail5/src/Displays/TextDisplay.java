package Displays;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

//import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//import javax.swing.JFrame;
//import java.awt.Frame;

import Domain.Train;
import Domain.Wagon;
import Domain.WagonType;
import TaskSpecific.*;

public class TextDisplay extends JPanel implements Displayer{
	private Controller controller = Controller.getInstance();
	private JPanel panel;
	private static JTextArea textArea;
	private JLabel LabelKopTekst = new JLabel("This is the text display");
	
	public TextDisplay(JPanel p ) {

		panel = new JPanel(); 
		add(panel); 
		panel.setBackground(Color.CYAN); 
		panel.add(LabelKopTekst);
		panel.setPreferredSize(new Dimension(450, 250));
		
		textArea = new JTextArea("");
        textArea = new JTextArea(12, 36);
        textArea.setPreferredSize(new Dimension(300, 400));
        textArea.setBackground(Color.WHITE);
        textArea.setEditable(false);
        
        panel.add(textArea);
        JScrollPane scrollpane = new JScrollPane(textArea);
        panel.add(scrollpane, BorderLayout.CENTER);
        
		setSize(400, 150);
		setVisible(true);

	}

	public void setController(Controller con){
		this.controller = con;               
		con.addObserver( this );
	}
	
	@Override
	public void update(Observable subject) {
		TextDisplay.textArea.setText("Wagontypes: \n");
		//TextDisplay.jb.setText(TextDisplay.jb.getText() + con.getWagonTypes());
		String s = "";
		for (WagonType w : controller.WagonTypes){
			s = s + "(" + w.getNaam() + ":" + w.getSeats() + ")";
		}
		TextDisplay.textArea.setText(TextDisplay.textArea.getText() +s);
		s = "";
		
		
		TextDisplay.textArea.setText(TextDisplay.textArea.getText() + "\n Wagons: \n");
		for (Wagon w : controller.Wagons){
			s = s + "(" + w.getID() + ")";
		}
		TextDisplay.textArea.setText(TextDisplay.textArea.getText() +s);
		s = "";
		
		TextDisplay.textArea.setText(TextDisplay.textArea.getText() + "\n Trains: \n");
		//TextDisplay.jb.setText(TextDisplay.jb.getText() + con.getTrainsAndWagons());
		for (Train tr : controller.Trains){
			s = s + "(" + tr.getName() + ")";
			for (Wagon w : tr.getWagons()){
				s = s + "-(" + w.getID() + ":" + w.getType().getNaam() + ")";
			}
			s = s + "\n";
		}
		TextDisplay.textArea.setText(TextDisplay.textArea.getText() +s);
	}



}
