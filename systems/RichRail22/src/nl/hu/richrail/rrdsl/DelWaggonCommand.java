package nl.hu.richrail.rrdsl;

import nl.hu.richrail.trains.RollingStock;
import nl.hu.richrail.trains.RollingStockPool;

public class DelWaggonCommand implements Command
{

	private String rollingStockId;

	public DelWaggonCommand(String rollingStockId)
	{
		this.rollingStockId = rollingStockId;
	}
	@Override
	public CmdResult<DelWaggonCommand> interpret(RollingStockPool context)
	{
		RollingStock rs = context.getRollingStockItem(rollingStockId);
		context.removeRollingStock(rs);
		return new CmdResult<DelWaggonCommand>("Waggon: "+ rollingStockId +" removed",rs);
	}
}
