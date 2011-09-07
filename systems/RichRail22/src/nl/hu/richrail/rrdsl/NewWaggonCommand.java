package nl.hu.richrail.rrdsl;

import nl.hu.richrail.trains.*;

public class NewWaggonCommand implements Command
{
	
	private String type,id;
	private int numberofseats;
	
	//TODO
	// What todo with this type, specification mentions a type of train -> turn out to be just a name or id
	public NewWaggonCommand(String type, String id,int numberofseats)
	{
		this.type = type;
		this.id = id;
		this.numberofseats =numberofseats;
		
	}

	@Override
	public CmdResult<NewWaggonCommand> interpret(RollingStockPool context)
	{
		RollingStock c = (RollingStock) new Car(id,numberofseats);
		context.addRollingStock(c);
		CmdResult<NewWaggonCommand> n = null;
		n = new CmdResult<NewWaggonCommand>("Created new wagon with ID:"+id+" with " + numberofseats + " seats",c);
		return n;
	}
}
