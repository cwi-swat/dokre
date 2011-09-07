package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Station;
import java.awt.Dimension;
import java.util.Collections;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class Log_display extends JPanel implements Observer{
	private JTextArea txt1 = new JTextArea();
        private JScrollPane jsp = new JScrollPane();
	public Log_display(){
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		this.setOpaque(true);
		this.setVisible(true);

                txt1.setBackground(Color.BLACK);
		txt1.setForeground(Color.WHITE);
                jsp.setBackground(Color.BLACK);
		jsp.setForeground(Color.WHITE);
                jsp.setPreferredSize(new Dimension(350, 185));
		txt1.setEditable(false);
                jsp.getViewport().add(txt1);
		this.add(jsp, BorderLayout.NORTH);
		
		
	}

	@Override
	public void refreshData() {
		txt1.setText("");
		ArrayList<String> log = Station.getInstance().getLog();
                Collections.reverse(log);
		for (String s : log){
			txt1.setText(txt1.getText()+s.toString()+"\n");
		}
	}
}
