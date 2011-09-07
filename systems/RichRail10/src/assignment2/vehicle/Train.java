package assignment2.vehicle;

import java.awt.*;
import java.util.ArrayList;

public class Train extends Vehicle
{
	private ArrayList<Wagon> wagons = new ArrayList<Wagon>();

	public Train(String id)
	{
		setId(id);
		setType("train");
	}

	public void addWagon(String id, Wagon wagon)
	{
		wagons.add(wagon);
	}

	public void removeWagon(Wagon wagon)
	{
		while (wagons.contains(wagon))
		{
			wagons.remove(wagon);
		}
	}

	public boolean hasWagon(Wagon wagon)
	{
		return wagons.contains(wagon);
	}

	public ArrayList<Wagon> getWagons()
	{
		return wagons;
	}

	@Override
	public int getNumberOfSeats()
	{
		int numSeats = super.getNumberOfSeats();

		for (int i = 0; i < wagons.size(); i++)
		{
			numSeats += wagons.get(i).getNumberOfSeats();
		}

		return numSeats;
	}

	public void draw(Graphics g, int currentTrain, int curNumOfWagons)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30, 80 + currentTrain * OFFSET, 80, 40);
		g.fillRect(80, 60 + currentTrain * OFFSET, 30, 30);
		g.drawRoundRect(85, 40 + currentTrain * OFFSET, 20, 20, 20, 20);
		g.drawRoundRect(85, currentTrain * OFFSET, 40, 40, 40, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35, 120 + currentTrain * OFFSET, 20, 20, 20, 20);
		g.fillRoundRect(80, 120 + currentTrain * OFFSET, 20, 20, 20, 20);
		g.drawString(getId(), 40, 105 + currentTrain * OFFSET);
	}
}
