package nl.hu.richrail.rrdsl;

import nl.hu.richrail.trains.RollingStock;
import nl.hu.richrail.trains.RollingStockPool;

public class GetCommand implements Command
{

	private String type,id;
	public GetCommand(String type,String id)
	{
		this.type = type;
		this.id = id;
	}
	@Override
	public CmdResult<GetCommand> interpret(RollingStockPool context) {
		RollingStock rs = null;	
			if(type.equals("train"))
				rs=context.getTrain(id);
			else
				rs=context.getRollingStockItem(id);
		
		if(id != null)
			return new CmdResult<GetCommand>("The number of seats in " + type +" " +id + " is: " +rs.getSeatCount(),rs);
		
		else
			return new CmdResult<GetCommand>("The " + type +" " +id + " does not exist!",null);	
	}
	
}
