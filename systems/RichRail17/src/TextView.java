import java.awt.Dimension;
import java.awt.TextArea;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TextView extends JPanel implements IDisplay{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Train> trains;
	TextArea text = new TextArea(15, 27);
	
	
	public TextView()
	{
		this.setPreferredSize(new Dimension(300, 20));
//		this.setBackground(Color.BLUE);
		this.setVisible(true);
		
		trains = new ArrayList<Train>();
	}
	
	@Override
	public void update() {
		showTextView(new TrainsToText(trains));
	}

	@Override
	public void update(ArrayList<Train> TrainList) {
		// TODO Auto-generated method stub
		trains = TrainList;
		update();
	}
	
	public void showTextView(TrainsToText input){
		
		//this.setVisible(false);
		
		this.removeAll();
		this.updateUI();
		text.setText(input.toString());
		this.add(text);
		//this.setVisible(true);
		text.setFocusable(false);
	}
}
