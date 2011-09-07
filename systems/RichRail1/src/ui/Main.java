package ui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import domain.EndWagon;
import domain.Locomotive;
import domain.NormalTrain;
import domain.NormalWagon;
import domain.Train;
import domain.Wagon;

import logic.Controller;
import logic.LogWriter;
import logic.Writer;

public class Main {
	public static void main(String[] args)
	{	
		JFrame frame1 = new JFrame();
		frame1.setLayout(new GridLayout(2,1));
		frame1.setSize(1000, 700);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		panel1.setLayout(new GridLayout(1,1));
		panel2.setLayout(new GridLayout(2,1));

		Writer writer = new LogWriter();
		
		Controller c = new Controller();
		
		View v1 = new GraphicView();
		View v2 = new ConsoleView();
		View v3 = new LogView();
		CommandView v4 = new CommandView();
		
		v4.setController(c);
		
		panel1.add(v1);
		panel2.add(v2);
		panel2.add(v3);
		panel2.add(v4);
		frame1.add(panel1);
		frame1.add(panel2);
		
		c.addWriter(writer);
		c.addView(v1);
		c.addView(v2);
		c.addView(v3);
		c.addView(v4);

		Train t1 = new NormalTrain("Train1");
		c.addTrain(t1);
		
		t1.addObserver(c);
		
		Wagon w1 = new Locomotive("Locomotive Train1");
		Wagon w2 = new NormalWagon("Normalw");
		Wagon w3 = new EndWagon("Endw");
		
		c.addWagon(w1);
		c.addWagon(w2);
		c.addWagon(w3);
		
		t1.addWagon(w1);
		t1.addWagon(w2);
		t1.addWagon(w3);
	}
}