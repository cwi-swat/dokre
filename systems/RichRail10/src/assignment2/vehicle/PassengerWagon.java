package assignment2.vehicle;

import java.awt.*;

public class PassengerWagon extends Wagon
{
	private final int DEFAULT_NUMSEATS = 20;

	public PassengerWagon(String id)
	{
		setId(id);
		setNumberOfSeats(DEFAULT_NUMSEATS);
	}

	public PassengerWagon(String id, int numberOfSeats)
	{
		setId(id);
		setNumberOfSeats(numberOfSeats);
	}

	public void draw(Graphics g, int currentTrain, int curNumOfWagons)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(30 + (curNumOfWagons * LENGTH), 80 + (currentTrain * OFFSET), 80, 40);
		g.setColor(Color.BLACK);
		g.fillRoundRect(35 + (curNumOfWagons * LENGTH), 120 + (currentTrain * OFFSET), 20, 20, 20, 20);
		g.fillRoundRect(80 + (curNumOfWagons * LENGTH), 120 + (currentTrain * OFFSET), 20, 20, 20, 20);
		g.drawString(getId(), 40 + (curNumOfWagons * LENGTH), 105 + (currentTrain * OFFSET));
	}
}
