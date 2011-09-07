package nl.hu.richrail.rrdsl;

import nl.hu.richrail.trains.RollingStock;
import nl.hu.richrail.trains.RollingStockPool;
import nl.hu.richrail.trains.Train;

public class RemCommand implements Command
{

	private String id1 , id2;
	
	public RemCommand(String id1, String id2)
	{
		this.id1 = id1;
		this.id2 = id2;
	
	}
	
	@Override
	public CmdResult<RemCommand> interpret(RollingStockPool context) {
		// TODO Auto-generated method stub
		Train t =context.getTrain(id2);
		RollingStock rs = t.getRollingStockItem(id1);
		try{
		context.detachRollingStock(rs, t);
		return new CmdResult<RemCommand>("removed wagon: " +id1 + "from: " +id2,rs);
		}catch (NullPointerException e) {
			return new CmdResult<RemCommand>("[ERROR] removing wagon: " +id1 + " " + rs+ "from: " +id2 +" " +t,rs);
		}
	}

}
