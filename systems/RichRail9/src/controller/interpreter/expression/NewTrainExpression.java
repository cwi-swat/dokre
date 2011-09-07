package controller.interpreter.expression;

import logging.Logger;
import controller.AppController;
import controller.interpreter.Context;
import controller.interpreter.InvalidExpressionException;
import domain.Train;

public class NewTrainExpression extends Expression
{
	private Expression id;
	
	@Override
	public void interpret(Context context) throws InvalidExpressionException
	{
		Expression expression = new IDExpression();
		expression.interpret(context);
		id = expression;
		
		Train t = new Train(id.toString());
		
		// Get the controller
		AppController controller = context.getController();
		
		// Add the train
		try {
			controller.getCompany().addTrain(t);
		
			Logger.getInstance().log(new String("train " + t.getName() + " created"));
		} catch(Exception e) {
			throw new InvalidExpressionException(e.getMessage());
		}
	}
	
	@Override
	public String toString()
	{
		if (id == null)
			return "train";
		return "train " + id.toString();
	}
	
}
