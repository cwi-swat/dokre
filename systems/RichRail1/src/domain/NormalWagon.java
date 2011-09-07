package domain;

import java.awt.Color;
import java.awt.Graphics;

public class NormalWagon extends Wagon {
	
	public NormalWagon()
	{
		super();
	}
	
	public NormalWagon(String name)
	{
		super(name);
	}
	
	public NormalWagon(String name, int seats) {
		super(name, seats);
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 120, 5);
		g.fillRect((x+5), (y+5), 110, 70);
		g.fillOval((x+5), (y+75), 30, 30);
		g.fillOval((x+85), (y+75), 30, 30);
		g.setColor(Color.WHITE);
		g.drawString(name, (x+10), (y+50));
	}
}