package richrail.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class OutputPanel extends JPanel {
	
	private JTextArea textarea;
	private JScrollPane scrollpane;
	
	public OutputPanel(){
		
		textarea = new JTextArea();
		//textarea.setEnabled(false);
		textarea.setEditable(false);
		scrollpane = new JScrollPane(textarea);
		this.setLayout(new GridLayout(1,1));
		this.add(scrollpane);
	}
	
	public void append(String message){
		textarea.append(message + "\n");
		textarea.selectAll();
	}
	
	public void setText(String message){
		textarea.setText(message);
	}
}
