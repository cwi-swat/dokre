package nl.hu.richrail.rrdsl;

import nl.hu.richrail.trains.RollingStockPool;
import nl.hu.richrail.trains.Train;

public class DelTrainCommand implements Command
{

	private String rollingStockId;

	public DelTrainCommand(String rollingStockId)
	{
		this.rollingStockId = rollingStockId;
	}
	@Override
	public CmdResult<DelTrainCommand> interpret(RollingStockPool context)
	{
		Train t = context.getTrain(rollingStockId);
		context.removeRollingStock(t);
		return new CmdResult<DelTrainCommand>("Train: "+ rollingStockId +" removed",t);
	}
}
