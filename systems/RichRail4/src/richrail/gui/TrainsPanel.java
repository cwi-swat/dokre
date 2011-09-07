package richrail.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import richrail.domain.Train;
import richrail.domain.Wagon;


@SuppressWarnings("serial")
public class TrainsPanel extends JPanel implements ActionListener{
	
	private GuiDelegate delegate;
	private JButton create, remove, addwagon;
	public JTextField newname;
	public JComboBox train_addtowagoncombo, traincombo, wagon_addtowagoncombo;
	
	
	public TrainsPanel(){
		
		JLabel headerlabel = new JLabel("Trains manager");
		
		newname = new JTextField("", 10);
		create = new JButton("Create train");
		
		traincombo = new JComboBox();
		remove = new JButton("Remove train");
		
		train_addtowagoncombo = new JComboBox();
		wagon_addtowagoncombo = new JComboBox();
		addwagon = new JButton("Add wagon to train");
		
		
		this.setLayout(new GridLayout(5, 2, 20, 20));
		this.add(headerlabel);
		this.add(new JPanel());
		
		this.add(newname);
		this.add(create);
		
		this.add(traincombo);
		this.add(remove);
		
		this.add(train_addtowagoncombo);
		this.add(wagon_addtowagoncombo);
		
		this.add(addwagon);
		

	}
	
	public void setDelegate(GuiDelegate guiDelegate) {
		this.delegate = guiDelegate;
		this.setHandlers();
	}
	
	private void setHandlers(){
		create.addActionListener(this);
		remove.addActionListener(this);
		addwagon.addActionListener(this);
	}
	
	public void updateTrains(List<Train> trains){
		traincombo.removeAllItems();
		train_addtowagoncombo.removeAllItems();
		
		for(Train tr : trains){
			traincombo.addItem(tr.getName());
			train_addtowagoncombo.addItem(tr.getName());
		}
	}
	
	public void updateWagons(List<Wagon> wagons){
		wagon_addtowagoncombo.removeAllItems();
		for(Wagon wg : wagons){
			wagon_addtowagoncombo.addItem(wg.getName());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == create){
			delegate.createTrain();
		} else if(e.getSource() == remove){
			delegate.removeTrain();
		} else if(e.getSource() == addwagon){
			delegate.addWagon();
		}
		
	}
	
}
