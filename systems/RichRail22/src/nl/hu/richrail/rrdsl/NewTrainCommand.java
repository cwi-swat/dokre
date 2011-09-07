package nl.hu.richrail.rrdsl;

import nl.hu.richrail.trains.Engine;
import nl.hu.richrail.trains.RollingStock;
import nl.hu.richrail.trains.RollingStockPool;
import nl.hu.richrail.trains.Train;

public class NewTrainCommand implements Command
{
	public static final String TOKEN = "train";
	private String trainId,locomotiveId;
	
	
	//locomotiveId ?? also todo to be removed actually
	public NewTrainCommand(String trainId)
	{
		this.locomotiveId = "haxxpr";
		this.trainId = trainId;
	}

	@Override
	public CmdResult<NewTrainCommand> interpret(RollingStockPool context)
	{
		Train nt = (Train) new Train(trainId);
		context.addRollingStock(nt);
		return new CmdResult<NewTrainCommand>("Created new Train with ID: " + trainId + " and locomotive: " + locomotiveId, 
				(RollingStock)nt);
		
		// try catch	return new CmdResult("Failed to create train, Rollingstock item# id: " + locomotiveId + "is not an Egine!",null,this.getClass());
		
		
	}
}
