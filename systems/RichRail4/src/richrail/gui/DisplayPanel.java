package richrail.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

import richrail.domain.Train;
import richrail.domain.Wagon;

@SuppressWarnings("serial")
public class DisplayPanel extends JPanel{
	
	List<Train> trains;
	
	private static int TRAINWIDTH = 80;
	private static int TRAINHEIGHT = 30;
	private static int TRAINSPACING = 5;
	
	private static int WAGONWIDTH = 80;
	private static int WAGONHEIGHT = 25;
	private static int WAGONSPACING = 5;
	
	private static int LABELOFFSET = 3;
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect (5, 5, 770, 200);
		g.setColor(Color.BLACK);
		g.drawRect (5, 5, 770, 200);
		
		// draw trains when possible
		
		if(trains != null){
			Point trainpointer = new Point(10+TRAINSPACING, 10+TRAINHEIGHT+TRAINSPACING);
			
			for(Train tr : trains){
				
				// draw engine
				g.setColor(Color.RED);
				g.fillRect(trainpointer.x, trainpointer.y-TRAINHEIGHT, TRAINWIDTH, TRAINHEIGHT);
				g.setColor(Color.BLACK);
				g.drawRect(trainpointer.x, trainpointer.y-TRAINHEIGHT, TRAINWIDTH, TRAINHEIGHT);
				g.drawString(tr.getName()+"("+tr.getSeats()+")", trainpointer.x+LABELOFFSET, trainpointer.y-LABELOFFSET);
				
				// draw wagons
				if(!tr.getWagons().isEmpty()){
					
					Point wagonpointer = new Point(trainpointer.x+TRAINWIDTH+WAGONSPACING, trainpointer.y);
					
					for(Wagon wg : tr.getWagons()){
						g.setColor(Color.GREEN);
						g.fillRect(wagonpointer.x, wagonpointer.y-WAGONHEIGHT, WAGONWIDTH, WAGONHEIGHT);
						g.setColor(Color.BLACK);
						g.drawRect(wagonpointer.x, wagonpointer.y-WAGONHEIGHT, WAGONWIDTH, WAGONHEIGHT);
						g.drawString(wg.getName()+"("+wg.getSeats()+")", wagonpointer.x+LABELOFFSET, wagonpointer.y-LABELOFFSET);
						
						wagonpointer.setLocation(wagonpointer.x + WAGONWIDTH + WAGONSPACING, wagonpointer.y);
					}
				}
				trainpointer.setLocation(trainpointer.x, trainpointer.y + TRAINSPACING + TRAINHEIGHT);

			}
		}
		
		
	}

	public void updateTrains(List<Train> trains) {
		this.trains = trains;
		this.repaint();
	}
}
