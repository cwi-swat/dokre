package controller.interpreter.expression;

import logging.Logger;
import controller.AppController;
import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;
import domain.Part;
import domain.Train;

public class RemExpression extends Expression
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
				
		// Skip next context item ('from')
		if(!context.pollFirst().toString().equals("from")) {
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
		
		// Remove part from train
		if(train.removePart(part))
		{
			Logger.getInstance().log("part " + part.getName() + " removed from train " + part.getTrain().getName());
		}
		else
		{
			throw new InvalidExpressionException(part.getName() + " is not part of train " + train.getName());
		}
	}
	
}
