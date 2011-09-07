import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GraphicalView extends JPanel implements IDisplay{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ArrayList<Train> trains;
	
	int trainsDrawn = 0;
	int wagonsDrawn = 0;
	
	public GraphicalView()
	{	
		this.setPreferredSize(new Dimension(200, 200));
		this.setVisible(true);
//		this.setBackground(Color.PINK);
		trains = new ArrayList<Train>();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		for (Train t : trains)
		{
			// Train

			
			Graphics g = this.getGraphics();
			g.setColor(Color.RED);
			
			int y = (trainsDrawn * 50 + trainsDrawn * 10) + 10;
			g.fillRect(10, y,50,50);
			
			trainsDrawn++;
						
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			// Wagons
			for	(@SuppressWarnings("unused") Wagon w : t.getWagons())
			{
				// Train + leeway + wagonsDrawn * 25
				int width = 70 + (wagonsDrawn * 25 + wagonsDrawn * 10);
				
				g.fillRect(width, (y + 25),25,25);
				
				// x y width height
					
				wagonsDrawn++; 
			}
			
			wagonsDrawn = 0;

//			this.updateUI();
		}
		
		trainsDrawn = 0;
		wagonsDrawn = 0;
	}

	@Override
	public void update(ArrayList<Train> TrainList) {
		// TODO Auto-generated method stub
		trains = TrainList;
		update();
	}

}
