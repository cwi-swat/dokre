package richrail.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import richrail.domain.Wagon;


@SuppressWarnings("serial")
public class WagonsPanel extends JPanel implements ActionListener{
	
	private GuiDelegate delegate;
	
	public JTextField newname, seats;
	public JButton create, remove;
	public JComboBox wagoncombo;
	
	public WagonsPanel(){
		
		JLabel headerlabel = new JLabel("Wagons manager");
		
		newname = new JTextField("", 10);
		seats = new JTextField("", 3);
		create = new JButton("Create wagon");
		
		wagoncombo = new JComboBox();
		remove = new JButton("Remove wagon");
		
		this.setLayout(new GridLayout(3,2,20,20));
		
		this.add(headerlabel);
		this.add(new JPanel());
		
		JPanel tmp = new JPanel(new FlowLayout());
		tmp.add(newname);
		tmp.add(seats);
		
		this.add(tmp);
		this.add(create);
		this.add(wagoncombo);
		this.add(remove);
	}
	
	public void setDelegate(GuiDelegate delegate) {
		this.delegate = delegate;
		this.setHandlers();
	}

	private void setHandlers() {
		create.addActionListener(this);
		remove.addActionListener(this);		
	}

	public void updateWagons(List<Wagon> wagons) {
		wagoncombo.removeAllItems();
		for(Wagon wg : wagons){
			wagoncombo.addItem(wg.getName());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == create){
			delegate.createWagon();
		} else if (e.getSource() == remove){
			delegate.removeWagon();
		}
		
	}
	
}
