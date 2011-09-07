import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DataFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Displays
	TextView tv = new TextView();
	GraphicalView gv = new GraphicalView();
	LogView lv = new LogView();

	// Trains and Displays
	ArrayList<Train> trains;
	ArrayList<IDisplay> displays = new ArrayList<IDisplay>();

	// ExecuteField
	private JTextField tf;
	private JButton b;

	// Data class
	public DataFrame(ArrayList<Train> Trains, LogView logv)
	{
		new JFrame();
		trains = Trains;
		lv = logv;

		this.addDisplay(tv); 
		this.addDisplay(gv);
		this.addDisplay(lv);

		this.setTitle("RichRail");
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(600, 500));
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		init();
	}

	public void addDisplay(IDisplay d)
	{
		displays.add(d);
	}

	public void init(){

		this.add(tv);
		this.getContentPane().add(tv, BorderLayout.EAST);

		this.add(lv);
		this.getContentPane().add(lv, BorderLayout.WEST);

		this.addDisplay(gv);
		this.getContentPane().add(gv, BorderLayout.NORTH);

		JPanel executePanel = new JPanel();
		tf = new JTextField(25);
		b = new JButton("Execute");
		executePanel.add(tf);
		executePanel.add(b);
		this.getContentPane().add(executePanel, BorderLayout.SOUTH);

		ActionListener al = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				for (IDisplay d : displays)
				{
					d.update(trains);
				}
			}
		};

		addExecutionListener(al);
	}

	public void addExecutionListener(ActionListener a){
		b.addActionListener(a);
	}

	public String getExecuteString(){
		return tf.getText();
	}
}