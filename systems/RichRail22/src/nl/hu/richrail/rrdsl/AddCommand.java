package nl.hu.richrail.rrdsl;

import nl.hu.richrail.swing.gfx.Waggon;
import nl.hu.richrail.trains.RollingStock;
import nl.hu.richrail.trains.RollingStockPool;
import nl.hu.richrail.trains.Train;


public class AddCommand implements Command
{

	private String id1,id2;
	
	public AddCommand(String id1,String id2)
	{
		
		this.id1 = id1;
		this.id2 = id2;
	}
	
	@Override
	public CmdResult<AddCommand> interpret(RollingStockPool context) {
		
		Train toTrain = context.getTrain(id2);
		RollingStock addedTrain = context.getRollingStockItem(id1);
		
		try{
		context.attachRollingStock(addedTrain, toTrain);
		return new CmdResult<AddCommand>("Added: " +addedTrain.getId() + " to: " + toTrain.getId(), toTrain);
		}catch (NullPointerException e) {
			return new CmdResult<AddCommand>("[ERROR] Adding wagon: " +id1 + " " + addedTrain+ " from: " +id2 +" " +toTrain,addedTrain);
		}
	}

}
