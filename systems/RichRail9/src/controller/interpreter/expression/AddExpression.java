package controller.interpreter.expression;

import logging.Logger;
import controller.AppController;
import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;
import domain.Part;
import domain.Train;

public class AddExpression extends Expression
{
	
	private Expression trainId;
	private Expression partId;
	
	@Override
	public void interpret(Context context) throws InvalidExpressionException
	{
		// Parse the part id
		Expression expression = new IDExpression();
		expression.interpret(context);
		partId = expression;
				
		// Skip next context item ('to')
		if(!context.pollFirst().toString().equals("to")) {
			throw new InvalidExpressionException();
		}
		
		// Parse the train id
		expression = new IDExpression();
		expression.interpret(context);
		trainId = expression;
		
		// Get the controller
		AppController controller = context.getController();
		
		// Check if the specified train exists
		Train train = controller.getCompany().getTrainByName(trainId.toString());
		if(train == null) {
			throw new InvalidExpressionException(trainId.toString() + " could not be found");
		}
		
		// Check if the specified part exists
		Part part = controller.getCompany().getPartByName(partId.toString());
		if(part == null) {
			throw new InvalidExpressionException(partId.toString() + " could not be found");
		}
		
		// Add part to train
		try
		{
			train.addPart(part);
			Logger.getInstance().log("wagon " + part.getName() + " added to train " + train.getName());
			
		}
		catch(Exception e)
		{
			throw new InvalidExpressionException(e.getMessage());
		}
	}
	
}
