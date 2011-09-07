import java.awt.Dimension;
import java.awt.TextArea;
import java.util.ArrayList;

import javax.swing.JPanel;

public class LogView extends JPanel implements IDisplay {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextArea text;
	
	public LogView()
	{
		this.setPreferredSize(new Dimension(300, 20));
		text = new TextArea(15, 27);
	}
	
	@Override
	public void update() {

	}

	@Override
	public void update(ArrayList<Train> TrainList) {
		
	}

	public void showtext(String textthis){
		
		// Refresh
		this.removeAll();
		this.updateUI();	
		
		// Add text rule
		text.setText(text.getText() + "\n " + textthis.toString());
		this.add(text);
		
		text.setFocusable(true);
		text.setCaretPosition(text.getText().length());
		text.setFocusable(false);	
	}
}
