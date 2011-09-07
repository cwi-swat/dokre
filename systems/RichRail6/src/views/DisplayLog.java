package views;

import java.awt.Color;
import javax.swing.JTextArea;

import models.Log;

public class DisplayLog extends Display implements Observer {
	private static final long serialVersionUID = 1L;
    private JTextArea log_field;
    
    public DisplayLog(){
    	setVariables();
    	log_field = new JTextArea(this.generateLogs());
    	log_field.setSize(getWidth(),200);
    	log_field.setLineWrap(true);
    	log_field.setAutoscrolls(true);
    	this.setForeground(Color.white);
    	this.setBackground(Color.black);
    	log_field.setForeground(Color.white);
    	log_field.setBackground(Color.black);
    	log_field.setCaretColor(Color.white);;
    	this.add(log_field);
    }
    
    @Override
	public void refresh() {
    	setVariables();
    	log_field.setText(this.generateLogs());
	}
    
    
    protected String generateLogs(){
    	String s = "";
    	if (logs.size() > 0) {
    		for (Log l : logs) {
    			s = l.toString() + "\n"+s;
    		}
    	}else{
    		s = "No logs";
    	}
    	return s;
    }
}
