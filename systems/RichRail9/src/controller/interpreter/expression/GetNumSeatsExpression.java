package controller.interpreter.expression;

import logging.Logger;
import controller.AppController;
import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;
import domain.Part;
import domain.Train;

public class GetNumSeatsExpression extends Expression
{
	
	@Override
	public void interpret(Context context) throws InvalidExpressionException
	{
		// Get the type
		Expression type = new TypeExpression();
		type.interpret(context);
		
		// Get the Id
		Expression id = new IDExpression();
		id.interpret(context);
		
		// Get controller
		AppController controller = context.getController();
		
		int seats = 0;
		
		// Get number of seats
		if(type.toString().equals("train"))
		{
			Train train = controller.getCompany().getTrainByName(id.toString());
			
			if(train != null)
			{
				seats = train.getNumberOfSeats();
				
				Logger.getInstance().log("number of seats in train " + train.getName() + ": " + seats);
			}
		} 
		else if(type.toString().equals("wagon"))
		{
			Part part = controller.getCompany().getPartByName(id.toString());
			
			if(part != null) 
			{ 
				seats = part.getNumberOfSeats();
				
				Logger.getInstance().log("number of seats in wagon " + part.getName() + ": " + seats);
			}
		}
		
	}
	
}
