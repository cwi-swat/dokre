package controller.interpreter.expression;

import logging.Logger;
import controller.AppController;
import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;

public class DelExpression extends Expression
{

	private Expression type;
	private Expression id;

	@Override
	public void interpret(Context context) throws InvalidExpressionException
	{
		// Get the type
		type = new TypeExpression();
		type.interpret(context);
		
		// Get the Id
		id = new IDExpression();
		id.interpret(context);
		
		// Get controller
		AppController controller = context.getController();
		
		// Delete train or wagon
		try
		{
			if(type.toString().equals("train")) 
			{	
				controller.getCompany().deleteTrainByName(id.toString(), false);
				Logger.getInstance().log("train " + id.toString() + " deleted");		
			} 
			
			else if(type.toString().equals("wagon"))
			{
				controller.getCompany().deleteWagonByName(id.toString());
				Logger.getInstance().log("wagon " + id.toString() + " deleted");	
			}
		}
		catch(Exception e)
		{
			throw new InvalidExpressionException(e.getMessage());
		}
	}
}
