package domain;

import java.awt.Color;
import java.awt.Graphics;

public class EndWagon extends Wagon {

	public EndWagon(){
		super();
	}
	
	public EndWagon(String name)
	{
		super(name);
	}
	
	public EndWagon(String name, int seats)
	{
		super(name, seats);
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.fillRect(x, (y+25), 120, 5);
		g.fillRect((x+5), (y+30), 110, 45);
		g.fillOval((x+5), (y+75), 30, 30);
		g.fillOval((x+85), (y+75), 30, 30);
		g.setColor(Color.WHITE);
		g.drawString(name, (x+10), (y+50));
		
	}

}
