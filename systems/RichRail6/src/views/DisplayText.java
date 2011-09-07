package views;

import java.awt.Color;
import javax.swing.JTextArea;
import models.Train;
import models.Wagon;
import models.WagonType;

public class DisplayText extends Display implements Observer {
	private static final long serialVersionUID = 1L;
	
	private JTextArea text_field = new JTextArea();
	
	public DisplayText(){
		setVariables();
    	text_field = new JTextArea(generateContents());
    	text_field.setSize(getWidth(),200);
    	text_field.setLineWrap(true);
    	text_field.setAutoscrolls(true);
    	this.setForeground(Color.black);
    	this.setBackground(Color.white);
    	text_field.setForeground(Color.black);
    	text_field.setBackground(Color.white);
    	text_field.setCaretColor(Color.black);
    	this.add(text_field);
	}
	
    @Override
    public void refresh(){
    	setVariables();
    	text_field.setText(generateContents());
    }
    
    private String generateContents(){
    	String s = "";
    	if(wagontypes.isEmpty()){
    		s += "No wagons\n";
    	}else{
    		s += "Wagons:\n";
	    	for(WagonType w : wagontypes){
	    		s += w.toString()+" ";
	    	}
	    	s += "\n";
    	}
    	
    	if(trains.isEmpty()){
    		s += "No trains\n";
    	}else{
    		s += "Trains:\n";
	    	for(Train train : trains){
	    		s += train.toString();
	    		for(Wagon w : train.getWagons()){
	    			s += "-"+w.toString();
	    		}
	    		s += "\n";
	    	}
	    	s += "\n";
    	}
    	return s;
    }
  
}
