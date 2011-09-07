package gui;




import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.Station;

import Command.Factory;




@SuppressWarnings("serial")
public class RichRail extends JFrame implements ActionListener{

	private Img_display imgdisplay= new Img_display();
	private Log_display logdisplay= new Log_display();
	private List_display listdisplay = new List_display();
	private JPanel command_panel = new JPanel();
	private TextField command_text_box = new TextField("", 40);
	private JButton command_button = new JButton("Execute");
	private JPanel main_panel = new JPanel();

	public static void main(String[] args) 
	{

		RichRail inst = new RichRail();
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		

	}

	public RichRail(){
		initGUI();   
		Station.getInstance(imgdisplay, listdisplay, logdisplay);
		Station.getInstance().register(logdisplay);
	}

	public void initGUI(){

		main_panel.setLayout(new BorderLayout());
		main_panel.setSize(new Dimension(800, 600));
		
		imgdisplay.setPreferredSize(new Dimension(800, 300));
		logdisplay.setPreferredSize(new Dimension(350, 200));
		listdisplay.setPreferredSize(new Dimension(350, 200));
		command_panel.setPreferredSize(new Dimension(400, 100));
		
		command_panel.add(command_text_box);
		command_panel.add(command_button);
		
		command_button.addActionListener(this);
		
		main_panel.add(imgdisplay, BorderLayout.NORTH);
		main_panel.add(logdisplay, BorderLayout.EAST);
		main_panel.add(listdisplay, BorderLayout.WEST);
		main_panel.add(command_panel, BorderLayout.SOUTH);
		
		this.getContentPane().add(main_panel);
		
		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		



	}

	public void actionPerformed(ActionEvent e){
		if(!command_text_box.equals("")){
			String command = command_text_box.getText();
			Factory f = new Factory(command);
			f.createCommand();
			
		}
	}
}
