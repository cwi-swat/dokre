package domain;

import java.awt.Color;
import java.awt.Graphics;

public class Locomotive extends Wagon {
	
	public Locomotive()
	{
	}
	
	public Locomotive(String name) {
		super(name);
	}
	
	public Locomotive(String name, int seats)
	{
		super(name, seats);
	}


	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.fillRect((x+18), y, 14, 5);
		g.fillRect((x+20), (y+5), 10, 20);
		g.fillRect((x+65), y, 55, 5);
		g.fillRect((x+70), (y+5), 50, 20);
		g.fillRect(x, (y+25), 120, 50);
		g.fillOval((x+10), (y+75), 30, 30);
		g.fillOval((x+90), (y+75), 30, 30);
		g.setColor(Color.WHITE);
		g.drawString(name, (x+10), (y+50));
	}
}
