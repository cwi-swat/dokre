package richrail.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class TrainCompositeImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image img;
	private String label;
	
	public TrainCompositeImagePanel(Image img, String label, int width, int height) {
		super();
		this.img = img;
		this.label = label;
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(width, height));
		this.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
		g.drawString(label, 0, 80);
	}
}
